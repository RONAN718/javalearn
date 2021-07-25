package com.demo.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "student")
@Data
public class StudentProperties {
    private int id;
    private String name;
}
