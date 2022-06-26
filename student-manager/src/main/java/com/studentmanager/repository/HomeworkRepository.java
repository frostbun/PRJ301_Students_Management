package com.studentmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {

    public Optional<Homework> findById(Long id);
    public Optional<Homework> findByIdAndClassroom(Long id, Classroom classroom);

    public List<Homework> findByClassroom(Classroom classroom, Pageable pageable);
    public Long countByClassroom(Classroom classroom);
}
