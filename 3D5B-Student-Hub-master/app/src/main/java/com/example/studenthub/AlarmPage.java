package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlarmPage extends AppCompatActivity {
    RecyclerView reminderDisplay;
    ArrayList<ReminderDisplay> reminders = new ArrayList<>();
    ReminderRecyclerViewAdapter adapter;
    DatabaseReference reminders_reff;



    FloatingActionButton mAddAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        mAddAlarm = (FloatingActionButton) findViewById(R.id.remindersPage);
        mAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButton = new Intent (AlarmPage.this, Reminders1.class);
                startActivity(backButton);
                finish();
            }
        });


        reminderDisplay = (RecyclerView) findViewById(R.id.ReminderRecycler);
        reminderDisplay.setHasFixedSize(true);
        reminderDisplay.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReminderRecyclerViewAdapter(this, reminders);
        reminderDisplay.setAdapter(adapter);
        adapter.setClickListener(new ReminderRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                ReminderDisplay deleted = reminders.get(position);
                reminders_reff.child(deleted.reminderKey).removeValue();



            }
        });

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reminders_reff = FirebaseDatabase.getInstance().getReference().child("User").
                child(user_id).child("D_Reminders");

        reminders_reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reminders.clear();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    String rKey = userSnapshot.getKey();
                    String mTitle = userSnapshot.child("moduleTitle").getValue().toString();
                    String aMsg = userSnapshot.child("assignMsg").getValue().toString();
                    String date = userSnapshot.child("date").getValue().toString();
                    String time = userSnapshot.child("time").getValue().toString();
                    reminders.add(new ReminderDisplay(rKey, mTitle, aMsg, date, time));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
