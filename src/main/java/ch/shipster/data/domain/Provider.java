package ch.shipster.data.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//Timo

@Entity
@Table(name="provider")
public class Provider {

    /// ID
    @Id
    @GeneratedValue(generator = "providerId-generator")
    @GenericGenerator(name = "providerId-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "PI"),
            strategy = "ch.shipster.util.ShipsterIdGenerator")
    private String id;

    /// Attributes
    private String name;

    /// Constructor

    public Provider(String name) {
        this.name = name;
    }

    public Provider(){}

    /// Methods

    /// Special Getters & Setters

    /// Getters & Setters

    public Long getId() {
        return Long.parseLong(id.substring(id.indexOf("_")+1));
    }

    public String getFullId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
