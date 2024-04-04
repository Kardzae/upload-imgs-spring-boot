package com.upload.files.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFilesService {

    public String handleFileUpload(MultipartFile file) throws Exception{
        try{
            String fileName = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            long fileSize = file.getSize();
            long maxFileSize = 5 * 1024 * 1024;

            if(fileSize > maxFileSize){
                return "File size must be less than or equal to 5 MB";
            }

            if(!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".png") && !fileOriginalName.endsWith(".jpeg")){
                return "File must be only .jpg, .jpeg or .png extension";
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = fileName + " " + fileExtension;

            File folder = new File("src/main/resources/pictures");

            if(!folder.exists()){
                folder.mkdirs();
            }

            Path path = Paths.get("src/main/resources/pictures/" + newFileName);
            Files.write(path, bytes);

            return "File uploaded successfully";
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
