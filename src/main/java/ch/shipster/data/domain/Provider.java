package ch.shipster.data.domain;

import javax.persistence.*;

//Timo

@Entity
@Table(name="provider")
public class Provider {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

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
}
