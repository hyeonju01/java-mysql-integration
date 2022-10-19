package com.dbexercise.dao;

import com.dbexercise.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {

    // connection 정보 분리
    private Connection makeConnection() throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv();
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD")
        );
        return conn;
    }

    //INSERT문
    public void add(User user) throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = makeConnection();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //SELECT문
    public User findById(String id) throws ClassNotFoundException {
        Map<String, String> env = System.getenv();
        Connection conn;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = makeConnection();
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();

        User user = new User("13", "hj", "password");
        userDao.add(user);
    }
}