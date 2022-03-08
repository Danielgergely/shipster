package ch.shipster.security.model;

import javax.persistence.Entity;
import javax.persistence.Id;

// Daniel
// based on the digipr-arcm project from the Internet technology class: https://github.com/Danielgergely/digipr-acrm

@Entity
public class Token {
    @Id
    private String token;

    public Token() {}

    public Token(String token) {
        this.token = token;
    }
}
