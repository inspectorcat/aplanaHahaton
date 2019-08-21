package com.aplana.vin;

import com.aplana.config.Loader;
import com.aplana.redis.RedisPool;
import org.apache.commons.lang3.RandomStringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class GeneratorVin {
    private final static String REDIS_DELIMITER = ";";
    private static Map<String, Integer> map = new HashMap<>();

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

    // Get Redis connection parameters from config and create connection
    public GeneratorVin() {

    }

    // Get data from redis
    private String[] getDateFromRedis() {
        try (Jedis jedis = RedisPool.getRedisPool().getResource()) {
            String str = jedis.srandmember(Loader.getConfig().getNameSet());
            return str.split(REDIS_DELIMITER);
        }

    }

    // Generate random 6-digit string
    private String generateSerialNumber() {
        return RandomStringUtils.randomNumeric(6);
    }

    // Build VIN-number
    public String generateVIN() {
        VinNumber vinNumber = new VinNumber();
        String[] data = getDateFromRedis();
        vinNumber.setWmi(data[0]);
        vinNumber.setVds(data[1]);
        vinNumber.setYear(data[2]);
        vinNumber.setSerialNumber(generateSerialNumber());
        vinNumber.setCheckDigit(calculateCheckDigit(vinNumber));
        return vinNumber.toString().toUpperCase();
    }

    // Calculate check digit -.-
    private String calculateCheckDigit(VinNumber vinNumber) {
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
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (java.lang.String.valueOf(chars).toLowerCase().equals(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return chars;
    }
}
