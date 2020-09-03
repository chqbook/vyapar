package com.chqbook.vypaar.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.chqbook.vypaar.R;
import com.chqbook.vypaar.model.initiate_payment.Result;

import java.util.ArrayList;

public class PaymentReviewAdapter extends RecyclerView.Adapter<PaymentReviewAdapter.PaymentHolder> {

    // List to store all the contact details
    private ArrayList<Result> paymentValues;
    private Context mContext;

    // Counstructor for the Class
    public PaymentReviewAdapter(ArrayList<Result> contactsList) {
        this.paymentValues = contactsList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.payment_review_card, parent, false);
        return new PaymentHolder(view);
    }

    @Override
    public int getItemCount() {
        return paymentValues == null ? 0 : paymentValues.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, final int position) {
        final Result payment = paymentValues.get(position);
        boolean visible=true;
        if(position==(paymentValues.size()-1))
            visible=false;
        else
            visible=true;
        // Set the data to the views here
        holder.setTitle(payment.getTitle());
        holder.setValue(payment.getValue());
        holder.setLineVisibility(visible);

    }

    public class PaymentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView value;
        private View line;

        public PaymentHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            value = itemView.findViewById(R.id.value);
            line = itemView.findViewById(R.id.line);
        }

        public void setTitle(String name) {
            title.setText(name);
        }

        public void setValue(String number) {
            value.setText(number);
        }

        public void setLineVisibility(boolean visible) {
            if(visible)
                line.setVisibility(View.VISIBLE);
            else
                line.setVisibility(View.GONE);
        }
    }
}