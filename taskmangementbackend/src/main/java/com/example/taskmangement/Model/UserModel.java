package com.example.taskmangement.Model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_table")
public class UserModel {

    // Primary key for user table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Unique username field, cannot be null
    @Column(nullable = false, unique = true)
    private String login;

    // Encrypted password, cannot be null
    @Column(nullable = false)
    private String password;

    // Unique email field, cannot be null
    @Column(nullable = false, unique = true)
    private String email;

    // Default constructor required by JPA
    public UserModel() {}

    // Parameterized constructor used when creating a user manually
    public UserModel(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    // Getter and Setter for user id
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter and Setter for login username
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    // Getter and Setter for encrypted password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Override equals to compare user objects by id, login, and email
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel user = (UserModel) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email);
    }

    // Override hashCode to match equals logic
    @Override
    public int hashCode() {
        return Objects.hash(id, login, email);
    }

    // toString used for logging/debugging, excludes password for security
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
