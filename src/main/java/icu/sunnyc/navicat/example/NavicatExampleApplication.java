package icu.sunnyc.navicat.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@EnableScheduling
@SpringBootApplication
public class NavicatExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NavicatExampleApplication.class, args);
    }

}
