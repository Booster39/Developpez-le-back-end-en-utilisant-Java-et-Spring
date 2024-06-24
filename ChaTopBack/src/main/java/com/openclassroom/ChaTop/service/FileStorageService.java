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
/*
  @Value("${application.pictures.path}")
  private String picturesPath;

  @Value("${application.pictures.url}")
  private String picturesUrl;

  @Override
  public String savePicture(MultipartFile picture) {
    File pictureFolder = new File(picturesPath);

    // If picture folder does not exist, create it
    if (!pictureFolder.exists() && pictureFolder.mkdirs() || pictureFolder.exists() && pictureFolder.isDirectory()) {
      // That is the new name of the picture, we use UUID to avoid conflicts
      String pictureFileName = UUID.randomUUID().toString();
      File pictureFile = new File(pictureFolder, pictureFileName);

      try {
        // Saving the picture to the file and returning the URL
        picture.transferTo(pictureFile);
        return picturesUrl + "/" + pictureFileName;
      } catch (Exception e) {
        logger.error("Failed to save picture", e);
      }
    }
    throw new ResponseEntityException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save picture");
  }
*/

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
      return  fileName; // Return the path to the stored file
    } catch (IOException e) {
      throw new RuntimeException("Could not store file " + fileName, e);
    }
  }
}
