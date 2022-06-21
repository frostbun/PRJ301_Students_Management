package com.studentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.repository.ClassMemberRepository;
import com.studentmanager.repository.ClassroomRepository;

@Service
public class ClassroomService {
    @Autowired
    private SessionService session;
    @Autowired
    private ClassroomRepository classroomRepo;
    @Autowired
    private ClassMemberRepository classMemberRepo;

    public Long create(String name) {
        Account account = session.getCurrentAccount();
        Classroom classroom = Classroom.builder().name(name).build();
        classroomRepo.save(classroom);
        classMemberRepo.save(
            ClassMember
                .builder()
                .account(account)
                .classroom(classroom)
                .role(ClassMember.TEACHER)
                .build()
        );
        return classroom.getId();
    }

    public Long join(Model view, String inviteCode) {
        Optional<Classroom> cOptional = classroomRepo.findByInviteCode(inviteCode);
        if (!cOptional.isPresent()) {
            view.addAttribute("error", "Wrong invite code");
            return null;
        }
        Account account = session.getCurrentAccount();
        Classroom classroom = cOptional.get();
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroom(account, classroom);
        if (cmOptional.isPresent()) {
            view.addAttribute("error", "Already in class");
            return null;
        }
        classMemberRepo.save(
            ClassMember
                .builder()
                .account(account)
                .classroom(classroom)
                .role(ClassMember.STUDENT)
                .build()
        );
        return classroom.getId();
    }

    public Classroom getClassroomById(Long id) {
        Optional<Classroom> cOptional = classroomRepo.findById(id);
        if (!cOptional.isPresent()) {
            return null;
        }
        Account account = session.getCurrentAccount();
        Classroom classroom = cOptional.get();
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroom(account, classroom);
        if (!cmOptional.isPresent()) {
            return null;
        }
        return classroom;
    }

    public List<Classroom> getJoinedClassrooms(int page, int size) {
        Account account = session.getCurrentAccount();
        return classMemberRepo
            .findByAccount(
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

    public List<Account> getClassroomMembers(Long id, int page, int size) {
        Optional<Classroom> cOptional = classroomRepo.findById(id);
        if (!cOptional.isPresent()) {
            return new ArrayList<>();
        }
        return classMemberRepo
            .findByClassroom(
                cOptional.get(),
                PageRequest.of(
                    page,
                    size,
                    Sort.by("firstName").ascending()
                        .and(Sort.by("lastName").ascending())
                )
            )
            .stream()
            .map(ClassMember::getAccount)
            .collect(Collectors.toList());
    }
}
