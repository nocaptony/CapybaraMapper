package learn.capybara_mapper.models;

import java.time.LocalDate;

public class User {
    private int userId;
    private LocalDate dateOfBirth;
    private String name;
    private String email;
    private String phoneNumber;

    // empty constructor
    public User() {}

    public User(int userId, LocalDate dateOfBirth, String name, String email, String phoneNumber) {
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
