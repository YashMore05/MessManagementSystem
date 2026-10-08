package com.messmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {
    private static final Set<String> ALLOWED_FOLDERS = Set.of("mess_photos", "dish_photos", "profile_photos");

    @Value("${app.upload-dir}")
    private String uploadDir;

    public String store(MultipartFile file, String folder) throws Exception {
        if (!ALLOWED_FOLDERS.contains(folder)) {
            throw new IllegalArgumentException("Invalid upload folder");
        }
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
        String extension = "";
        int dot = originalName.lastIndexOf('.');
        if (dot >= 0) {
            extension = originalName.substring(dot);
        }

        Path folderPath = Path.of(uploadDir, folder).toAbsolutePath().normalize();
        Files.createDirectories(folderPath);
        String fileName = UUID.randomUUID() + extension;
        Path target = folderPath.resolve(fileName).normalize();
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + folder + "/" + fileName;
    }
}
