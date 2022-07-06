package com.studentmanager.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateSubmissionDTO extends DTO {
    private MultipartFile file;

    public String validate() {
        return null;
    }
}
