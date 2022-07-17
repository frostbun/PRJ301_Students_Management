package com.studentmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.Comment;
import com.studentmanager.model.Homework;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    public Optional<Comment> findById(Long id);

    public List<Comment> findByHomework(Homework homework);
}
