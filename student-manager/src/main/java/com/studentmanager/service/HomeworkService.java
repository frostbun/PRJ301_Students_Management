package com.studentmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        MultipartFile file = dto.getFile();
        if (!file.isEmpty()) {
            String filePath = String.format("upload/homework/%d/%s", homework.getId(), file.getOriginalFilename());
            try {
                File dest = new File(new File(filePath).getAbsolutePath());
                dest.mkdirs();
                file.transferTo(dest);
            }
            catch (IOException | IllegalStateException e) {
                homeworkRepo.delete(homework);
                e.printStackTrace();
                return ServiceResponse.error("Failed to upload file");
            }
            homework.setFilePath(filePath);
        }
        return ServiceResponse.success(homeworkRepo.save(homework));
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
