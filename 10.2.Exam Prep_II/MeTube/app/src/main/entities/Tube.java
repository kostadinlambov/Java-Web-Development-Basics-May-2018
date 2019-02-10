package main.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tubes")
public class Tube {
    private String id;

    private String title;

    private String author;

    private String description;

    private String youtubeId;

    private long views;

    private User uploader;

    public Tube() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false, name = "youtube_id")
    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    @Column(nullable = false)
    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    private String extractThumbnail() {
        return "https://img.youtube.com/vi/"
                + this.getYoutubeId()
                + "/hqdefault.jpg";
    }


    public String extractIFrameUrl() {
        return "http://www.youtube.com/embed/"
                + this.getYoutubeId()
                + "?autoplay=0&amp;showinfo=0&amp;controls=0";
    }

    public String extractTubeThumbnailView() {
        StringBuilder result = new StringBuilder();

        result.append("<div class=\"col-md-3 d-flex flex-column text-center\">")
                .append("<td><a href=\"/tube/details/" + this.getId() + "\">")
                .append("<img width=\"300\" height=\"300\" class=\"img-thumbnail\" src=\""
                        + this.extractThumbnail() + "\"></a>")
                .append("<h4 class=\"text-center\">" + this.getTitle() + "</h4>")
                .append("<h5 class=\"text-center\">" + this.getAuthor() + "</h5>")
                .append("</div>");

        return result.toString();
    }

    public String extractTubeTableView() {
        StringBuilder result = new StringBuilder();

        result.append("<tr>")
                .append("<th scope=\"row\">%d</th>")
                .append("<td>" + this.getTitle() + "</td>")
                .append("<td>" + this.getAuthor() + "</td>")
                .append("<td><a href=\"/tube/details/" + this.getId() + "\">Details</a></td>")
                .append("</div>");

        return result.toString();
    }
}
