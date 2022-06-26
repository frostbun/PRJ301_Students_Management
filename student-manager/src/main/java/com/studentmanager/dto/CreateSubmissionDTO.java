<<<<<<< HEAD

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
=======
package com.studentmanager.dto;

import org.springframework.ui.Model;
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

    public Model addToView(Model view) {
        return view;
    }
>>>>>>> cd1d3930141ce8e85d38e5d587f76f49eb204293
}
