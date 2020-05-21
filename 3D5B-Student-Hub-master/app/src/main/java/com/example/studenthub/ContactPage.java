package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ContactPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);




        /*TextView t4 = (TextView) findViewById(R.id.c4);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Intent.ACTION_DIAL);
                intent4.setData(Uri.parse("tel: 35318962097"));
                startActivity(intent4);
            }
        });
        TextView t5 = (TextView) findViewById(R.id.c5);
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(Intent.ACTION_DIAL);
                intent5.setData(Uri.parse("tel: 35318961177"));
                startActivity(intent5);
            }
        });
        TextView t6 = (TextView) findViewById(R.id.c6);
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(Intent.ACTION_DIAL);
                intent6.setData(Uri.parse("tel: 35318961721"));
                startActivity(intent6);
            }
        });



        TextView t2 = (TextView) findViewById(R.id.c2);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel: 35318964500"));
                startActivity(intent2);
            }
        });
        TextView t3 = (TextView) findViewById(R.id.c3);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Intent.ACTION_DIAL);
                intent3.setData(Uri.parse("tel: 35318961556"));
                startActivity(intent3);
            }
        });
        TextView t1 = (TextView) findViewById(R.id.c1);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 35318961999"));
                startActivity(intent);
            }
        });*/
    }
    public void returnActivity() {
        Intent intentR = new Intent(ContactPage.this,Login.class);
        startActivity(intentR);
    }
}
