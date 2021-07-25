package com.demo.starter;

public class StudentService {
    private int id;
    private String name;

    public StudentService(){}
    public StudentService(int id_,String name_){
        id=id_;
        name=name_;
    }
    public void showDetail(){
        System.out.println(id+":"+name);
    }
}
