package com.example.jdbctest;

import com.example.jdbctest.DAO.UserDAO;
import com.example.jdbctest.entity.User;
import com.example.jdbctest.mapper.UserMapper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
public class UserJDBCTemplate implements UserDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> listUsers() {
        String SQL = "select * from user";
        List<User> users = jdbcTemplate.query(SQL, new UserMapper());
        return users;
    }

    @Override
    public boolean addUser(User user) {
        // TODO Auto-generated method stub
        String insertQuery = "insert into user (id, name) values (?, ?)";

        jdbcTemplate.update(insertQuery, user.getId(), user.getName());
        System.out.println("Created Record Id = " + user.getId() + " name = " + user.getName());
        return true;

    }

    //批处理+预编译
    //spend 4583 milliseconds to insert 1000000
    @Override
    public void batchAdd() {
        Connection connection = null;
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false&rewriteBatchedStatements=true", "root", "980718");
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long begin = System.currentTimeMillis();

        String prefix = "insert into user (id,name) values (?,?)";
        try {
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(prefix);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 100000; j++) {
                    String no = String.valueOf(i * 100000 + j);
                    pst.setInt(1, i * 100000 + j);
                    pst.setString(2, no);
                    pst.addBatch();
                }
                pst.executeBatch();
                connection.commit();
            }
            pst.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("spend " + (end - begin) + " milliseconds to insert 1000000");
    }

    //多值拼装+非预编译
    //spend 8411 milliseconds to insert 1000000
    @Override
    public void appendSqlAdd() {
        Connection connection = null;
        Statement st = null;
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false", "root", "980718");
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long begin = System.currentTimeMillis();
        StringBuffer prefix = new StringBuffer("insert into user (id,name) values ");
        try {
            st = (Statement) connection.createStatement();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 100000; j++) {
                    if ((j) != 0) {
                        prefix.append(",");
                    }
                    String no = String.valueOf(i * 100000 + j);
                    System.out.println(no);
                    prefix.append("(" + no + "," + no + ")");
                }
                st.execute(prefix.toString());
                connection.commit();
                prefix = new StringBuffer("insert into user (id,name) values ");
            }
            st.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("spend " + (end - begin) + " milliseconds to insert 1000000");

    }

    // SQL预编译+多值拼装sql
    // spend 8973 milliseconds to insert 1000000
    @Override
    public void dynamicSqlAdd() {
        Connection connection = null;
        PreparedStatement st = null;
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useSSL=false", "root", "980718");
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long begin = System.currentTimeMillis();
        StringBuffer prefix = new StringBuffer("insert into user (id,name) values ");
        try {

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 100000; j++) {
                    if ((j) != 0) {
                        prefix.append(",");
                    }
                    String no = String.valueOf(i * 100000 + j);
                    System.out.println(no);
                    prefix.append("(" + no + "," + no + ")");
                }
                st = (PreparedStatement) connection.prepareStatement(prefix.toString());
                st.execute();
                connection.commit();
                prefix = new StringBuffer("insert into user (id,name) values ");
            }
            st.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("spend " + (end - begin) + " milliseconds to insert 1000000");
    }
}
