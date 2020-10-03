package com.example.android.creditmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.creditmanagementapp.database.transferTable.TransactionInfo;
import com.example.android.creditmanagementapp.database.transferTable.TransactionViewModel;
import com.example.android.creditmanagementapp.database.userTable.User;
import com.example.android.creditmanagementapp.database.userTable.UserViewModel;

import java.util.Date;
import java.util.List;

public class MakeTransactionActivity extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewAddress;
    private TextView textViewCredit;
    private TextView textViewID;
    private Spinner selectUserSpinner;
    private UserViewModel userViewModel;
    private TransactionViewModel transactionViewModel;
    private Button transferButton;
    private EditText creditEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transaction);
        setTitle("Make Transaction");
        textViewName = findViewById(R.id.tv_mt_name);
        textViewAddress = findViewById(R.id.tv_mt_address);
        textViewEmail = findViewById(R.id.tv_mt_email);
        textViewCredit = findViewById(R.id.tv_mt_credit);
        textViewID = findViewById(R.id.tv_mt_id);
        selectUserSpinner = findViewById(R.id.select_user_spinner);
        transferButton = findViewById(R.id.button_transfer);
        creditEditText = findViewById(R.id.et_amount);

        Intent intent = getIntent();
        String name = intent.getStringExtra(AddUserActivity.EXTRA_NAME);
        String email = intent.getStringExtra(AddUserActivity.EXTRA_EMAIL);
        String address = intent.getStringExtra(AddUserActivity.EXTRA_ADDRESS);
        final int credit = intent.getIntExtra(AddUserActivity.EXTRA_CREDIT, -1);
        final int id = intent.getIntExtra(AddUserActivity.EXTRA_ID, -1);

        final ArrayAdapter<User> userListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAvailableUser(id).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // update Recycler View
                for(User user : users){
                    userListAdapter.add(user);
                }
            }
        });


        userListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        User defaultUser = new User("< none >","","",0);
        defaultUser.setId(-1);
        userListAdapter.add(defaultUser);

        selectUserSpinner.setAdapter(userListAdapter);
        selectUserSpinner.setSelection(0);

        setDisplayUI(name, email, address, credit,id);

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User selectedUser = (User)selectUserSpinner.getSelectedItem();
                if(selectedUser.getId() == -1){
                    Toast.makeText(MakeTransactionActivity.this, "Select user", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(creditEditText.getText().toString().trim().isEmpty()){
                    Toast.makeText(MakeTransactionActivity.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                int creditTOTransfer = Integer.parseInt( creditEditText.getText().toString().trim());
                if(creditTOTransfer > credit){
                    Toast.makeText(MakeTransactionActivity.this, "Insufficient Credit", Toast.LENGTH_SHORT).show();
                    return;
                }

                int receiverId = selectedUser.getId();
                makeTransaction(id, credit, creditTOTransfer, receiverId, selectedUser.getCurrentCredit());
            }
        });
    }

    private void makeTransaction(int senderId, int senderCredit, int transferCredit, int receiverId, int receiverCredit){
        userViewModel.updateUserCredit(receiverId, receiverCredit + transferCredit);
        userViewModel.updateUserCredit(senderId, senderCredit - transferCredit);

        transactionViewModel.insert(new TransactionInfo(senderId, receiverId, transferCredit,new Date().getTime()));
        textViewCredit.setText(String.valueOf(senderCredit - transferCredit));
        creditEditText.setText("");
        Toast.makeText(MakeTransactionActivity.this, "Transfer Complete", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setDisplayUI(String name, String email, String address, int credit, int id) {
        name = "Name : " + name;
        email = "Email : " + email;
        address = "Address : " + address;
        String userId = "User ID : " + id;
        String creditText = "Credit : " + credit;
        textViewName.setText(name);
        textViewAddress.setText(address);
        textViewEmail.setText(email);
        textViewCredit.setText(creditText);
        textViewID.setText(userId);
    }
}
