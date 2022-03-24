package ch.shipster.data.domain;

// Daniel

public class User {

    private final Integer userId;
    private final String userName;

    public User(Integer userId,
                String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
