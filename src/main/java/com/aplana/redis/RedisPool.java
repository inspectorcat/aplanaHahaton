package com.aplana.redis;

import com.aplana.config.Loader;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private static JedisPool redisPool;


    public static JedisPool getRedisPool() {
        return redisPool;
    }


    public static void createRedisPool(JedisPoolConfig poolConfig) {
        redisPool= new JedisPool(poolConfig, Loader.getConfig().getHost(),Loader.getConfig().getPort());
    }
}
