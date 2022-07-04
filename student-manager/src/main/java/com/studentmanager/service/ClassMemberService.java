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

    public Classroom getClassroom(Account account, Long cid) {
        return classMemberRepo
            .findByAccountAndClassroomId(account, cid)
            .map(ClassMember::getClassroom)
            .orElse(null);
    }

    public List<Classroom> getClassrooms(Account account, int page, int size) {
        return classMemberRepo.findByAccount(
                account,
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

    public Long countClassrooms(Account account) {
        return classMemberRepo.countByAccount(account);
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
                        .and(Sort.by("firstName").ascending())
                        .and(Sort.by("lastName").ascending())
                )
            )
            .stream()
            .map(ClassMember::getAccount)
            .collect(Collectors.toList());
    }

    public Long countMembers(Classroom classroom) {
        return classMemberRepo.countByClassroom(classroom);
    }

    public Long countStudents(Classroom classroom) {
        return classMemberRepo.countByClassroomAndRole(classroom, ClassMember.STUDENT);
    }
}
