package com.green.kinsomy.interview.model;

/**
 * Created by kinsomy on 2017/10/21.
 */

public class ImageModel extends BaseResult {

    private String imageUrl;
    private String imageFilename;

    public ImageModel(String imageUrl, String imageFilename) {
        this.imageUrl = imageUrl;
        this.imageFilename = imageFilename;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
