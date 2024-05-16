package com.forest.forest_server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageLoader {

    private final static String BASE_PATH = "forest_server/src/main/resources";

    public static String load(String path){
        try {
            File file = new File(BASE_PATH+path);
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

    public static int countFiles(String path){
        File file = new File(BASE_PATH+path);
        File[] files = file.listFiles();
        return files.length;
    }

    public static int countImages(){
        return countFiles("/img");
    }
}
