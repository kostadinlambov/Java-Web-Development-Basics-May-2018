package models.binding;

import entities.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

public class TubeAddBindingModel {
    private String title;
    private String author;
    private String description;
    private String youtubeLink;

    public TubeAddBindingModel() {
    }

    public String getYoutubeId(){
        return this.youtubeLink.substring(this.youtubeLink.lastIndexOf('=') + 1);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
}
