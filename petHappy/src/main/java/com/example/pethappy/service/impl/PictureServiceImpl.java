package com.example.pethappy.service.impl;

import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.model.entity.Product;
import com.example.pethappy.repository.PictureRepository;
import com.example.pethappy.repository.ProductRepository;
import com.example.pethappy.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ProductRepository productRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, ProductRepository productRepository) {
        this.pictureRepository = pictureRepository;
        this.productRepository = productRepository;
    }

}
