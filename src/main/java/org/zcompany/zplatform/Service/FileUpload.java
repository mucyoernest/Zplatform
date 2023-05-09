package org.zcompany.zplatform.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUpload {
   String uploadImage(MultipartFile file) throws IOException;

    }
