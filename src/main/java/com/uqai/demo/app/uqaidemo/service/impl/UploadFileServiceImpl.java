package com.uqai.demo.app.uqaidemo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uqai.demo.app.uqaidemo.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Value("${path.image}")
    private String pathImage;

    private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Override
    public Resource loading(String nameImage, String type) throws MalformedURLException {
        Path pathFile = getPath(nameImage, type);
        log.info(pathFile.toString());
        Resource resource = new UrlResource(pathFile.toUri());
        if (!resource.exists() && !resource.isReadable()) {
            return null;
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file, String type) throws IOException {
        String nameImage = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");

        Path pathFile = getPath(nameImage, type);
        log.info(pathFile.toString());

        if (Files.notExists(pathFile)) {
            System.out.println("Message Not folder exist");
            File fileSystem = new File(getPathFile(type));
            fileSystem.mkdirs();
        }

        Files.copy(file.getInputStream(), pathFile);

        return nameImage;
    }

    private String getPathFile(String type) {
        String separator = System.getProperty("file.separator");
        return pathImage + separator + type;
    }

    @Override
    public boolean delete(String nameImage, String type) {
        if (nameImage != null && nameImage.length() > 0) {
            Path pathImageAnt = getPath(nameImage, type);
            File fileAnt = pathImageAnt.toFile();
            if (fileAnt.exists() && fileAnt.canRead()) {
                fileAnt.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String nameImage, String type) {
        return Paths.get(getPathFile(type)).resolve(nameImage).toAbsolutePath();
    }

    @Override
    public String uploadImageCloud(MultipartFile file, String type) {
        Cloudinary cloudinary = new Cloudinary(pathImage);
        try {
            Map uploadResult =  cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", type));
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteImageOldCloud(String fileName, String type) {
        int index = fileName.indexOf(type);
        String name = fileName.substring(index);
        Cloudinary cloudinary = new Cloudinary(pathImage);
        try {
            Map uploadResult =  cloudinary.uploader().destroy(name.split("\\.")[0], ObjectUtils.asMap(
                    "folder", type));
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}