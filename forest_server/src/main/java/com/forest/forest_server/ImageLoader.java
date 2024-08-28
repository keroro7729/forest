package com.forest.forest_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageLoader {

    private final static String BASE_PATH = "C:\\Users\\keror\\forest\\forest_server\\src\\main\\resources\\img\\";

    public static String load(String fileName){
        try {
            File file = new File(BASE_PATH+fileName);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            inputStream.read(bytes);
            inputStream.close();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
