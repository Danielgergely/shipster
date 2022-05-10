package ch.shipster.data.domain;

// Daniel

import javax.persistence.*;

import ch.shipster.security.ShipsterUserRole;

//Timo

@Entity
@Table(name="shipster_user")
public class User {

    /// ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /// Attributes
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long addressId;
    private String gender;
    private String roles;

    /// Constructor
    public User(String userName,
                String firstName,
                String lastName,
                String email,
                String password,
                Long addressId,
                String gender) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.addressId = addressId;
        this.gender = gender;
        this.roles = "USER";
    }

    public User() {}

    /// Getters & Setters
    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoles() {
        return roles;
    }

    // to overwrite current role and remove ADMIN
    public void setRole(String role) {
        this.roles = role;
    }

    public void setRoles(ShipsterUserRole role) {
        String r = this.roles;
        if (!r.contains(role.name())) {
            this.roles = r + "," + role.name();
        }
    }
}
