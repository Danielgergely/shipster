package ch.shipster.data.domain;

// Daniel

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String userName;

    public User(Integer userId,
                String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User() {}

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
