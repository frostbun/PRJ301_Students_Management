package com.studentmanager.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileUploadDTO extends DTO {
    private MultipartFile file;

    public String validate() {
        return null;
    }

    public String upload(Path path) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            path = path.resolve(file.getOriginalFilename());
            File dest = path.toAbsolutePath().toFile();
            dest.mkdirs();
            file.transferTo(dest);
            return path.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
