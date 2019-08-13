package com.aplana.vin;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VinController {

    static Map<String, String> map = new HashMap<>();

    @Autowired
    private RedisConfig data;

    // Get request
    @GetMapping("")
    public Map<String, String> vin() {
        GeneratorVin gv = new GeneratorVin();
        map.put("vin", gv.getVin());
        return map;
    }

    @PostConstruct
    public void init() {
        Loader.setConfig(data);
    }

}
