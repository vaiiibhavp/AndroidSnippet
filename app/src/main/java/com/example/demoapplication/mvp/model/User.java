package com.example.demoapplication.mvp.model;

public class User implements IUser {
    String email,password;

    String firstName,lastName,phoneNumber,userId;
    public User(String email,String password)
    {
        this.email=email;
        this.password=password;
    }

    public User(String email, String firstName, String lastName, String phoneNumber,String userId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    @Override
    public String getEmailOrMobNo() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isValidData() {
        if(email.isEmpty()) {
            return 0;
        }else if(password.isEmpty()) {
            return 2;
        }else
        {
            return 1;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserId() {
        return userId;
    }
}
