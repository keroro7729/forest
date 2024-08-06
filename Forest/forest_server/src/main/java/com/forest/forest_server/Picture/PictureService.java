package com.forest.forest_server.Picture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureService {

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(PictureRepository a){
        this.pictureRepository = a;
    }

    //
    public void addPicture(String filename, Long tag){
        Picture picture = new Picture();
        picture.setSource(filename);
        picture.setTag(tag);
        pictureRepository.save(picture);
    }

    public Picture getById(Long id){
        return pictureRepository.findById(id).orElse(null);
    }
}
