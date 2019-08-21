package com.aplana.vin;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import com.aplana.redis.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VinController {
    private static JedisPoolConfig poolConfig = new JedisPoolConfig();
    private static GeneratorVin gv;
    private static Map<String, String> map = new HashMap<>();

    @Autowired
    private RedisConfig data;

    // Get request
    @GetMapping("/generateVin")
    public Map<String, String> vin() {
        map.put("vin", gv.generateVIN());
        return map;
    }

    @PostConstruct
    public void init() {
        Loader.setConfig(data);
        poolConfig.setMaxWaitMillis(Loader.getConfig().getMaxWaitMillis());
        poolConfig.setMaxTotal(Loader.getConfig().getMaxTotal());
        poolConfig.setMaxIdle(Loader.getConfig().getMaxIdle());
        poolConfig.setMinIdle(Loader.getConfig().getMinIdle());
        RedisPool.createRedisPool(poolConfig);
        gv = new GeneratorVin();
    }

}
