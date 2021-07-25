package com.tzh.demo.bean.componentTest;


import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Data
@ToString
@Component("person01")
public class Person {
    @Value("2")
    private int id;
    @Value("ttzh")
    private String name;
    @Value("23")
    private int age;
    @Value("personT")
    private String nickname;
}
