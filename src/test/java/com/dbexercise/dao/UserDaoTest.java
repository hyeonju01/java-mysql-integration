package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Autowired
    ApplicationContext context;
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        String id = "23";
        userDao.add(new User(id, "EternityHwan", "password"));
        User user = userDao.findById(id);
        assertEquals("EternityHwan", user.getName());
        assertEquals("password", user.getPassword());
    }
}