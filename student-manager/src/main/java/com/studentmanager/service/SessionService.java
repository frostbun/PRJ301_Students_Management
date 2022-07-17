package com.studentmanager.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentmanager.model.Account;
import com.studentmanager.model.ClassMember;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;
import com.studentmanager.repository.AccountRepository;
import com.studentmanager.repository.ClassMemberRepository;
import com.studentmanager.repository.HomeworkRepository;
import com.studentmanager.repository.SubmissionRepository;

@Service
public class SessionService {
    @Autowired
    private HttpSession session;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private ClassMemberRepository classMemberRepo;
    @Autowired
    private HomeworkRepository homeworkRepo;
    @Autowired
    private SubmissionRepository submissionRepo;

    public boolean checkCurrentAccount() {
        // setCurrentAccount(Account.builder().username("thanhienee").build());
        // find in session
        Account account = getCurrentAccount();
        if (account == null) {
            return false;
        }
        // check in database
        account = accountRepo.findByUsername(account.getUsername()).orElse(null);
        setCurrentAccount(account);
        return account != null;
    }

    public Account getCurrentAccount() {
        return (Account)session.getAttribute("account");
    }

    public void setCurrentAccount(Account account) {
        session.setAttribute("account", account);
    }

    public boolean checkCurrentClassMember(Long cid) {
        ClassMember classMember = classMemberRepo.findByAccountAndClassroomId(getCurrentAccount(), cid).orElse(null);
        setCurrentClassMember(classMember);
        return classMember != null;
    }

    public ClassMember getCurrentClassMember() {
        return (ClassMember)session.getAttribute("classMember");
    }

    public Classroom getCurrentClassroom() {
        return getCurrentClassMember().getClassroom();
    }

    public boolean isTeacher() {
        return getCurrentClassMember() != null && getCurrentClassMember().isTeacher();
    }

    public boolean isStudent() {
        return getCurrentClassMember() != null && getCurrentClassMember().isStudent();
    }

    public void setCurrentClassMember(ClassMember classMember) {
        session.setAttribute("classMember", classMember);
        session.setAttribute("classroom", classMember.getClassroom());
        if (classMember.getRole().equals(ClassMember.TEACHER)) {
            session.setAttribute("isTeacher", true);
        }
        if (classMember.getRole().equals(ClassMember.STUDENT)) {
            session.setAttribute("isStudent", true);
        }
    }

    public boolean checkCurrentHomework(Long hid) {
        Homework homework = homeworkRepo.findByIdAndClassroom(hid, getCurrentClassroom()).orElse(null);
        setCurrentHomework(homework);
        return homework != null;
    }

    public Homework getCurrentHomework() {
        return (Homework)session.getAttribute("homework");
    }

    public void setCurrentHomework(Homework homework) {
        session.setAttribute("homework", homework);
    }

    public boolean checkCurrentSubmission(Long sid) {
        Submission submission = submissionRepo.findByIdAndHomework(sid, getCurrentHomework()).orElse(null);
        setCurrentSubmission(submission);
        return submission != null;
    }

    public Submission getCurrentSubmission() {
        return (Submission)session.getAttribute("submission");
    }

    public void setCurrentSubmission(Submission submission) {
        session.setAttribute("submission", submission);
    }
}
