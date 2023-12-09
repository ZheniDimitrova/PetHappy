package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.repository.PictureRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;



    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;

    }


    @Override
    public Picture uploadPicture(MultipartFile picture) throws IOException {

        Picture pic = new Picture(picture.getContentType(), picture.getName(), picture.getBytes());
        pictureRepository.save(pic);

        return pic;
    }

    @Override
    public Picture getById(Long id) {
        return pictureRepository.findById(id).get();
    }
}
