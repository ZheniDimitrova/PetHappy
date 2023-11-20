package com.example.pethappy.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {
    @Column(name = "picture_type")
    private String pictureType;
    @Column(name = "picture_name")
    private String pictureName;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] bytes;
    @OneToOne
    private Product product;

    public Picture(String pictureType, String pictureName, byte[] bytes, Product product) {
        this.pictureType = pictureType;
        this.pictureName = pictureName;
        this.bytes = bytes;
        this.product = product;
    }

    public Picture() {
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}


