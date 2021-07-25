package com.tzh.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person implements BeanNameAware, ApplicationContextAware {
    private int id;
    private String name;
    private int age;
    private String nickname;

    private String beanName;
    private ApplicationContext applicationContext;

    public void print() {
        System.out.println("context.getBeanDefinitionNames()====>"
                + String.join(",", applicationContext.getBeanDefinitionNames()));
    }
}
