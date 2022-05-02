package ch.shipster.data.domain;

import javax.persistence.*;

//Timo

@Entity
@Table(name="provider")
public class Provider {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Attributes

    /// Constructor
    public Provider(){}

    /// Methods

    /// Special Getters & Setters

    /// Getters & Setters

}
