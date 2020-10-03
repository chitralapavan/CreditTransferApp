package com.example.android.creditmanagementapp.database.userTable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_details")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String email;

    private String address;

    private int currentCredit;

    public User(String name, String email, String address, int currentCredit) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.currentCredit = currentCredit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getCurrentCredit() {
        return currentCredit;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
