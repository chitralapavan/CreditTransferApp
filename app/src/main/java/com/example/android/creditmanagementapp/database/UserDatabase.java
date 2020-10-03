package com.example.android.creditmanagementapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.creditmanagementapp.database.transferTable.TransactionDao;
import com.example.android.creditmanagementapp.database.transferTable.TransactionInfo;
import com.example.android.creditmanagementapp.database.userTable.User;
import com.example.android.creditmanagementapp.database.userTable.UserDao;

@Database(entities = {User.class, TransactionInfo.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;

    public abstract UserDao userDao();
    public abstract TransactionDao transactionDao();

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database").
                    fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateUserDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateUserDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private TransactionDao transactionDao;
        private PopulateUserDBAsyncTask(UserDatabase db){
            this.userDao = db.userDao();
            this.transactionDao = db.transactionDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            userDao.insert(new User("Abhinav Kumar","abhinav@gmail.com","Bihar,India",1000));
            userDao.insert(new User("Ashwini Verma","ashwini@gmail.com","Maharastra,India",2000));
            userDao.insert(new User("Ranjan Raj","ranjan@gmail.com","Delhi,India",3000));
            userDao.insert(new User("Amisha patel","Amisha@gmail.com","Rajasthan,India",1000));

            return null;
        }
    }
}
