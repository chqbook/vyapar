package com.chqbook.vypaar.ui;

import android.content.Context;
import android.graphics.Typeface;
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

    private ArrayList<Result> paymentValues;
    private Context mContext;

    public PaymentReviewAdapter(ArrayList<Result> contactsList, Context context) {
        this.paymentValues = contactsList;
        this.mContext = context;
    }

    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.payment_review_card, parent, false);
        return new PaymentHolder(view);
    }

    @Override
    public int getItemCount() {
        return paymentValues == null ? 0 : paymentValues.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, final int position) {
        final Result payment = paymentValues.get(position);
        boolean visible = true;
        if (position == (paymentValues.size() - 1))
            visible = false;
        else
            visible = true;
        holder.setTitle(payment.getTitle());
        holder.setValue(payment.getValue());
        holder.setLineVisibility(visible);
        if (position > paymentValues.size() - 3) {
            holder.setFamily(true);
        }


    }

    public class PaymentHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView value;
        private View line;

        private PaymentHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            value = itemView.findViewById(R.id.value);
            line = itemView.findViewById(R.id.line);
        }

        private void setTitle(String name) {
            title.setText(name);
        }

        private void setValue(String number) {
            value.setText(number);
        }

        private void setLineVisibility(boolean visible) {
            if (visible)
                line.setVisibility(View.VISIBLE);
            else
                line.setVisibility(View.GONE);
        }

        private void setFamily(boolean isBold) {
            if (isBold) {
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                value.setTypeface(value.getTypeface(), Typeface.BOLD);
            } else {
                title.setTypeface(title.getTypeface(), Typeface.NORMAL);
                value.setTypeface(value.getTypeface(), Typeface.NORMAL);
            }
        }
    }
}