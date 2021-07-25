package com.tzh.demo.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@Data
public class Company {
    @Autowired(required = true)
    Group group1;
    @Resource(name = "ronan0")
    Person person0;

    public void showDetail() {

        System.out.println("group1 have " + this.group1.getPersons().size() + " Persons and one is " + this.person0);

    }
}
