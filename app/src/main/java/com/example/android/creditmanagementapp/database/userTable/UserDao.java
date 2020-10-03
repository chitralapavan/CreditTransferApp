package com.example.android.creditmanagementapp.database.userTable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM USER_DETAILS")
    void deleteAll();

    @Query("SELECT * FROM USER_DETAILS")
    LiveData<List<User>> getUserList();

    @Query("SELECT * FROM USER_DETAILS WHERE id != :userID")
    LiveData<List<User>> getAvailableUsers(int userID);

    @Query("UPDATE USER_DETAILS SET currentCredit = :credit WHERE id = :id")
    void updateUserCredit(int id, int credit);

}
