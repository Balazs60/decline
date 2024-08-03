package com.github.balazs60.decline.generator;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomGenerator {
    @Bean
    public Random random() {
        return new Random();
    }
}
