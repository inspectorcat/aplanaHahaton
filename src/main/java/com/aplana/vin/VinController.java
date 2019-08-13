package com.aplana.vin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VinController {

    static Map<String, String> map = new HashMap<>();

    // Get request
    @GetMapping("")
    public Map<String, String> vin() {
        GeneratorVin gv = new GeneratorVin();
        map.put("vin", gv.getVin());
        return map;
    }
}
