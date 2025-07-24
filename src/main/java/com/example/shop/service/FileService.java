package com.example.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    /**
     * Uploads a file to the specified path using a UUID as the saved file name.
     * @return The name of the saved file with UUID and original extension.
     * @throws Exception If an error occurs during file writing.
     */
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    /**
     * Deletes a file from the specified file path if it exists.
     * @param filePath The full path of the file to be deleted.
     * @throws Exception If an error occurs during deletion.
     */
    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("File has been deleted.");
        } else {
            log.info("File does not exist");
        }
    }

}
