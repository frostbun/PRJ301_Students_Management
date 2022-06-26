package com.studentmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.repository.ClassMemberRepository;
import com.studentmanager.repository.ClassroomRepository;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepo;
    @Autowired
    private ClassMemberRepository classMemberRepo;

    public ServiceResponse<Classroom> create(Account account, String name) {
        return ServiceResponse.success(
            classMemberRepo.save(
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
        );
    }

    public ServiceResponse<Classroom> join(Account account, String inviteCode) {
        Optional<Classroom> cOptional = classroomRepo.findByInviteCode(inviteCode);
        if (!cOptional.isPresent()) {
            return ServiceResponse.error("Wrong invite code");
        }
        Classroom classroom = cOptional.get();
        Optional<ClassMember> cmOptional = classMemberRepo.findByAccountAndClassroom(account, classroom);
        if (cmOptional.isPresent()) {
            return ServiceResponse.success(cmOptional.get().getClassroom());
        }
        return ServiceResponse.success(
            classMemberRepo.save(
                    ClassMember.builder()
                        .account(account)
                        .classroom(classroom)
                        .role(ClassMember.STUDENT)
                        .build()
                )
                .getClassroom()
        );
    }
}
