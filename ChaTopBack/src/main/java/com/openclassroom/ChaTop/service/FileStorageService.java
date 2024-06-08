package com.openclassroom.ChaTop.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
  private final Path storageLocation;

  public FileStorageService() {
    this.storageLocation = Paths.get("ChaTopBack/src/main/resources/static/public/");
    try {
      Files.createDirectories(this.storageLocation);
    } catch (IOException e) {
      throw new RuntimeException("Could not create storage directory", e);
    }
  }

  public String savePicture(MultipartFile file) {
    // Generate a unique filename
    String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    try {
      // Copy the file to the target location
      Path targetLocation = this.storageLocation.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation);
      return "http://localhost:3001/public/" +fileName; // Return the path to the stored file
    } catch (IOException e) {
      throw new RuntimeException("Could not store file " + fileName, e);
    }
  }
}
