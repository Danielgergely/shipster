package ch.shipster.data.domain;

import javax.persistence.*;

//Timo

@Entity
@Table(name = "article")
public class Article {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Attributes
    private String name;
    private String description;
    private String imageUrl;
    Float price;
    Float palletSpace;
    Float maxStack;

    /// Constructor

    public Article(String name, String description, String imageUrl, Float price, Float palletSpace, Float maxStack) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.palletSpace = palletSpace;
        this.maxStack = maxStack;
    }

    public Article() {
    }

    /// Methods

    /// Special Getters & Setters

    public float getPalletProductRatio() {
        return (palletSpace / maxStack);
    }

    /// Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPalletSpace() {
        return palletSpace;
    }

    public void setPalletSpace(float palletSpace) {
        this.palletSpace = palletSpace;
    }

    public float getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(float maxStack) {
        this.maxStack = maxStack;
    }

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String url) {
        this.imageUrl = url;
    }

}
