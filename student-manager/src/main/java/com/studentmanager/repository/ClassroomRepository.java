package com.studentmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    public Optional<Classroom> findById(Long id);

    public Optional<Classroom> findByInviteCode(String inviteCode);
}
