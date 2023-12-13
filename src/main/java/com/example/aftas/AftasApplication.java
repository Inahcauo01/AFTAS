package com.example.aftas;

import com.example.aftas.seeder.FishSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AftasApplication {

    public static void main(String[] args) {
        SpringApplication.run(AftasApplication.class, args);
    }


//    @Bean
//    public CommandLineRunner dataLoader(FishSeeder fishSeeder) {
//        return args -> {
//            fishSeeder.seedFish();
//        };
//    }
}
