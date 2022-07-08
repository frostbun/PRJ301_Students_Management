package com.studentmanager.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateSubmissionDTO extends FileUploadDTO {

    public String validate() {
        if (getFile().isEmpty()) {
            return "No file selected";
        };
        return null;
    }
}
