package com.aplana;

import com.aplana.config.Loader;
import com.aplana.config.RedisConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.dataformat.csv.CsvMapper;
//import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.*;
import java.util.*;


@SpringBootApplication
@EnableConfigurationProperties(RedisConfig.class)
@EnableAsync
public class Application {

    public static void main(String[] args) {
        loadTestParam();
        try {
            createJsonForTest(new File("C:\\Users\\user\\Desktop\\test.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(Application.class, args);
    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }


    private static void loadTestParam()
    {
        Set<String> values_1 = new HashSet<>();
        Set<String> values_2 = new HashSet<>();
        values_1.add("name_1");
        values_1.add("name_2");

        values_2.add("lastname_1");
        values_2.add("lastname_2");
        Map<String, Set<String>> parametersMap = new HashMap<>();

        parametersMap.put("name",values_1);
        parametersMap.put("lastname",values_2);
        Loader.setParametersMap(parametersMap);
    }
//
//
    private static void createJsonForTest(File file) throws IOException {
        File input = file;


        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

        ObjectMapper mapper = new ObjectMapper();

        // Write JSON formated data to stdout
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));

    }


}
