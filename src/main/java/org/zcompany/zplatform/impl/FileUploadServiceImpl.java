package org.zcompany.zplatform.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zcompany.zplatform.Service.FileUpload;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUpload {
// Injects an instance of the Cloudinary client
    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        // Uploads the file to Cloudinary and receives the upload result as a map
        try {
            Map<String, String> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            // Extracts the URL
            return uploadResult.get("url");
        }catch (IOException e) {
            throw new FileUploadException("Failed to upload file", e);
        }
    }

}

