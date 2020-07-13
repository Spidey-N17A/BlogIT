package com.example.blogit;


public class Blog {

    private String story;
    private String image;
    private String title;

    public Blog() {
    }

    public Blog(String story, String image, String title) {
        this.story = story;
        this.image = image;
        this.title = title;
    }


    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
