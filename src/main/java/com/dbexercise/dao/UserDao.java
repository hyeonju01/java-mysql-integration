package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Map;

public class UserDao {

    // ver1. connection 정보 분리
    // ver2. interface 실습
    // ver3. UserDaoFactory 로 연결
    // ver4. deleteAll(), getCount() 메서드 추가
    // ver5. dataSource 적용
    private DataSource dataSource;
    private ConnectionMaker connectionMaker;
    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

//    public UserDao(ConnectionMaker connectionMaker) {
//        this.connectionMaker = connectionMaker;
//    }
    public UserDao(AwsConnectionMaker dataSource) {
        this.dataSource = dataSource;
    }

    // ? 일단 적기는 하는데 무슨 역할을 하는지는 모르겠는 메서드
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = dataSource.getConnection(); // datasource를 통해 getConnection
            ps = stmt.makePreparedStatement(conn);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    //INSERT문
//    public void add(User user) throws SQLException, ClassNotFoundException {
//            Map<String, String> env = System.getenv();
//            try {
//                Connection conn = connectionMaker.getConnection();
//                PreparedStatement ps = conn.prepareStatement(
//                        "INSERT INTO users(id, name, password) VALUES (?, ?, ?)"
//                );
//                ps.setString(1, user.getId()); //2번째 인자 - 객체.메소드 사용
//                ps.setString(2, user.getName()); //2번째 인자 - 객체.메소드 사용
//                ps.setString(3, user.getPassword()); //2번째 인자 - 객체.메소드 사용
//
//                ps.executeUpdate(); //int 반환
//
//                //db 접속 종료
//                ps.close();
//                conn.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//    }


    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.getConnection();
            ps = conn.prepareStatement("SELECT count(*) FROM users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {

                }

            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {

                }

            }
            if (conn != null) {
                try {
                    conn.close();
                }catch (SQLException e) {

                }
            }
        }
    }
    public void add(User user) throws SQLException, ClassNotFoundException {
        AddStrategy addStrategy = new AddStrategy(user);
        jdbcContextWithStatementStrategy(addStrategy);
    }

    //SELECT문
    public User findById(String id) throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv();
        Connection conn;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = connectionMaker.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users where id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            rs.next();

            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

            //db 접속 종료
            rs.close();
            pstmt.close();
            conn.close();

            if (user == null) throw new EmptyResultDataAccessException(1);

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao(); //이 코드의 의미는 ?
        User user = new User("13", "hj", "password");
        userDao.add(user);
    }
}