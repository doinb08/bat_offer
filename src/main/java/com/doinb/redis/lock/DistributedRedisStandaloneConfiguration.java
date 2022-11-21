//package com.doinb.redis.lock;
//
//import org.springframework.data.redis.connection.RedisPassword;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//public class DistributedRedisStandaloneConfiguration extends RedisStandaloneConfiguration {
//    public DistributedRedisStandaloneConfiguration() {
//    }
//
//    public DistributedRedisStandaloneConfiguration(String clusterNodes, String password) {
//        Assert.notNull(clusterNodes, "HostAndPort must not be null!");
//        String _hostAndPort = this.getHostAndPort(clusterNodes);
//        Assert.notNull(_hostAndPort, "HostAndPort must not be null!");
//        String[] args = StringUtils.split(_hostAndPort, ":");
//        Assert.notNull(args, "HostAndPort need to be seperated by  ':'.");
//        Assert.isTrue(args.length == 2, "Host and Port String needs to specified as host:port");
//        this.setHostName(args[0]);
//        this.setPort(Integer.valueOf(args[1]));
//        if (password != null) {
//            this.setPassword(RedisPassword.of(password));
//        } else {
//            this.setPassword(RedisPassword.none());
//        }
//
//    }
//
//    private String getHostAndPort(String clusterNodes) {
//        if (clusterNodes.indexOf(",") != -1) {
//            String[] arrHostAndPort = StringUtils.split(clusterNodes, ",");
//            Assert.notNull(arrHostAndPort, "HostAndPort must not be null!");
//            return arrHostAndPort[0];
//        } else {
//            return clusterNodes;
//        }
//    }
//}
//
