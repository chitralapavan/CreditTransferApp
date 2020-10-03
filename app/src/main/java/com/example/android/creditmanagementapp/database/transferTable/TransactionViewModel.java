package com.example.android.creditmanagementapp.database.transferTable;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransferRepository repository;
    private LiveData<List<TransactionInfo>> transactionList;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransferRepository(application);
        transactionList = repository.getAllTransactionList();
    }

    public void insert(TransactionInfo transactionInfo){
        repository.insert(transactionInfo);
    }

    public void delete(TransactionInfo transactionInfo){
        repository.delete(transactionInfo);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<TransactionInfo>> getAllTransactionList() {
        return transactionList;
    }

}
