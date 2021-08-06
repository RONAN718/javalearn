package com.example.jdbctest.DAO;

import com.example.jdbctest.entity.User;

import javax.sql.DataSource;
import java.util.List;

public interface UserDAO {
    public void setDataSource(DataSource dataSource);
    List<User> listUsers();
    public boolean addUser(User user);
    public void batchAdd();
    public void appendSqlAdd();
    public void dynamicSqlAdd();
}
