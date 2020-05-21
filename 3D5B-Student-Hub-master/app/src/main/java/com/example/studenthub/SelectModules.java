package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectModules extends AppCompatActivity {
    private Button register_modules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_modules);

        register_modules = (Button) findViewById(R.id.registerModules);
        register_modules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
    }

    public void openLogin() {
        Intent intent1 =  new Intent(this, Login.class );
        startActivity(intent1 );
    }
}
