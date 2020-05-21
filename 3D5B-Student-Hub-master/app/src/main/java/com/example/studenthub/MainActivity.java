package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button HomeBtn = (Button) findViewById(R.id.Homebutton);
        HomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpToHome = new Intent(MainActivity.this, ModuleList.class);
                jumpToHome.putExtra("username", "admin");
                startActivity(jumpToHome);
            }
        });

        Button ReviewBtn = (Button) findViewById(R.id.Reviewbutton);
        ReviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Review.class));
            }
        });

        Button LoginBtn = (Button) findViewById(R.id.Loginbutton);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }
}
