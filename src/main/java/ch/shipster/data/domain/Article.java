package ch.shipster.data.domain;


import javax.persistence.*;

@Entity
@Table(name="article")
public class Article {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Attributes
    private String name;
    private float price;
    private float palletSpace;
    private float maxStack;

    /// Constructor
    public Article(){}

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
}
