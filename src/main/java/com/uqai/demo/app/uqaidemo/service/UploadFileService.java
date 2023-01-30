package com.uqai.demo.app.uqaidemo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface UploadFileService {
    Resource loading(String nameImage, String type) throws MalformedURLException;
    String copy(MultipartFile file, String type) throws IOException;
    boolean delete(String nameImage, String type);
    Path getPath(String nameImage, String type);
    String uploadImageCloud(MultipartFile file, String type);
    String deleteImageOldCloud(String fileName, String type);
}