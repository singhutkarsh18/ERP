package com.ERP.ERPAPI.Controller;
import com.ERP.ERPAPI.Model.ImageModel;
import com.ERP.ERPAPI.Repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageRepo imageRepo;
    @PostMapping(value="/image/upload", consumes = { "multipart/form-data" })
    public ResponseEntity<?> uplaodImage(@RequestParam("file") MultipartFile file) throws IOException, HttpMediaTypeNotAcceptableException {

        try {
            if (!imageRepo.existsByName(file.getOriginalFilename())) {
                System.out.println("Original Image Byte Size - " + file.getBytes().length);
                ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
                imageRepo.save(img);
                return ResponseEntity.status(HttpStatus.OK).body("Image Saved");
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body("Image name not unique");
            }
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    @GetMapping(path = { "/image/get" })
    public ImageModel getImage(@RequestBody Map<String,String> imageName) throws IOException {
        final Optional<ImageModel> retrievedImage = imageRepo.findByName(imageName.get("imageName"));
        ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {

            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();

    }

}