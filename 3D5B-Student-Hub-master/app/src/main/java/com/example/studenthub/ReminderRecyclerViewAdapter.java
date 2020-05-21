package com.example.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReminderRecyclerViewAdapter extends RecyclerView.Adapter<ReminderRecyclerViewAdapter.ViewHolder>{

    private List<ReminderDisplay> mData;
    private LayoutInflater mInflater;
    private ReminderRecyclerViewAdapter.ItemClickListener mClickListener;

    // data is passed into the constructor
    ReminderRecyclerViewAdapter(Context context, List<ReminderDisplay> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ReminderRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reminder_row, parent, false);
        return new ReminderRecyclerViewAdapter.ViewHolder(view, mClickListener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ReminderRecyclerViewAdapter.ViewHolder holder, int position) {
        ReminderDisplay reminder = mData.get(position);
        holder.moduleTitle.setText(reminder.moduleTitle);
        holder.assignMsg.setText(reminder.assignMsg);
        holder.date.setText(reminder.date);
        holder.time.setText(reminder.time);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView moduleTitle, assignMsg, date, time;
        ImageView deleteBtn;

        ViewHolder(View itemView, final ReminderRecyclerViewAdapter.ItemClickListener listener) {
            super(itemView);
            moduleTitle = (TextView) itemView.findViewById(R.id.moduleTitle);
            assignMsg = (TextView) itemView.findViewById(R.id.assignMsg);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            deleteBtn = (ImageView) itemView.findViewById(R.id.deleteIcon);

            itemView.setOnClickListener(this);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ReminderDisplay getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ReminderRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onDeleteClick(int position);
    }

}
