package com.studentmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.User;

@Repository
public class UserDAO {
    @Autowired
    private JdbcTemplate db;

    public boolean create(User user) {
        return db.update(
            "INSERT INTO [User](username, password) VALUES (?, ?)",
            user.getUsername(),
            user.getPassword()
        ) > 0;
    }

    public User readByUsername(String username) {
        try {
            return db.queryForObject(
                "SELECT * FROM [User] WHERE username=?",
                BeanPropertyRowMapper.newInstance(User.class),
                username
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean updateByUsername(User user) {
        return db.update(
            ""
        ) > 0;
    }

    public boolean deleteByUsername(User user) {
        return db.update(
            "UPDATE [User] SET deleted=1 WHERE username=?",
            user.getUsername()
        ) > 0;
    }
}
