package com.aplana.controller;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import com.aplana.redis.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Controller
public class FakeController {
    // Get request
    @GetMapping("/vin")
    public String vinGui() {
        return "fakevin";
    }

    // Get request
    @GetMapping("")
    public String vinGuiRedirect() {
        return "redirect:/vin";
    }

    // Get request
    @GetMapping("/okay")
    public String okPressButton() {
        return "okay";
    }




}
