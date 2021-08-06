package com.example.jdbctest;


import com.example.jdbctest.entity.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@SpringBootApplication

public class JdbcTestApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-beans.xml");

        UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("UserJDBCTemplate");


        //System.out.println("------Records Creation--------");
        //userJDBCTemplate.addUser(new User(6, "Maxsu"));
        //userJDBCTemplate.addUser(new User(7, "Curry"));
        //userJDBCTemplate.addUser(new User(8, "Suzend"));

        //System.out.println("------Listing Multiple Records--------");
        List<User> users = userJDBCTemplate.listUsers();
        for (User record : users) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());

            //userJDBCTemplate.appendSqlAdd();
            //    userJDBCTemplate.dynamicSqlAdd();
            //    userJDBCTemplate.batchAdd();
        }
    }
}


