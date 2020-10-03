package com.example.android.creditmanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.creditmanagementapp.adapter.UserAdapter;
import com.example.android.creditmanagementapp.database.userTable.User;
import com.example.android.creditmanagementapp.database.userTable.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    public static final int ADD_USER_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Credit Management App");

        FloatingActionButton addUserButton = findViewById(R.id.fab_add_user);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST_CODE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // update Recycler View
                adapter.setUserList(users);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                userViewModel.delete(adapter.getUser(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                Intent intent = new Intent(MainActivity.this, MakeTransactionActivity.class);
                intent.putExtra(AddUserActivity.EXTRA_NAME, user.getName());
                intent.putExtra(AddUserActivity.EXTRA_EMAIL, user.getEmail());
                intent.putExtra(AddUserActivity.EXTRA_ADDRESS, user.getAddress());
                intent.putExtra(AddUserActivity.EXTRA_CREDIT, user.getCurrentCredit());
                intent.putExtra(AddUserActivity.EXTRA_ID, user.getId());

                startActivity(intent);
            }
        });
    }

    /**
     *
     * @param requestCode
     * @param resultCode returned by @Add
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_USER_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra(AddUserActivity.EXTRA_NAME);
                String email = data.getStringExtra(AddUserActivity.EXTRA_EMAIL);
                String address = data.getStringExtra(AddUserActivity.EXTRA_ADDRESS);
                int credit = data.getIntExtra(AddUserActivity.EXTRA_CREDIT, -1);

                User newUser = new User(name,email,address,credit);
                userViewModel.insert(newUser);

                Toast.makeText(this, "Added Success !!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete_all:
                userViewModel.deleteAll();
                Toast.makeText(this, "All user removed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_show_all_transaction:
                showAllTransaction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAllTransaction() {
        Intent intent = new Intent(MainActivity.this,TransactionListActivity.class);
        startActivity(intent);
    }

}
