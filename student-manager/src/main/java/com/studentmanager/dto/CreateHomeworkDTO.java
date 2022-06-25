package com.studentmanager.dto;

import java.time.Instant;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.studentmanager.model.Homework;

import lombok.Data;

@Data
public class CreateHomeworkDTO implements DTO {
    private String description;
    private MultipartFile file;
    private Instant deadline;

    public String validate() {
        if (deadline.compareTo(Instant.now()) < 0) {
            return "??? :D";
        }
        return null;
    }

    public void addToView(Model view) {
        view.addAttribute("description", description);
        view.addAttribute("deadline", deadline);
    }

    public Homework mapToHomework(Homework homework) {
        homework.setDescription(description);
        homework.setDeadline(deadline);
        return homework;
    }
}
