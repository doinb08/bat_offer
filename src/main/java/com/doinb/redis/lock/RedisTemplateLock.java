//package com.doinb.redis.lock;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisCommands;
//
//import java.util.Collections;
//import java.util.UUID;
//
///**
// * <p>基于 RedisTemplate 的分布式锁</p>
// */
//public class RedisTemplateLock {
//    static final Logger logger = LoggerFactory.getLogger(RedisTemplateLock.class);
//
//    static final String LOCK_SUCCESS = "OK";
//
//    static final String SET_IF_NOT_EXIST = "NX";
//
//    static final String SET_WITH_EXPIRE_TIME_PX = "PX";
//
//    static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//
//    static final Long UNLOCK_SUCCESS = 1L;
//
//    final RedisTemplate redisTemplate;
//
//    /**
//     * 全局默认过期时间，单位：毫秒
//     */
//    final long expireMillis;
//
//    ThreadLocal<String> lockFlag = ThreadLocal.withInitial(() -> "");
//
//    public RedisTemplateLock(RedisTemplate redisTemplate, long expireMillis) {
//        Assert.notNull(redisTemplate, "redisTemplate must not be null");
//        Assert.isTrue(0 < expireMillis, "expireMillis must be greater than 0");
//        this.redisTemplate = redisTemplate;
//        this.expireMillis = expireMillis;
//    }
//
//    /**
//     * 根据传入的重试次数来获取 redis 锁
//     *
//     * @param lockKey
//     * @param retryTimes
//     * @return boolean
//     */
//    public boolean lock(String lockKey, int retryTimes) {
//        int count = 0;
//        while (!tryLock(lockKey) && 0 < retryTimes--) {
//            try {
//                logger.debug("Got redis lock failed, retrying {} times...", ++count);
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 根据传入的重试次数来获取 redis 锁
//     *
//     * @param lockKey
//     * @param reqId
//     * @param expireMillis
//     * @param retryTimes
//     * @param waitMillis
//     * @return boolean
//     */
//    public boolean lock(String lockKey, String reqId, long expireMillis, int retryTimes, long waitMillis) {
//        int count = 0;
//        while (!tryLock(lockKey, reqId, expireMillis) && 0 < retryTimes--) {
//            try {
//                logger.debug("Got redis lock failed, retrying {} times...", ++count);
//                Thread.sleep(waitMillis);
//            } catch (InterruptedException e) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 快速尝试获取 redis 锁
//     *
//     * @param lockKey
//     * @return boolean
//     */
//    public boolean tryLock(String lockKey) {
//        return tryLock(lockKey, UUID.randomUUID().toString(), expireMillis);
//    }
//
//    /**
//     * 根据当前请求调用方的唯一标识来获取 redis 锁
//     *
//     * @param lockKey
//     * @param reqId
//     * @return boolean
//     * @apiNote 调用者必须要保证传入的标识是全局唯一的，禁止传入固定字符串和日期格式化字符串
//     */
//    public boolean tryLock(String lockKey, String reqId) {
//        return tryLock(lockKey, reqId, expireMillis);
//    }
//
//    /**
//     * 根据当前请求调用方的唯一标识和锁过期时间来获取 redis 锁
//     *
//     * @param lockKey
//     * @param reqId
//     * @param expireMillis
//     * @return boolean
//     */
//    public boolean tryLock(String lockKey, String reqId, long expireMillis) {
//        try {
//            String result = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
//                lockFlag.set(reqId);
//                return ((JedisCommands) connection.getNativeConnection()).set(lockKey, reqId,
//                        SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME_PX, expireMillis);
//            });
//            logger.debug("Lock result: {}", result);
//            return StringUtils.hasText(result) && LOCK_SUCCESS.equals(result);
//        } catch (Exception e) {
//            logger.error("Try getting an redis lock(key={}, value={}) occurs an error: ", lockKey, reqId, e);
//        }
//        return false;
//    }
//
//    /**
//     * 快速释放 redis 锁
//     *
//     * @param lockKey
//     * @return boolean
//     */
//    public boolean unlock(String lockKey) {
//        final String reqId = lockFlag.get();
//        try {
//            Long result = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
//                Object nativeConnection = connection.getNativeConnection();
//                if (nativeConnection instanceof JedisCluster) {
//                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_SCRIPT,
//                            Collections.singletonList(lockKey), Collections.singletonList(reqId));
//                } else if (nativeConnection instanceof RedisProperties.Jedis) {
//                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_SCRIPT,
//                            Collections.singletonList(lockKey), Collections.singletonList(reqId));
//                }
//                return 0L;
//            });
//            lockFlag.remove();
//            logger.debug("Unlock result: {}", result);
//            return UNLOCK_SUCCESS.equals(result);
//        } catch (Exception e) {
//            logger.error("Try deleting an redis lock(key={}, value={}) occurs an error: ", lockKey, reqId, e);
//        }
//        return false;
//    }
//}
