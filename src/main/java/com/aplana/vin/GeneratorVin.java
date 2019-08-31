package com.aplana.vin;

import com.aplana.config.Loader;
import com.aplana.redis.RedisPool;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GeneratorVin {
    private final static java.lang.String REDIS_DELIMITER = ";";
    private static final Logger logger = LoggerFactory.getLogger(GeneratorVin.class);
    private static Map<java.lang.String, Integer> map = new HashMap<>();

    // Map with bound of letters and numbers
    static {
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 4);
        map.put("E", 5);
        map.put("F", 6);
        map.put("G", 7);
        map.put("H", 8);
        map.put("J", 1);
        map.put("K", 2);
        map.put("L", 3);
        map.put("M", 4);
        map.put("N", 5);
        map.put("P", 7);
        map.put("R", 9);
        map.put("S", 2);
        map.put("T", 3);
        map.put("U", 4);
        map.put("V", 5);
        map.put("W", 6);
        map.put("X", 7);
        map.put("Y", 8);
        map.put("Z", 9);
    }

    AtomicInteger i = new AtomicInteger(0);

    // Get Redis connection parameters from config and create connection
    public GeneratorVin() {

    }

    // Get data from redis
    private java.lang.String[] getDateFromRedis() {
        try (Jedis jedis = RedisPool.getRedisPool().getResource()) {
            java.lang.String str = jedis.srandmember(Loader.getConfig().getNameSet());
            return str.split(REDIS_DELIMITER);
        }
    }

    // Generate random 6-digit string
    private java.lang.String generateSerialNumber() {
        return RandomStringUtils.randomNumeric(6);
    }

    // Build VIN-number
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<VinNumber> generateVIN() {
        long time1 = System.currentTimeMillis();
        i.incrementAndGet();
        VinNumber vinNumber = new VinNumber();
        java.lang.String[] data = getDateFromRedis();
        vinNumber.setWmi(data[0]);
        vinNumber.setVds(data[1]);
        vinNumber.setYear(data[2]);
        vinNumber.setSerialNumber(generateSerialNumber());
        vinNumber.setCheckDigit(calculateCheckDigit(vinNumber));

        long time2 = System.currentTimeMillis();
        logger.info("Был сгенерирован номер " + vinNumber + " за время " + (time2 - time1) + " " + i);
        return CompletableFuture.completedFuture(vinNumber);
    }

    // Calculate check digit -.-
    private java.lang.String calculateCheckDigit(VinNumber vinNumber) {
        char[] wmi = vinNumber.getWmi().toCharArray();
        char[] vds = vinNumber.getVds().toCharArray();
        char[] year = vinNumber.getYear().toCharArray();
        char[] serialNumber = vinNumber.getSerialNumber().toCharArray();
        int weight = 0;

        for (int i = 0; i < wmi.length; i++) {
            weight += getWeight(wmi[i]) * (8 - i);
        }

        for (int i = 0; i < vds.length; i++) {
            if (i == 4) {
                weight += getWeight(vds[i]) * 10;
            }
            weight += getWeight(vds[i]) * (5 - i);
        }

        weight += getWeight(year[0]) * 9;
        // Using static letter "A" on 11th number of VIN with weight 8
        weight += 8;

        for (int i = 0; i < serialNumber.length; i++) {
            weight += serialNumber[i] * 7 - i;
        }

        int diff = weight - weight / 11 * 11;
        if (diff == 10) {
            return "X";
        }
        return java.lang.String.valueOf(diff);
    }

    // Return number equivalent of letter or a number
    private int getWeight(char chars) {
        for (Map.Entry<java.lang.String, Integer> entry : map.entrySet()) {
            if (java.lang.String.valueOf(chars).toLowerCase().equals(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return chars;
    }
}
