package com.mat.firebase;

public class CarModel {
    private String CarName;
    private String ImageUrl;

    public CarModel() {
    }

    public CarModel(String carName, String imageUrl) {
        CarName = carName;
        ImageUrl = imageUrl;
    }

    public String getCarName() {
        return CarName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

}
