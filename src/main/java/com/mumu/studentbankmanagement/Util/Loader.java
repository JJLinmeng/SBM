package com.mumu.studentbankmanagement.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;


public class Loader {

    public static byte[] readPicture(String url) throws IOException {
        ClassPathResource imgFile = new ClassPathResource(url);
        return Files.readAllBytes(imgFile.getFile().toPath());
    }
}
