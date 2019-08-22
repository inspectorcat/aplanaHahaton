package com.aplana.vin;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import com.aplana.redis.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Controller
public class VinController {
    private static JedisPoolConfig poolConfig = new JedisPoolConfig();
    private static GeneratorVin gv;

    @Autowired
    private RedisConfig data;

    // Get request
    @GetMapping("/generatevin")
    public String vin(Model model) {
        model.addAttribute("vin", gv.generateVIN());
        return "generatevin";
    }

    // Get request
    @GetMapping("/trytogenerate")
    public String vinGui() {
        return "trytogenerate";
    }

    // Get request
    @GetMapping("")
    public String vinGuiRedirect() {
        return "redirect:/trytogenerate";
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
