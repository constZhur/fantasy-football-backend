package ru.mirea.fantasyfootballengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
//@EnableElasticsearchRepositories
@EnableScheduling
public class FantasyFootballEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasyFootballEngineApplication.class, args);
    }

}
