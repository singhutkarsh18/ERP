package com.ERP.ERPAPI.Controller;
import com.ERP.ERPAPI.Model.ImageModel;
import com.ERP.ERPAPI.Model.Username;
import com.ERP.ERPAPI.Repository.ImageRepo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageRepo imageRepo;


    @PostMapping("/image/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile")MultipartFile file,
                                         @RequestParam("imageName") String name) {
        try {
            String imageDirectory ="./src/main/resources/images";
            makeDirectoryIfNotExist(imageDirectory);
            String[] fileFrags = file.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length-1];
            Path fileNamePath = Paths.get(imageDirectory,
                name.concat(".").concat(extension));
            System.out.println(fileNamePath);
            Files.write(fileNamePath, file.getBytes());
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username=userDetails.getUsername();
            if(imageRepo.existsByUsername(username))
            {
                ImageModel imageModel =imageRepo.findImageModelByUsername(username);
                imageModel.setImageName(fileNamePath.toString());
                imageRepo.save(imageModel);
            }
            else {
                ImageModel imageModel = new ImageModel();
                imageModel.setUsername(username);
                imageModel.setImageName(fileNamePath.toString());
                imageRepo.save(imageModel);
            }
            return new ResponseEntity<>("Image uploaded", HttpStatus.CREATED);
        } catch (IOException ex) {
            return new ResponseEntity<>("Image is not uploaded", HttpStatus.BAD_REQUEST);
        }
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    @GetMapping("/image/get")
    public ResponseEntity<?> getImage() {
        byte[] image = new byte[0];
        try {
            UserDetails userDetails=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username=userDetails.getUsername();
            if(!imageRepo.existsByUsername(username))
            return new ResponseEntity<>("Image not found", HttpStatus.OK);
        else {
                image = FileUtils.readFileToByteArray(new File(imageRepo.findImageModelByUsername(username).getImageName()));
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(e);
        }
    }
    @PostMapping("/image/get/username")
    public ResponseEntity<?> getImageByUsername(@RequestBody Username username) {
        byte[] image = new byte[0];
        try {
            if(!imageRepo.existsByUsername(username.getUsername()))
                return new ResponseEntity<>("Image not found", HttpStatus.OK);
            else {
                image = FileUtils.readFileToByteArray(new File(imageRepo.findImageModelByUsername(username.getUsername()).getImageName()));
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(e);
        }
    }
}