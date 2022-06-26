package com.studentmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        String filePath = String.format("upload/homework/%ld/%s", homework.getId(), file.getOriginalFilename());
        try {
            file.transferTo(new File(filePath));
        }
        catch (IOException | IllegalStateException e) {
            homeworkRepo.delete(homework);
            return ServiceResponse.error("Failed to upload file");
        }
        homework.setFilePath(filePath);
        return ServiceResponse.success(homeworkRepo.save(homework));
    }

    public Homework getHomework(Classroom classroom, Long hid) {
        Optional<Homework> hOptional =  homeworkRepo.findByIdAndClassroom(hid, classroom);
        if (!hOptional.isPresent()) {
            return null;
        }
        return hOptional.get();
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
