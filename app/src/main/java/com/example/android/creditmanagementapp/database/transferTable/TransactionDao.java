package com.example.android.creditmanagementapp.database.transferTable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(TransactionInfo transactionInfo);

    @Delete
    void delete(TransactionInfo transactionInfo);

    @Query("DELETE FROM transaction_info")
    void deleteAll();

    @Query("SELECT * FROM transaction_info")
    LiveData<List<TransactionInfo>> getAllTransactionList();

}
