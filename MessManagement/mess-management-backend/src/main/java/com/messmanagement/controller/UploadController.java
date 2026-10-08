package com.messmanagement.controller;

import com.messmanagement.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UploadController {
    private final FileStorageService fileStorageService;

    public UploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploads/{folder}")
    public Map<String, String> upload(@PathVariable String folder, @RequestParam MultipartFile file) throws Exception {
        return Map.of("url", fileStorageService.store(file, folder));
    }
}
