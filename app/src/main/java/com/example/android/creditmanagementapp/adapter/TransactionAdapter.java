package com.example.android.creditmanagementapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.creditmanagementapp.R;
import com.example.android.creditmanagementapp.database.transferTable.TransactionInfo;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<TransactionInfo> transactionInfoList = new ArrayList<>();
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.transaction_list_item,parent,false);

        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionInfo transactionInfo = transactionInfoList.get(position);

        String senderInfo = "sender id : " + transactionInfo.getSenderId();
        String receiverInfo = "receiver id : " + transactionInfo.getReceiverId();
        String credit = "Credit Transferred : " + transactionInfo.getAmount();

        holder.textViewSender.setText(senderInfo);
        holder.textViewReceiver.setText(receiverInfo);
        holder.textViewCredit.setText(credit);

        holder.textViewDate.setText(formatDate(transactionInfo.getTimeInMilliSec()));
    }

    /**
     * @param timeInMilliSec time of transaction in millisecond
     * @return String representation of the date of transaction
     */
    private String formatDate(long timeInMilliSec){
        Date date = new Date(timeInMilliSec);
        DateFormat df = DateFormat.getDateInstance();
        return df.format(date);
    }

    @Override
    public int getItemCount() {
        return transactionInfoList.size();
    }

    public void setTransactionInfoList(List<TransactionInfo> transactionInfoList) {
        this.transactionInfoList = transactionInfoList;
        notifyDataSetChanged();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewSender;
        private TextView textViewReceiver;
        private TextView textViewCredit;
        private TextView textViewDate;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSender = itemView.findViewById(R.id.tv_sender);
            textViewReceiver = itemView.findViewById(R.id.tv_receiver);
            textViewCredit = itemView.findViewById(R.id.tv_credit_transfer);
            textViewDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
