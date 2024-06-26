package com.openclassroom.ChaTop.service;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
  private final Path storageLocation;

  @Value("${oc.app.pictures.url}")
  private String picturesUrl;

  public FileStorageService() {
    this.storageLocation = Paths.get("src/uploads/images/");
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
      return picturesUrl+fileName; // Return the path to the stored file
    } catch (IOException e) {
      throw new RuntimeException("Could not store file " + fileName, e);
    }
  }
}
