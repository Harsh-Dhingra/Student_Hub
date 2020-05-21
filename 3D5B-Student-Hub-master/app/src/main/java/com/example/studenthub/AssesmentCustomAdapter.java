package com.example.studenthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AssesmentCustomAdapter extends BaseAdapter {
    private String[] assesments;
    private String[] grades;
    private ArrayList<AssesmentRow> items;
    private Context context;

    public AssesmentCustomAdapter(Context context, ArrayList<AssesmentRow> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.assessment_list_element, parent, false);
        }

        AssesmentRow currentItem = (AssesmentRow) getItem(position);

        // get the TextView for item name and item description
        TextView textViewAssesmentName = (TextView)
                convertView.findViewById(R.id.AssesmentName);
        TextView textViewGrade = (TextView)
                convertView.findViewById(R.id.GradeData);

        //sets the text for item name and item description from the current item object
        textViewAssesmentName.setText(currentItem.AssesmentName);
        textViewGrade.setText(currentItem.Grade);

        // returns the view for the current row
        return convertView;
    }
}
