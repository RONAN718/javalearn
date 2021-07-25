package com.demo.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(StudentService.class)
@EnableConfigurationProperties(StudentProperties.class)
public class StudentConfig {
    @Autowired
    private  StudentProperties student;

   @Bean
   @ConditionalOnMissingBean
   @ConditionalOnProperty(prefix = "student",value = "enabled",havingValue = "true")
    public StudentService service(){
       return new StudentService(student.getId(),student.getName());
   }
}
