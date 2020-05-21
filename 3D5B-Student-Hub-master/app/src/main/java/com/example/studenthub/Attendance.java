package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Attendance extends AppCompatActivity {

    int numAttended = 0;
    int numMissed = 0;
    float attendedPercentage = 100;
    String percentage_string;
    TextView attendanceTextView;
    TextView moduleName;

    //test

    TextView AttendancePercentageTextView;
    TextView AttendMissView;
    Button ButtonMissed;
    Button ButtonAttended;

    DatabaseReference reff;

    //test1

    //DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mPercentageRef = RootRef.child("Percentage");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        AttendancePercentageTextView =(TextView)findViewById(R.id.attendancePercentageTextView);
        AttendMissView =(TextView)findViewById(R.id.toAttendMissView);
        ButtonMissed =(Button)findViewById(R.id.buttonMissed);
        ButtonAttended =(Button)findViewById(R.id.buttonAttended);



        moduleName = findViewById(R.id.ModuleNameText);
        Bundle bn = getIntent().getExtras();
        String module_name = bn.getString("modulename");
        String module_number = bn.getString("modulenumber");
        //moduleName.setText(String.valueOf(module_number));
        moduleName.setText(String.valueOf(module_name));

        attendanceTextView = findViewById(R.id.attendancePercentageTextView);

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("User").
                child(user_id).child("C_Modules").child(module_number).child("Attendance");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                percentage_string = dataSnapshot.child("Percentage").getValue().toString();
                attendedPercentage = Float.parseFloat(percentage_string);
                numAttended = Integer.parseInt(dataSnapshot.child("Number Attended").getValue().toString());
                numMissed = Integer.parseInt(dataSnapshot.child("Number Missed").getValue().toString());
                AttendancePercentageTextView.setText("Your Attendance is: " + attendedPercentage +"%");

                if (Math.round(attendedPercentage) == 75 ){
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou are even!!");
                }
                else if(Math.round(attendedPercentage)<=75){
                    int toAttend = (3*numMissed)-numAttended;
                    String toAttendAsString = String.valueOf(toAttend);
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou need to attend " + toAttendAsString + " more lectures to get even");
                }
                else{
                    int toMiss = (numAttended-(3*numMissed))/3;
                    String toMissAsString = String.valueOf(toMiss);
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou can bunk " + toMissAsString + " lectures and still be even");
                }

                if (Math.round(attendedPercentage) == 75 ){
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou are even!!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        ButtonAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Attendance.this, "Number Attended:" + numAttended + "Number Missed:" + numMissed, Toast.LENGTH_SHORT).show();
                numAttended++;
                attendedPercentage = 100 * numAttended / (numAttended + numMissed);
                String attendedPercentageAsString = String.valueOf(attendedPercentage);
                reff.child("Percentage").setValue(attendedPercentageAsString);
                reff.child("Number Attended").setValue(numAttended);
                AttendancePercentageTextView.setText("Your Attendance is: " + attendedPercentage +"%");
            }
        });

        ButtonMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Attendance.this, "Number Attended:" + numAttended + "Number Missed:" + numMissed, Toast.LENGTH_SHORT).show();
                numMissed++;
                attendedPercentage =100 * numAttended / (numAttended + numMissed);
                String attendedPercentageAsString = String.valueOf(attendedPercentage);
                reff.child("Percentage").setValue(attendedPercentageAsString);
                reff.child("Number Missed").setValue(numMissed);
                AttendancePercentageTextView.setText("Your Attendance is: " + attendedPercentage +"%");

            }
        });

    }

    /*
    @Override
    protected void onStart(){
        super.onStart();

        mPercentageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                AttendancePercentageTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ButtonAttended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numAttended++;
                double attendedPercentage = 100 * numAttended / (numAttended + numMissed);
                String attendedPercentageAsString = String.valueOf(attendedPercentage);
                mPercentageRef.setValue("Attendance: " + attendedPercentageAsString + "%");

                if(attendedPercentage<=75){
                    int toAttend = (3*numMissed)-numAttended;
                    String toAttendAsString = String.valueOf(toAttend);
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou need to attend " + toAttendAsString + " more lectures to get even");
                }
                else if (attendedPercentage == 75 ){
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou are even!!");
                }
                else{
                    int toMiss = (numAttended-(3*numMissed))/3;
                    String toMissAsString = String.valueOf(toMiss);
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou can bunk " + toMissAsString + " lectures and still be even");
                }
            }
        });

        ButtonMissed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numMissed++;
                float attendedPercentage =100 * numAttended / (numAttended + numMissed);
                String attendedPercentageAsString = String.valueOf(attendedPercentage);
                mPercentageRef.setValue("Attendance: " + attendedPercentageAsString + "%");

                if(attendedPercentage<75){
                    int toAttend = (3*numMissed)-numAttended;
                    String toAttendAsString = String.valueOf(toAttend);
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou need to attend " + toAttendAsString + " more lectures to get even");
                }
                else if (attendedPercentage == 75 ){
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou are even!!");
                }
                else{
                    int toMiss = (numAttended-(3*numMissed))/3;
                    String toMissAsString = String.valueOf(toMiss);
                    AttendMissView.setText("Attended: " + numAttended+ "\nMissed: " + numMissed + "\nTotal Lectures: " + (numAttended+numMissed) + "\nYou can bunk " + toMissAsString + " lectures and still be even");
                }

            }
        });

    }
    */


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
