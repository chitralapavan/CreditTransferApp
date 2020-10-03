package com.example.android.creditmanagementapp.database.userTable;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> userList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        userList = repository.getUserList();
    }

    public void insert(User user){
        repository.insert(user);
    }

    public void delete(User user){
        repository.delete(user);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public LiveData<List<User>> getAvailableUser(int id){
        LiveData<List<User>> availableUsers = repository.getAvailableUsers(id);
        return availableUsers;
    }

    public void updateUserCredit(int id, int credit){
        repository.updateUserCredit(id, credit);
    }
}
