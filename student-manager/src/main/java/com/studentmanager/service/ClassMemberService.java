package com.studentmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.repository.ClassMemberRepository;

@Service
public class ClassMemberService {
    @Autowired
    private ClassMemberRepository classMemberRepo;

    public void remove(ClassMember classMember) {
        classMemberRepo.delete(classMember);
    }

    public void promote(ClassMember classMember) {
        classMember.setRole(ClassMember.TEACHER);
        classMemberRepo.save(classMember);
    }

    public void demote(ClassMember classMember) {
        classMember.setRole(ClassMember.STUDENT);
        classMemberRepo.save(classMember);
    }

    public ClassMember getClassMember(String username, Long cid) {
        return classMemberRepo
            .findByAccountUsernameAndClassroomId(username, cid)
            .orElse(null);
    }

    public List<ClassMember> getClassMembers(Classroom classroom, String name, int page, int size) {
        return classMemberRepo.findByClassroomAndAccountFirstNameContainsOrClassroomAndAccountLastNameContains(
                classroom,
                name,
                classroom,
                name,
                PageRequest.of(
                    page,
                    size,
                    Sort.by("role").descending()
                        .and(Sort.by("account.firstName").ascending())
                        .and(Sort.by("account.lastName").ascending())
                )
            );
    }

    public Long countClassMembers(Classroom classroom) {
        return classMemberRepo.countByClassroom(classroom);
    }

    public List<Classroom> getClassrooms(Account account, String name, int page, int size) {
        return classMemberRepo.findByAccountAndClassroomNameContains(
                account,
                name,
                PageRequest.of(
                    page,
                    size,
                    Sort.by("createdAt").descending()
                )
            )
            .stream()
            .map(ClassMember::getClassroom)
            .collect(Collectors.toList());
    }

    public Long countClassrooms(Account account, String name) {
        return classMemberRepo.countByAccountAndClassroomNameContains(account, name);
    }

    private List<Account> getMembersByRole(Classroom classroom, String role, String name, int page, int size) {
        return classMemberRepo.findByClassroomAndRoleAndAccountFirstNameContainsOrClassroomAndRoleAndAccountLastNameContains(
                classroom,
                role,
                name,
                classroom,
                role,
                name,
                PageRequest.of(
                    page,
                    size,
                    Sort.by("account.firstName").ascending()
                        .and(Sort.by("account.lastName").ascending())
                )
            )
            .stream()
            .map(ClassMember::getAccount)
            .collect(Collectors.toList());
    }

    public List<Account> getStudents(Classroom classroom, String name, int page, int size) {
        return getMembersByRole(classroom, ClassMember.STUDENT, name,  page, size);
    }

    public List<Account> getTeachers(Classroom classroom, String name, int page, int size) {
        return getMembersByRole(classroom, ClassMember.TEACHER, name, page, size);
    }

    private Long countMembersByRole(Classroom classroom, String role) {
        return classMemberRepo.countByClassroomAndRole(classroom, role);
    }

    public Long countStudents(Classroom classroom) {
        return countMembersByRole(classroom, ClassMember.STUDENT);
    }

    public Long countTeachers(Classroom classroom) {
        return countMembersByRole(classroom, ClassMember.TEACHER);
    }
}
