package com.example.demoapplication.mvp.model;

public interface IUser {
    String getEmailOrMobNo();
    String getPassword();
    int isValidData();
}
