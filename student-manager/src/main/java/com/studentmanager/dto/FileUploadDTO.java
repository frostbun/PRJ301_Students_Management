package com.studentmanager.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public String upload(String path) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            Path filePath = Paths.get(path, file.getOriginalFilename());
            File dest = filePath.toAbsolutePath().toFile();
            dest.mkdirs();
            file.transferTo(dest);
            return filePath.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
