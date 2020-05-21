package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModulesNewPage extends AppCompatActivity {
    private Button review;
    private Button attendance;
    private Button grade;
    DatabaseReference reff;
    EditText lectureName;
    EditText lectureEmail;
    Button mSolutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_new_page);





        TextView Display = findViewById(R.id.textView2);

        Bundle bn = getIntent().getExtras();
        final String name = bn.getString("module_name");
        final String module_number = bn.getString("module_number");
        Display.setText(String.valueOf(name));
        //Display.setText("Module: " + module_number);
        Display.setFocusable(false);
        Display.setCursorVisible(false);
        Display.setKeyListener(null);

        /*
        bn = getIntent().getExtras();
        final String module_number = bn.getString("module_number");
        Display.setText("Module: " + module_number);
         */

        reff = FirebaseDatabase.getInstance().getReference().child("Modules").
                child("Computer Engineering").child("Year 3").child(name);

        lectureName = (EditText) findViewById(R.id.lectureName);
        disableEditText(lectureName);
        lectureEmail = (EditText) findViewById(R.id.lectureEmail);
        disableEditText(lectureEmail);



        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String lec_name = dataSnapshot.child("Lecture Name").getValue().toString();
                String lec_email = dataSnapshot.child("Lecture Email").getValue().toString();

                lectureName.setText(lec_name);
                lectureEmail.setText(lec_email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        review = findViewById(R.id.ReviewBtn);
        review.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //startActivity(new Intent(ModulesNewPage.this, Review.class));

                Intent jumpToReview = new Intent(ModulesNewPage.this, Review.class);
                jumpToReview.putExtra("modulename",name);
                //jumpToAttendance.putExtra("modulenumber",module_number);
                startActivity(jumpToReview);

            }

        });

        attendance = findViewById(R.id.AttendanceBtn);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToAttendance = new Intent(ModulesNewPage.this, Attendance.class);
                jumpToAttendance.putExtra("modulename",name);
                jumpToAttendance.putExtra("modulenumber",module_number);
                Toast.makeText(ModulesNewPage.this, module_number, Toast.LENGTH_SHORT).show();
                startActivity(jumpToAttendance);
            }
        });

        
        mSolutions = findViewById(R.id.SolutionsBtn);
        mSolutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToSolutions = new Intent(ModulesNewPage.this, SolutionsDownload.class);
                jumpToSolutions.putExtra("module_name",name);
                startActivity(jumpToSolutions);

            }
        });

        grade = findViewById(R.id.GradeAnalyticsBtn);
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToGrade = new Intent(ModulesNewPage.this, GradeAnalytics.class);
                jumpToGrade.putExtra("module_name", name);
                startActivity(jumpToGrade);
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


    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        //editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

}
