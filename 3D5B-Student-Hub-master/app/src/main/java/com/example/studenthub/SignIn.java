package com.example.studenthub;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class SignIn extends AppCompatActivity {
    private static final String TAG = "SignIn";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    Spinner year, Course;
    ArrayList<String> arrayList_year;
    ArrayAdapter<String> arrayAdapter_year;

    ArrayList<String> arrayList_year1, arrayList_year2, arrayList_year3, arrayList_year4, arrayList_year5;
    ArrayAdapter<String> arrayAdapter_Course;

    DatabaseReference reff;


    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mStudentNo;

    private Button mSignUp;
    private Button mLogIn;
    //private Button mSelectModule;

    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        mLastName = (EditText) findViewById(R.id.LastName);
        mStudentNo = (EditText) findViewById(R.id.StudentID);

        reff = FirebaseDatabase.getInstance().getReference().child("User");

        // Code for Date Picker In DOB Column
        mDisplayDate = (TextView) findViewById(R.id.d_o_b);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignIn.this, android.R.style.Widget_DeviceDefault, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "Date Of Birth : dd/mm/yyyy" + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        // Code Ends

        /*year = (Spinner) findViewById(R.id.year);
        Course = (Spinner) findViewById(R.id.year);
        year = (Spinner) findViewById(R.id.year);
        Course = (Spinner) findViewById(R.id.course);
        arrayList_year = new ArrayList<>();
        arrayList_year.add("Select Year");
        arrayList_year.add("Year 1");
        arrayList_year.add("Year 2");
        arrayList_year.add("Year 3");
        arrayList_year.add("Year 4");
        arrayLi st_year.add("Year 5");
        arrayAdapter_year = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_black, arrayList_year);
        year.setAdapter(arrayAdapter_year);
        //Course Spinner process connected with year
        arrayList_year1 = new ArrayList<>();
        arrayList_year1.add("Course");
        arrayList_year1.add("Engineering");
        arrayList_year1.add("BESS");
        arrayList_year1.add("Global Business");
        arrayList_year1.add("Law ");
        arrayList_year1.add("Arts");
        arrayList_year2 = new ArrayList<>();
        arrayList_year2.add("Course");
        arrayList_year2.add("Dual BA");
        arrayList_year2.add("Sociology");
        arrayList_year2.add("Computer Science");
        arrayList_year2.add("Clinical Studies");
        arrayList_year2.add("Speech Language");
        arrayList_year3 = new ArrayList<>();
        arrayList_year3.add("Course");
        arrayList_year3.add("Engineering");
        arrayList_year3.add("BESS");
        arrayList_year3.add("Global Business");
        arrayList_year3.add("Law ");
        arrayList_year3.add("Arts");
        arrayList_year4 = new ArrayList<>();
        arrayList_year4.add("Course");
        arrayList_year4.add("Dual BA");
        arrayList_year4.add("Sociology");
        arrayList_year4.add("Computer Science");
        arrayList_year4.add("Clinical Studies");
        arrayList_year4.add("Speech Language");
        arrayList_year5 = new ArrayList<>();
        arrayList_year5.add("Course");
        arrayList_year5.add("Engineering MAI");
        arrayList_year5.add("MSc Finance");
        arrayList_year5.add("MSc Marketing");
        arrayList_year5.add("MSc Management");
        arrayList_year5.add("PHD");
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    arrayAdapter_Course = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_black, arrayList_year1);
                }
                if (position == 2) {
                    arrayAdapter_Course = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_black, arrayList_year2);
                }
                if (position == 3) {
                    arrayAdapter_Course = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_black, arrayList_year3);
                }
                if (position == 4) {
                    arrayAdapter_Course = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_black, arrayList_year4);
                }
                if (position == 5) {
                    arrayAdapter_Course = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_black, arrayList_year5);
                }
                Course.setAdapter(arrayAdapter_Course);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
         */


        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.emailTextView);
        mPassword = (EditText) findViewById(R.id.passwordTextView);
        mFirstName = (EditText) findViewById(R.id.FirstName);

        mSignUp = (Button) findViewById(R.id.SignInButton);
        mLogIn = (Button) findViewById(R.id.LoggingButton);
        /*mSelectModule = (Button) findViewById(R.id.SelectModuleBtn);
        mSelectModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ModuleReg= new Intent(SignIn.this, ModuleSelect.class);
                startActivity(ModuleReg);
            }
        });
         */

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, Login.class);
                startActivity(intent);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                final String eml= mEmail.getText().toString().trim();
                final String pwd= mPassword.getText().toString().trim();
                final String firstName= mFirstName.getText().toString().trim();


                ValidateEmail(mEmail);


                if (mEmail.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty() || mFirstName.getText().toString().isEmpty()){
                    Toast.makeText(SignIn.this, "One of the following fields is empty and must be completed: First Name, Email, Password", Toast.LENGTH_SHORT).show();
                    return;
                }



                inProgress(true);
                mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignIn.this, "User Registered Successfully!", Toast.LENGTH_LONG).show();
                                inProgress(false);
                                // Need to create branch in fire base for user
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                String first_name = mFirstName.getText().toString();
                                reff.child(user_id).child("Name").setValue(first_name);
                                Intent ModuleReg1 = new Intent(SignIn.this, ModuleSelect.class);
                                startActivity(ModuleReg1);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        inProgress(false);
                        Toast.makeText(SignIn.this, "Sign Up failed!" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

}

    private boolean ValidateEmail(EditText mEmail){
        String emailInput= mEmail.getText().toString();
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Toast.makeText(this,"Email Validated Successfully",Toast.LENGTH_SHORT).show();
            return true;
        }  else {
            Toast.makeText(this,"InValid Email Address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void inProgress(boolean x) {
        if (x) {
            mProgressBar.setVisibility(View.VISIBLE);
            mSignUp.setEnabled(false);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mSignUp.setEnabled(true);
        }
    }
}































