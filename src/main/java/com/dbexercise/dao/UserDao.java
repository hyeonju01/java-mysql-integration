package com.dbexercise.dao;

import com.dbexercise.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {

    // ver1. connection 정보 분리
    // ver2. interface 실습
    private ConnectionMaker connectionMaker = new AwsConnectionMaker();

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    //INSERT문
    public void add(User user) throws SQLException, ClassNotFoundException {
            Connection conn = connectionMaker.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(id, name, password) VALUES (?, ?, ?)"
            );
            ps.setString(1, user.getId()); //2번째 인자 - 객체.메소드 사용
            ps.setString(2, user.getName()); //2번째 인자 - 객체.메소드 사용
            ps.setString(3, user.getPassword()); //2번째 인자 - 객체.메소드 사용

            ps.executeUpdate(); //int 반환

            //db 접속 종료
            ps.close();
            conn.close();
    }

    //SELECT문
    public User findById(String id) throws SQLException, ClassNotFoundException {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = connectionMaker.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users where id = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

            //db 접속 종료
            rs.close();
            pstmt.close();
            conn.close();

            return user;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao(() -> {
            return null;
        }); //이 코드의 의미는 ?

        User user = new User("13", "hj", "password");
        userDao.add(user);
    }
}