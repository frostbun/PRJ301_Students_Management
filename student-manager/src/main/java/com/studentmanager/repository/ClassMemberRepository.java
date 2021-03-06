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
    public Optional<ClassMember> findByAccountAndClassroom(Account account, Classroom classroom);
    public Optional<ClassMember> findByAccountAndClassroomId(Account account, Long cid);
    public Optional<ClassMember> findByAccountUsernameAndClassroom(String username, Classroom classroom);
    public Optional<ClassMember> findByAccountUsernameAndClassroomId(String username, Long cid);

    public List<ClassMember> findByClassroom(Classroom classroom, Pageable pageable);
    public List<ClassMember> findByClassroomAndAccountFirstNameContainsOrClassroomAndAccountLastNameContains(Classroom c1, String q1, Classroom c2, String q2, Pageable pageable);
    public Long countByClassroom(Classroom classroom);
    
    public List<ClassMember> findByAccountAndClassroomNameContains(Account account, String name, Pageable pageable);
    public Long countByAccountAndClassroomNameContains(Account account, String name);
    
    public List<ClassMember> findByClassroomAndRoleAndAccountFirstNameContainsOrClassroomAndRoleAndAccountLastNameContains(Classroom c1, String r1, String q1, Classroom c2, String r2, String q2, Pageable pageable);
    public Long countByClassroomAndRole(Classroom classroom, String role);
}
