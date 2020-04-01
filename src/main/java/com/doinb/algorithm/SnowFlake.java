package com.doinb.algorithm;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

/**
 * Twitter的分布式自增ID雪花算法snowflake
 * <p>
 * Twitter的分布式雪花算法 SnowFlake 每秒自增生成26个万个可排序的ID
 * 1、twitter的SnowFlake生成ID能够按照时间有序生成
 * 2、SnowFlake算法生成id的结果是一个64bit大小的整数
 * 3、分布式系统内不会产生重复id（用有datacenterId和machineId来做区分）
 * datacenterId（分布式）（服务ID 1，2，3.....） 每个服务中写死
 * machineId（用于集群） 机器ID 读取机器的环境变量MACHINEID 部署时每台服务器ID不一样
 **/
public class SnowFlake {

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    private SnowFlake() {
    }

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    private synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    /**
     * @param datacenterId    （分布式）（服务ID 1，2，3.....） 每个服务中写死 选值 0-31 个机房
     * @param machineId（用于集群） 机器ID 读取机器的环境变量MACHINEID 部署时每台服务器ID不一样
     * @return long
     */
    public static long getSnowFlakeRandom(long datacenterId, long machineId) {
        SnowFlake snowFlake = new SnowFlake(datacenterId, machineId);
        return snowFlake.nextId();
    }


    public static void main(String[] args) {

        // 当前机器的 workerId
        String ipv4 = NetUtil.getLocalhostStr();
        System.out.println("ipv4:" + ipv4);
        long workerId = NetUtil.ipv4ToLong(ipv4);
        System.out.println("workerId:" + workerId);

        // exception default value.
        workerId = NetUtil.getLocalhostStr().hashCode();
        System.out.println("default workerId:" + workerId);

        Snowflake snowflake = IdUtil.getSnowflake(workerId, 1);
        System.out.println(snowflake.nextId());

/*
        //  datacenterId（分布式）（服务ID 1，2，3.....） 每个服务中写死
        //  machineId（用于集群） 机器ID 读取机器的环境变量MACHINEID 部署时每台服务器ID不一样

        // System.out.println(getSnowFlakeRandom(1,1));
        // hutool 工具包带分布式id生成
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        for (int i = 0; i < 1; i++) {
            System.out.println(IdUtil.getSnowflake(1, 1).nextId());
            System.out.println(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
*/
    }

}
