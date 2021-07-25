package com.tzh.demo.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Boss {
    @Bean(name = "Boss")
    public String check() {
        String str = "l'm big boss!!!";
        return str;
    }
}
