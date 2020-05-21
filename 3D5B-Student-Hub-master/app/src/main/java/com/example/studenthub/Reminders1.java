package com.example.studenthub;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Reminders1 extends AppCompatActivity {

    Spinner spinner;
    TextView tv1;
    TextView tv2;
    Calendar mCurrentDate;
    int day, month, year;
    Button mSet;
    EditText mMessage;
    DatabaseReference reminders_reff;
    ArrayList<String> modules = new ArrayList<>();
    DatabaseReference module_reff;
    ArrayAdapter<String> spinnerArrayAdaper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders1);

        mSet = (Button) findViewById(R.id.setAlarm);
        mMessage = (EditText) findViewById(R.id.Message);

        tv1 = (TextView) findViewById(R.id.currentDate);
        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        spinnerArrayAdaper = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modules);
        spinner= (Spinner) findViewById(R.id.ModuleList);
        spinner.setAdapter(spinnerArrayAdaper);


        tv2 = (TextView) findViewById(R.id.currentTime);
        tv2.setText("12:00");

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog TimePickerDialog = new TimePickerDialog(Reminders1.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       tv2.setText(hourOfDay+":"+minute);
                    }
                },0,0,false);
                TimePickerDialog.show();
            }
        });

        month=month+1;
        tv1.setText(day+"/"+month+"/"+year);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog DatePickerDialog = new DatePickerDialog(Reminders1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        tv1.setText(dayOfMonth + "/" + month + "/" + year);

                    }
                }, year, month, day);
                DatePickerDialog.show();
            }
        });

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        module_reff = FirebaseDatabase.getInstance().getReference().child("User").
                child(user_id).child("C_Modules");
        module_reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    String modTitle = userSnapshot.child("Title").getValue().toString();
                    modules.add(modTitle);
                }
                spinnerArrayAdaper.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reminders_reff = FirebaseDatabase.getInstance().getReference().child("User").
                child(user_id).child("D_Reminders");

        mSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String moduleName = spinner.getSelectedItem().toString();
                String moduleText = mMessage.getText().toString();
                String doomDate = tv1.getText().toString();
                String doomTime = tv2.getText().toString();

                DatabaseReference newRef = reminders_reff.push();
                Reminder newReminder = new Reminder(moduleName, moduleText, doomDate, doomTime);
                newRef.setValue(newReminder);
                Toast.makeText(Reminders1.this, "Remindered created.", Toast.LENGTH_SHORT).show();

                Intent setClicked = new Intent (Reminders1.this, AlarmPage.class);

                startActivity(setClicked);
                finish();
            }
        });
    }
}
