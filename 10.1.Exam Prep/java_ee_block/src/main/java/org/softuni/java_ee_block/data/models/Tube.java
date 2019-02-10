package org.softuni.java_ee_block.data.models;

public class Tube {
    private String title;
    private String description;
    private int views;
    private String uploader;

    private String videoLink;

    public Tube() {
    }

    public Tube(String title, String description, int views, String uploader) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.uploader = uploader;
    }

    public Tube(String title, String description, String videoLink) {
        this.title = title;
        this.description = description;
        this.videoLink = videoLink;
    }

    public String getVideoLink() {
        return this.videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getUploader() {
        return this.uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
