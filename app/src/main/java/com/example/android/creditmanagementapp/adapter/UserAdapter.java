package com.example.android.creditmanagementapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.creditmanagementapp.R;
import com.example.android.creditmanagementapp.database.userTable.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList = new ArrayList<>();
    private OnItemClickListener mListener;
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        String name = "Name : " + user.getName();
        String email = "Email : " + user.getEmail();
        String address = "Address : " + user.getAddress();
        holder.textViewName.setText(name);
        holder.textViewEmail.setText(email);
        holder.textViewAddress.setText(address);
        holder.textViewCredit.setText(String.valueOf(user.getCurrentCredit()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public User getUser(int index){
        return userList.get(index);
    }


    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewName;
        private TextView textViewEmail;
        private TextView textViewAddress;
        private TextView textViewCredit;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_name);
            textViewAddress = itemView.findViewById(R.id.tv_address);
            textViewEmail = itemView.findViewById(R.id.tv_email);
            textViewCredit = itemView.findViewById(R.id.tv_current_credit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(userList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(User user);
    }

    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }

}
