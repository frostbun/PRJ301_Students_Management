package com.studentmanager.dto;

import java.time.LocalDateTime;

import com.studentmanager.model.Homework;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateHomeworkDTO extends FileUploadDTO {
    private String title;
    private String description;
    private Double maxMark;
    private String deadline;
    private LocalDateTime dlLocal;

    public LocalDateTime getRawDeadline() {
        return dlLocal;
    }

    public String validate() {
        if (maxMark != null && maxMark < 0) {
            return "Max mark must be greater than 0";
        }
        if (deadline != null && deadline.length() > 0) {
            dlLocal = LocalDateTime.parse(deadline);
            if (dlLocal.isBefore(LocalDateTime.now())) {
                return "Deadline must be in the future";
            }
        }
        if (title.length() == 0 || title.length() > 50) {
            return "Title must be between 1 and 50 characters";
        }
        if (description.length() == 0) {
            return "Description must be at least 1 character";
        }
        return super.validate();
    }

    public Homework mapToHomework(Homework homework) {
        homework.setTitle(title);
        homework.setDescription(description);
        homework.setMaxMark(maxMark);
        homework.setDeadline(dlLocal);
        return homework;
    }
}
