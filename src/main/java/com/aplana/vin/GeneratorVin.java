package com.aplana.vin;

import com.aplana.config.Loader;
import com.aplana.redis.RedisPool;
import org.apache.commons.lang3.RandomStringUtils;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GeneratorVin {

    private String wmi;
    private String vds;
    private String year;
    private String serialNumber;

    private static JedisPoolConfig poolConfig = new JedisPoolConfig();

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

    public java.lang.String getWmi() {
        return wmi;
    }

    public void setWmi(java.lang.String wmi) {
        this.wmi = wmi;
    }

    public java.lang.String getVds() {
        return vds;
    }

    public void setVds(java.lang.String vds) {
        this.vds = vds;
    }

    public java.lang.String getYear() {
        return year;
    }

    public void setYear(java.lang.String year) {
        this.year = year;
    }

    public java.lang.String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(java.lang.String serialNumber) {
        this.serialNumber = serialNumber;
    }

    // Get Redis connection parameters from config and create connection
    public GeneratorVin() {
        poolConfig.setMaxWaitMillis(Loader.getConfig().getMaxWaitMillis());
        poolConfig.setMaxTotal(Loader.getConfig().getMaxTotal());
        poolConfig.setMaxIdle(Loader.getConfig().getMaxIdle());
        poolConfig.setMinIdle(Loader.getConfig().getMinIdle());
        RedisPool.createRedisPool(poolConfig);

    }

    // Get data from redis
    private void getDateFromRedis() {
        try(Jedis jedis = RedisPool.getRedisPool().getResource()) {
            String str = jedis.srandmember("testSet");
            String delimiter = ";";
            String[] subStr = str.split(delimiter);
            this.wmi = subStr[0];
            this.vds = subStr[1];
            this.year = subStr[2];
            this.serialNumber = generateSerialNumber();
        }

    }

    // Generate random 6-digit string
    private String generateSerialNumber() {
        return RandomStringUtils.randomNumeric(6);
    }

    // Build VIN-number
    public String getVin() {
        StringBuilder vin = new StringBuilder();
        getDateFromRedis();
        vin.append(getWmi())
                .append(getVds())
                .append(calculateCheckDigit())
                .append(getYear())
                .append("A")
                .append(getSerialNumber());
        return vin.toString().toUpperCase();
    }

    // Calculate check digit -.-
    private String calculateCheckDigit() {
        char[] wmi = getWmi().toCharArray();
        char[] vds = getVds().toCharArray();
        char[] year = getYear().toCharArray();
        char[] serialNumber = getSerialNumber().toCharArray();
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
            return  "X";
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
