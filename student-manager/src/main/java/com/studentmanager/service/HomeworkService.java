package com.studentmanager.service;

import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.dto.ServiceResponse;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.repository.HomeworkRepository;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkRepository homeworkRepo;

    public void delete(Homework homework) {
        homeworkRepo.delete(homework);
    }

    public ServiceResponse<Homework> create(Account account, Classroom classroom, CreateHomeworkDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        Homework homework = homeworkRepo.save(
            dto.mapToHomework(
                Homework.builder()
                    .author(account)
                    .classroom(classroom)
                    .build()
            )
        );
        String path = dto.upload(Paths.get("upload", "homework", homework.getId().toString()));
        if (path == null && !dto.getFile().isEmpty()) {
            homeworkRepo.delete(homework);
            return ServiceResponse.error("Failed to upload file");
        }
        homework.setFilePath(path);
        return ServiceResponse.success(homeworkRepo.save(homework));
    }

    public ServiceResponse<Homework> edit(Homework homework, CreateHomeworkDTO dto) {
        String error = dto.validate();
        if (error != null) {
            return ServiceResponse.error(error);
        }
        String path = dto.upload(Paths.get("homework", homework.getId().toString()));
        if (path == null && !dto.getFile().isEmpty()) {
            return ServiceResponse.error("Failed to upload file");
        }
        homework.setFilePath(path);
        return ServiceResponse.success(homeworkRepo.save(dto.mapToHomework(homework)));
    }

    public Homework getHomework(Classroom classroom, Long hid) {
        return homeworkRepo
            .findByIdAndClassroom(hid, classroom)
            .orElse(null);
    }

    public List<Homework> getHomeworks(Classroom classroom, int page, int size) {
        return homeworkRepo.findByClassroom(
            classroom,
            PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
            )
        );
    }

    public Long countHomeworks(Classroom classroom) {
        return homeworkRepo.countByClassroom(classroom);
    }
}
