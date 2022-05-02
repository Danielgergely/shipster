package ch.shipster.data.domain;


import javax.persistence.*;

@Entity
@Table(name="article")
public class Article {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Constructor
    public Article(){}
}
