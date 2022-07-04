package com.studentmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    public Optional<Submission> findById(Long id);
    public Optional<Submission> findByAuthorAndHomework(Account author, Homework homework);
    public Optional<Submission> findByIdAndHomework(Long id, Homework homework);
    public Optional<Submission> findByIdAndHomeworkClassroom(Long id, Classroom classroom);

    public List<Submission> findByHomework(Homework homework, Pageable pageable);
    public Long countByHomework(Homework homework);
}
