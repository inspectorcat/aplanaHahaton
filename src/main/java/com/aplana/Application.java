package com.aplana;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


@SpringBootApplication
public class Application {

    private static ClassLoader classLoader;

    public static void main(String[] args) {
        classLoader = Thread.currentThread().getContextClassLoader();
        setDataLoader();
        SpringApplication.run(Application.class, args);
    }

    private static void setDataLoader() {
        Constructor constructor = new Constructor(RedisConfig.class);
        Yaml yaml = new Yaml(constructor);
        InputStream input = null;

        // Maven run
        try {
            input = new FileInputStream(new File("config.yml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // For local run change path
        finally {
            try {
                input = new FileInputStream(new File("C:\\Users\\tmantashyan\\Desktop\\IDEA WorkFlow\\demo1\\src\\main\\resources\\config.yml"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        RedisConfig data = yaml.loadAs(input, RedisConfig.class);
        Loader.setConfig(data);
    }

}
