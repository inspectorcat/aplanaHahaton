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

    static GeneratorVin gv;
    static Map<String, String> map = new HashMap<>();

    @Autowired
    private RedisConfig data;

    // Get request
    @GetMapping("")
    public Map<String, String> vin() {
        map.put("vin", gv.getVin());
        return map;
    }

    @PostConstruct
    public void init() {
        Loader.setConfig(data);
        gv = new GeneratorVin();
    }

}
