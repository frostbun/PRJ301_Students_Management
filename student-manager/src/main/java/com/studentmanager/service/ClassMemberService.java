package com.studentmanager.service;

import java.util.List;
import java.util.Optional;
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
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroomId(account, cid);
        if (!cmOptional.isPresent()) {
            return null;
        }
        return cmOptional.get();
    }

    public ClassMember getClassMember(Account account, Classroom classroom) {
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroom(account, classroom);
        if (!cmOptional.isPresent()) {
            return null;
        }
        return cmOptional.get();
    }

    public Classroom getClassroom(Account account, Long cid) {
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroomId(account, cid);
        if (!cmOptional.isPresent()) {
            return null;
        }
        return cmOptional.get().getClassroom();
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
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountUsernameAndClassroom(username, classroom);
        if (!cmOptional.isPresent()) {
            return null;
        }
        return cmOptional.get().getAccount();
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
}
