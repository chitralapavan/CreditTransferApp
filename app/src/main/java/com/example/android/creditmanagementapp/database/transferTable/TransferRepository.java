package com.example.android.creditmanagementapp.database.transferTable;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.android.creditmanagementapp.database.UserDatabase;

import java.util.List;

public class TransferRepository {
    private TransactionDao transactionDao;
    private LiveData<List<TransactionInfo>> transactionList;
    private List<TransactionInfo> userTransactionList;

    public TransferRepository(Application application) {
        UserDatabase userDatabase = UserDatabase.getInstance(application);
        transactionDao = userDatabase.transactionDao();
        transactionList = transactionDao.getAllTransactionList();
    }

    public void insert(TransactionInfo transactionInfo) {
        new TransferRepository.InsertTransactionInfoAsyncTask(transactionDao).execute(transactionInfo);
    }

    public void delete(TransactionInfo transactionInfo) {
        new TransferRepository.DeleteTransactionInfoAsyncTask(transactionDao).execute(transactionInfo);
    }

    public void deleteAll() {
        new TransferRepository.DeleteAllTransactionInfoAsyncTask(transactionDao).execute();
    }

    public LiveData<List<TransactionInfo>> getAllTransactionList() {
        return transactionList;
    }


    class InsertTransactionInfoAsyncTask extends AsyncTask<TransactionInfo, Void, Void>{

        private TransactionDao transactionDao;

        public InsertTransactionInfoAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(TransactionInfo... transactionInfos) {
            transactionDao.insert(transactionInfos[0]);
            return null;
        }
    }

    class DeleteTransactionInfoAsyncTask extends AsyncTask<TransactionInfo, Void, Void>{

        private TransactionDao transactionDao;

        public DeleteTransactionInfoAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(TransactionInfo... transactionInfos) {
            transactionDao.delete(transactionInfos[0]);
            return null;
        }
    }

    class DeleteAllTransactionInfoAsyncTask extends AsyncTask<Void, Void, Void>{

        private TransactionDao transactionDao;

        public DeleteAllTransactionInfoAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactionDao.deleteAll();
            return null;
        }
    }

}
