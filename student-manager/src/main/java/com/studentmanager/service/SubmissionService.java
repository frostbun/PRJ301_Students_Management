package com.studentmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studentmanager.dto.CreateSubmissionDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Homework;
import com.studentmanager.model.Submission;
import com.studentmanager.repository.SubmissionRepository;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepo;

    public ServiceResponse<Submission> mark(Submission submission, Double mark                              ) {
        if (mark == null || mark < 0 || (submission.getHomework().getMaxMark() != null && mark > submission.getHomework().getMaxMark())) {
            return ServiceResponse.error("Invalid mark");
        }
        submission.setMark(mark);
        return ServiceResponse.success(submissionRepo.save(submission));
    }

    public ServiceResponse<Submission> create(Account account, Homework homework, CreateSubmissionDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        Optional<Submission> sOptional = submissionRepo.findByAuthorAndHomework(account, homework);
        Submission submission =
            sOptional.isPresent()
            ? sOptional.get()
            : submissionRepo.save(
                Submission.builder()
                    .author(account)
                    .homework(homework)
                    .build()
            );
        String path = dto.upload("upload/submission/" + submission.getId());
        if (path == null) {
            if (!sOptional.isPresent()) {
                submissionRepo.delete(submission);
            }
            return ServiceResponse.error("Failed to upload file");
        }
        submission.setFilePath(path);
        return ServiceResponse.success(submissionRepo.save(submission));
    }

    public Submission getSubmission(Account author, Homework homework) {
        return submissionRepo.findByAuthorAndHomework(author, homework).orElse(null);
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
