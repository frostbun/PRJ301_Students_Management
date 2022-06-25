
package com.studentmanager.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateSubmissionDTO {
    private int homeworkID;
    private String username;
    private String fileLink;
    private int mark;
    private LocalDateTime createdAt;
    private boolean deleted;
    
    public String validate(){
        return null;
    }
}
