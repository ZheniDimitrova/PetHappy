package com.example.pethappy.service;

import com.example.pethappy.model.entity.Picture;
import com.example.pethappy.repository.PictureRepository;
import com.example.pethappy.service.impl.PictureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PictureServiceTests {
    @Mock
    private PictureRepository pictureRepository;

    private PictureServiceImpl toTest;


    @BeforeEach
    public void setUp() {
        toTest = new PictureServiceImpl(pictureRepository);
    }

    @Test
    public void testUploadPicture() throws IOException {

        //Arrange
        MultipartFile file = Mockito.mock(MultipartFile.class);

        //Act
        toTest.uploadPicture(file);

        //Assert
        Mockito.verify(pictureRepository).save(any());
    }


}
