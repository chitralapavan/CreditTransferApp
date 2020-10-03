package com.example.android.creditmanagementapp.database.transferTable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.android.creditmanagementapp.database.userTable.User;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction_info", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "senderId",
        onDelete = CASCADE))

public class TransactionInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int senderId;

    private int receiverId;

    private long timeInMilliSec;

    private int amount;

    public TransactionInfo(int senderId, int receiverId, int amount, long timeInMilliSec) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.timeInMilliSec = timeInMilliSec;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public int getAmount() {
        return amount;
    }

    public long getTimeInMilliSec(){
        return this.timeInMilliSec;
    }
}
