package com.example.android.creditmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddUserActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "com.example.android.credittransferapp.EXTRA_NAME";
    public static final String EXTRA_ADDRESS =
            "com.example.android.credittransferapp.EXTRA_ADDRESS";
    public static final String EXTRA_EMAIL =
            "com.example.android.credittransferapp.EXTRA_EMAIL";
    public static final String EXTRA_CREDIT =
            "com.example.android.credittransferapp.EXTRA_CREDIT";
    public static final String EXTRA_ID =
            "com.example.android.credittransferapp.EXTRA_ID";

    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextEmail;
    private EditText editTextCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = findViewById(R.id.et_name);
        editTextEmail = findViewById(R.id.et_email);
        editTextAddress = findViewById(R.id.et_address);
        editTextCredit = findViewById(R.id.et_credit);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add User");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save_user:
                addUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addUser() {
        String name = editTextName.getText().toString();
        String address = editTextAddress.getText().toString();
        String email = editTextEmail.getText().toString();
        String creditString = editTextCredit.getText().toString();
        int credit;
        if(name.trim().isEmpty() || email.trim().isEmpty() ){
            Toast.makeText(this, "Name and Email are mandatory", Toast.LENGTH_SHORT).show();
            return;
        }

        if(creditString.trim().isEmpty()){
            credit = 0;
        }else{
            credit = Integer.parseInt(creditString);
        }

        if(address.trim().isEmpty()){
            address = "Not specified by user";
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_ADDRESS, address);
        intent.putExtra(EXTRA_CREDIT, credit);
        setResult(RESULT_OK, intent);
        finish();
    }
}
