package com.studentmanager.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanager.dto.CreateHomeworkDTO;
import com.studentmanager.model.Account;
import com.studentmanager.model.Classroom;
import com.studentmanager.model.Homework;
import com.studentmanager.repository.HomeworkRepository;

@Service
public class HomeworkService {
    @Autowired
    private HomeworkRepository homeworkRepo;

    public Long create(Model view, Account account, Classroom classroom, CreateHomeworkDTO dto) {
        String error = dto.validate();
        if (error != null) {
            view.addAttribute("error", error);
            return null;
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
        String filePath = String.format("uploads/homeworks/%ld/%s", homework.getId(), file.getOriginalFilename());
        try {
            file.transferTo(new File(filePath));
        }
        catch (IOException | IllegalStateException e) {
            homeworkRepo.delete(homework);
            view.addAttribute("error", "Failed to upload file");
            return null;
        }
        homework.setFilePath(filePath);
        return homeworkRepo.save(homework).getId();
    }

    public Homework getHomeworkById(Long id, Classroom classroom) {
        Optional<Homework> hOptional =  homeworkRepo.findByIdAndClassroom(id, classroom);
        if (!hOptional.isPresent()) {
            return null;
        }
        return hOptional.get();
    }
}
