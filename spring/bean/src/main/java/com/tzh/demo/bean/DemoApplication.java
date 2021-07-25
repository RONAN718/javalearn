package com.tzh.demo.bean;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class DemoApplication  {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //Person person=context.getBean(Person.class);
        Person person = (Person) context.getBean("ronan0");
        System.out.println(person.toString());
        person.print();

        Group group = (Group) context.getBean(Group.class);
        System.out.println(group);
        group.showPersons();

//Autowired
        Company company = (Company) context.getBean(Company.class);
        System.out.println(company);
        company.showDetail();
//Component
        ApplicationContext context2=new AnnotationConfigApplicationContext("com.tzh.demo.bean");
        com.tzh.demo.bean.componentTest.Person person1=(com.tzh.demo.bean.componentTest.Person)context2.getBean("person01");
        System.out.println(person1.toString());
//Bean
        System.out.println(context2.getBean("Boss"));
    }
}
