package ch.shipster.data.domain;

//Timo

import javax.persistence.*;

@Entity
@Table(name="cost")
public class Cost {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /// Attributes
    private Long providerId;
    private int km;
    private int pallet;
    private float price;

    /// Constructor

    public Cost(Long providerId, int km, int pallet, float price) {
        this.providerId = providerId;
        this.km = km;
        this.pallet = pallet;
        this.price = price;
    }

    public Cost(Provider provider, int km, int pallet, float price) {
        this.providerId = provider.getId();
        this.km = km;
        this.pallet = pallet;
        this.price = price;
    }

    public Cost(){}

    /// Methods



    /// Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getPallet() {
        return pallet;
    }

    public void setPallet(int pallet) {
        this.pallet = pallet;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
