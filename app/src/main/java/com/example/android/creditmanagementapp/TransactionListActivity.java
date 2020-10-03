package com.example.android.creditmanagementapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.creditmanagementapp.adapter.TransactionAdapter;
import com.example.android.creditmanagementapp.database.transferTable.TransactionInfo;
import com.example.android.creditmanagementapp.database.transferTable.TransactionViewModel;

import java.util.List;

public class TransactionListActivity extends AppCompatActivity {
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        setTitle("Transaction List");

        RecyclerView recyclerView = findViewById(R.id.transaction_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        transactionViewModel.getAllTransactionList().observe(this, new Observer<List<TransactionInfo>>() {
            @Override
            public void onChanged(List<TransactionInfo> transactionInfos) {
                // update Recycler View
                adapter.setTransactionInfoList(transactionInfos);
            }
        });
    }
}
