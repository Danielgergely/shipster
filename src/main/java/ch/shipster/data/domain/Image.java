package ch.shipster.data.domain;

import javax.persistence.*;

//Timo

@Entity
@Table(name="image")
public class Image {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Attributes
    private long articleId;
    private int sequence;
    private String url;

    /// Constructors
    public Image(){}

    /// Methods

    /// Special Getters & Setters

    /// Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
