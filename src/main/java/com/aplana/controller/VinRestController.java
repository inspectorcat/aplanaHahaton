package com.aplana.controller;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import com.aplana.data.DataContainer;
import com.aplana.redis.RedisPool;
import com.aplana.vin.GeneratorVin;
import com.aplana.vin.VinContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.JedisPoolConfig;


import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class VinRestController {
    private static JedisPoolConfig poolConfig = new JedisPoolConfig();
    private static GeneratorVin gv;

    @Autowired
    private RedisConfig data;


    // Get request
    @GetMapping("/restvin")
    public CompletableFuture<VinContainer> vin() {
        return gv.generateVIN().thenApply(vinNumber -> new VinContainer(vinNumber));
    }

    //Загрузка поступающих данных из Json
    @PostMapping(value = "/loadData", consumes = "application/json")
    public void loadData(@RequestBody DataContainer jsonArray) {
        jsonArray.getParams().forEach(parameter -> {
            Loader.getParametersMap().put(parameter.getName(),
                    Arrays.stream(parameter.getValues()).collect(Collectors.toSet()));
        });
    }


//    @PostConstruct
//    public void init() {
//        Loader.setConfig(data);
//        poolConfig.setMaxWaitMillis(Loader.getConfig().getMaxWaitMillis());
//        poolConfig.setMaxTotal(Loader.getConfig().getMaxTotal());
//        poolConfig.setMaxIdle(Loader.getConfig().getMaxIdle());
//        poolConfig.setMinIdle(Loader.getConfig().getMinIdle());
//        RedisPool.createRedisPool(poolConfig);
//        gv = new GeneratorVin();
//    }


}
