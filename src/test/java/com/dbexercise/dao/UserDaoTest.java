package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1 = new User("30", "kyeonghwan", "1123");
    User user2 = new User("40", "sujin", "1234");
    User user3 = new User("50", "sohyun", "4321");

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
    }
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("User가 null인 경우 Exception")
    void userNull() throws SQLException, ClassNotFoundException {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.deleteAll();
            userDao.findById("0");
        });
    }
}