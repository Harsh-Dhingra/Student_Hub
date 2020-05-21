package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GradeAnalytics extends AppCompatActivity {
    TextView ModuleNameGradeAnalytics, GradeAnalyticsTxt, AverageTxt, averageTxt, NoFailsTxt, noFailsTxt;
    Spinner spinner;
    ListView assesmentListView;

    DatabaseReference reff;
    DatabaseReference yearlyreff;
    ArrayList<String> arrayList_year = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter_year;
    ArrayList<AssesmentRow> assesments = new ArrayList<>();
    AssesmentCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_analytics);

        Bundle bn = getIntent().getExtras();
        final String name = bn.getString("module_name");

        spinner = (Spinner) findViewById(R.id.YearSpinner);
        ModuleNameGradeAnalytics = (TextView) findViewById(R.id.ModuleNameGradeAnalytics);
        ModuleNameGradeAnalytics.setText(String.valueOf(name));

        AverageTxt = (TextView) findViewById(R.id.AverageTxt);
        averageTxt = (TextView) findViewById(R.id.averageTxt);
        NoFailsTxt = (TextView) findViewById(R.id.NoFailsTxt);
        noFailsTxt = (TextView) findViewById(R.id.noFailsTxt);
        setTextDisable(ModuleNameGradeAnalytics);

        setTextDisable(AverageTxt);
        setTextDisable(averageTxt);
        setTextDisable(NoFailsTxt);
        setTextDisable(noFailsTxt);



        arrayAdapter_year = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, arrayList_year);
        spinner.setAdapter(arrayAdapter_year);



        reff = FirebaseDatabase.getInstance().getReference().child("Modules").child("Computer Engineering")
        .child("Year 3").child(name).child("Grade Analytics");



        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    String year = userSnapshot.getKey();
                    arrayList_year.add(year);
                }
                arrayAdapter_year.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        assesmentListView = (ListView) findViewById(R.id.assesmentListView);
        adapter = new AssesmentCustomAdapter(this, assesments);
        assesmentListView.setAdapter(adapter);


        yearlyreff = reff.child("2018-2019");
        yearlyreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    if (userSnapshot.getKey().contains( "Average")){
                        AverageTxt.setText(userSnapshot.getValue().toString());
                    } else if (userSnapshot.getKey().contains("Fails")){
                        NoFailsTxt.setText(userSnapshot.getValue().toString());
                    } else{
                        String name = userSnapshot.child("Name").getValue().toString();
                        String grade = userSnapshot.child("Grade").getValue().toString();
                        assesments.add(new AssesmentRow(name, grade));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assesments.clear();
                DatabaseReference newreff = reff.child(arrayList_year.get(position));
                newreff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                            if (userSnapshot.getKey().contains( "Average")){
                                AverageTxt.setText(userSnapshot.getValue().toString());
                            } else if (userSnapshot.getKey().contains("Fails")){
                                NoFailsTxt.setText(userSnapshot.getValue().toString());
                            } else{
                                String name = userSnapshot.child("Name").getValue().toString();
                                String grade = userSnapshot.child("Grade").getValue().toString();
                                assesments.add(new AssesmentRow(name, grade));
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setTextDisable(TextView text){
        text.setFocusable(false);
        text.setCursorVisible(false);
        text.setKeyListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                Intent startIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(startIntent);
                break;
            case R.id.menuReminders:
                Intent otherIntent = new Intent(getApplicationContext(), AlarmPage.class);
                startActivity(otherIntent);
                break;
            case R.id.menuContactUs:
                Intent anotherIntent = new Intent(getApplicationContext(), ContactPage.class);
                startActivity(anotherIntent);
                break;
        }
        return true;
    }

}
