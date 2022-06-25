package com.studentmanager.service;

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
import com.studentmanager.model.Homework;
import com.studentmanager.repository.ClassMemberRepository;
import com.studentmanager.repository.ClassroomRepository;
import com.studentmanager.repository.HomeworkRepository;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepo;
    @Autowired
    private ClassMemberRepository classMemberRepo;
    @Autowired
    private HomeworkRepository homeworkRepo;

    public Long create(Model view, Account account, String name) {
        return classMemberRepo.save(
                ClassMember.builder()
                    .account(account)
                    .classroom(
                        classroomRepo.save(Classroom
                            .builder()
                            .name(name)
                            .build()
                        )
                    )
                    .role(ClassMember.TEACHER)
                    .build()
            )
            .getClassroom()
            .getId();
    }

    public Long join(Model view, Account account, String inviteCode) {
        Optional<Classroom> cOptional = classroomRepo.findByInviteCode(inviteCode);
        if (!cOptional.isPresent()) {
            view.addAttribute("error", "Wrong invite code");
            return null;
        }
        Classroom classroom = cOptional.get();
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroom(account, classroom);
        if (cmOptional.isPresent()) {
            return cmOptional.get().getClassroom().getId();
        }
        return classMemberRepo.save(
                ClassMember.builder()
                    .account(account)
                    .classroom(classroom)
                    .role(ClassMember.STUDENT)
                    .build()
            )
            .getClassroom()
            .getId();
    }

    public Classroom getClassroomById(Long id, Account account) {
        Optional<Classroom> cOptional = classroomRepo.findById(id);
        if (!cOptional.isPresent()) {
            return null;
        }
        Classroom classroom = cOptional.get();
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroom(account, classroom);
        if (!cmOptional.isPresent()) {
            return null;
        }
        return classroom;
    }

    public String getAccountRole(Account account, Classroom classroom) {
        return classMemberRepo.findByAccountAndClassroom(account, classroom).get().getRole();
    }

    public List<Classroom> getJoinedClassrooms(Account account, int page, int size) {
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

    public Long countJoinedClassrooms(Account account) {
        return classMemberRepo.countByAccount(account);
    }

    public List<Account> getClassroomMembers(Classroom classroom, int page, int size) {
        return classMemberRepo.findByClassroom(
                classroom,
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

    public Long countClassroomMembers(Classroom classroom) {
        return classMemberRepo.countByClassroom(classroom);
    }

    public List<Homework> getClassroomHomeworks(Classroom classroom, int page, int size) {
        return homeworkRepo.findByClassroom(
            classroom,
            PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
            )
        );
    }

    public Long countClassroomHomeWorks(Classroom classroom) {
        return homeworkRepo.countByClassroom(classroom);
    }
}
