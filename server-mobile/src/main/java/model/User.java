package model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Id
    @Column(name = "username",columnDefinition="VARCHAR(250)")
    private String username;

    @Column(name="password")
    private String password;


    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(){}

    @Column(name="type")
    private String type;
}
