package ch.shipster.data.domain;

//Timo

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="cost")
public class Cost {

    /// ID
    @Id
    @GeneratedValue(generator = "costId-generator")
    @GenericGenerator(name = "costId-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CI"),
            strategy = "ch.shipster.util.ShipsterIdGenerator")
    private String id;

    /// Attributes
    private String providerId;
    private int km;
    private int pallet;
    private float price;

    /// Constructor

    public Cost(Long providerId, int km, int pallet, float price) {
        this.providerId = "PI_" + providerId;
        this.km = km;
        this.pallet = pallet;
        this.price = price;
    }

    public Cost(Provider provider, int km, int pallet, float price) {
        this.providerId = "PI_" + provider.getId();
        this.km = km;
        this.pallet = pallet;
        this.price = price;
    }

    public Cost(){}

    /// Methods



    /// Getters & Setters

    public Long getId() {
        return Long.parseLong(id.substring(id.indexOf("_")+1));
    }

    public String getFullId() {
        return id;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = "PI_" + providerId;
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
