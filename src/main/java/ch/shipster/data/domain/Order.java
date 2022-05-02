package ch.shipster.data.domain;


import javax.persistence.*;

@Entity
@Table(name="order")
public class Order {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Constructor
    public Order(){}
}
