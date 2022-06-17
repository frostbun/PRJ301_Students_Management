package com.studentmanager.repository;

import java.util.List;

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

    public boolean create(
        String username,
        String password,
        String firstName,
        String lastName,
        String phone,
        String email,
        String address
    ) {
        return db.update(
            "INSERT INTO "
                + "[User](username, password, firstName, lastName, phone, email, address) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
            username,
            password,
            firstName,
            lastName,
            phone,
            email,
            address
        ) > 0;
    }

    public User readByUsername(String username) {
        try {
            return db.queryForObject(
                "SELECT * FROM [User] WHERE username=?",
                BeanPropertyRowMapper.newInstance(User.class),
                username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> readByClassID(int classID, int page, int size) {
        return db.query(
            "SELECT [User].* "
                + "FROM [User] "
                + "JOIN ClassMember "
                + "ON User.username = ClassMember.username "
                + "WHERE classID=? "
                + "OFFSET ? "
                + "FETCH NEXT ? ROWS ONLY",
            BeanPropertyRowMapper.newInstance(User.class),
            classID,
            (page - 1) * size,
            size
        );
    }

    public boolean updateInformation(
        String username,
        String firstName,
        String lastName,
        String phone,
        String email,
        String address
    ) {
        return db.update(
            "UPDATE [User] "
                + "SET firstName=?, lastName=?, phone=?, email=?, address=? "
                + "WHERE username=?",
            firstName,
            lastName,
            phone,
            email,
            address,
            username
        ) > 0;
    }

    public boolean updatePassword(String username, String password) {
        return db.update(
            "UPDATE [User] SET password=? WHERE username=?",
            password,
            username
        ) > 0;
    }
    
    public boolean delete(String username) {
        return db.update(
            "UPDATE [User] SET deleted=1 WHERE username=?",
            username
        ) > 0;
    }
}
