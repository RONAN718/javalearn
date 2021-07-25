package com.tzh.demo.bean;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private String groupName;
    List<Person> persons;

    public void showPersons() {
        System.out.println(this.getPersons());
    }
}
