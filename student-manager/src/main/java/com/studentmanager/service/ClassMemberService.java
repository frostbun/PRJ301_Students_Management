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

    public ClassMember getClassMember(Account account, Long cid) {
        return classMemberRepo
            .findByAccountAndClassroomId(account, cid)
            .orElse(null);
    }

    public ClassMember getClassMember(Account account, Classroom classroom) {
        return classMemberRepo
            .findByAccountAndClassroom(account, classroom)
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

    public Classroom getClassroom(Account account, Long cid) {
        return classMemberRepo
            .findByAccountAndClassroomId(account, cid)
            .map(ClassMember::getClassroom)
            .orElse(null);
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

    public Account getMember(String username, Classroom classroom) {
        return classMemberRepo
            .findByAccountUsernameAndClassroom(username, classroom)
            .map(ClassMember::getAccount)
            .orElse(null);
    }

    public List<Account> getMembers(Classroom classroom, int page, int size) {
        return classMemberRepo.findByClassroom(
                classroom,
                PageRequest.of(
                    page,
                    size,
                    Sort.by("role").descending()
                        .and(Sort.by("account.firstName").ascending())
                        .and(Sort.by("account.lastName").ascending())
                )
            )
            .stream()
            .map(ClassMember::getAccount)
            .collect(Collectors.toList());
    }

    public Long countMembers(Classroom classroom) {
        return classMemberRepo.countByClassroom(classroom);
    }

    public List<Account> getMembersByRole(Classroom classroom, String role, int page, int size) {
        return classMemberRepo.findByClassroomAndRole(
                classroom,
                role,
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

    public Long countMembersByRole(Classroom classroom, String role) {
        return classMemberRepo.countByClassroomAndRole(classroom, role);
    }
}
