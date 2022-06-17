package com.studentmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.Class;

@Repository
public class ClassDAO {
    @Autowired
    private JdbcTemplate db;

    public boolean create(Class _class) {
        return db.update(
            "INSERT INTO Class(className, inviteCode, coverURL) VALUES (?, ?)",
            _class.getClassName(),
            _class.getInviteCode(),
            _class.getCoverURL()
        ) > 0;
    }
}
