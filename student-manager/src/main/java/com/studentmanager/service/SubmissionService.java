package com.studentmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanager.dto.CreateSubmissionDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;
import com.studentmanager.repository.SubmissionRepository;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepo;

    public ServiceResponse<Submission> create(Account account, Homework homework, CreateSubmissionDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        Submission submission = submissionRepo.save(
            Submission.builder()
                .author(account)
                .homework(homework)
                .build()
        );
        MultipartFile file = dto.getFile();
        String filePath = String.format("upload/submission/%ld/%s", submission.getId(), file.getOriginalFilename());
        try {
            file.transferTo(new File(filePath));
        }
        catch (IOException | IllegalStateException e) {
            submissionRepo.delete(submission);
            return ServiceResponse.error("Failed to upload file");
        }
        submission.setFilePath(filePath);
        return ServiceResponse.success(submissionRepo.save(submission));
    }

    public Submission getSubmission(Account author, Homework homework) {
        return submissionRepo.findByAuthorAndHomework(author, homework).orElse(null);
    }

    public Submission getSubmission(Classroom classroom, Long sid) {
        return submissionRepo.findByIdAndHomeworkClassroom(sid, classroom).orElse(null);
    }

    public List<Submission> getSubmissions(Homework homework, int page, int size) {
        return submissionRepo.findByHomework(
            homework,
            PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
            )
        );
    }

    public Long countSubmissions(Homework homework) {
        return submissionRepo.countByHomework(homework);
    }
}
