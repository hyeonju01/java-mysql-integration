package com.dbexercise.dao;

import com.dbexercise.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        String id = "22";
        User user = new User(id, "Rara", "112233");
        userDao.add(user);

        User selectedUser = userDao.findById(id);
        Assertions.assertEquals("Rara", selectedUser.getName());
    }
}