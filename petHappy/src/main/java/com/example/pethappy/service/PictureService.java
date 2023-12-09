package com.example.pethappy.service;

import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.validation.AddProductBindingModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {

    Picture uploadPicture(MultipartFile multipartFile) throws IOException;

    Picture getById(Long id);

}
