package shipster.data.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_accont")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Float price;
    private String inageURL;
    private Integer maxStacking;
    private Float area;
}
