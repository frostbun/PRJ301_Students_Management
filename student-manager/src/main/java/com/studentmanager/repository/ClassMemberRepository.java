package com.studentmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Account;

@Repository
public interface ClassMemberRepository extends JpaRepository<ClassMember, Long> {

    public Optional<ClassMember> findById(Long id);

    public List<ClassMember> findByClassroom(Classroom classroom, Pageable pageable);

    public List<ClassMember> findByAccount(Account account, Pageable pageable);

    public Optional<ClassMember> findByAccountAndClassroom(Account account, Classroom classroom);

    public Long countByAccount(Account account);

    public Long countByClassroom(Classroom classroom);
}
