package com.example.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.hsalf.smileyrating.SmileyRating;

import java.util.ArrayList;
import java.util.List;

public class StudentPeerReview extends AppCompatActivity {
    TextView SolutionsModuleText;
    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<uploadPDF> uploadPDFS;
    Button mSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_peer_review);

        mSubmit = findViewById(R.id.button2);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Review Submitted!", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(StudentPeerReview.this, SolutionsDownload.class);
                //startActivity(intent);
            }
        });

        Bundle bn = getIntent().getExtras();
        final String name = bn.getString("module_name");
        SolutionsModuleText = (TextView) findViewById(R.id.SolutionsModuleText);
        SolutionsModuleText.setText(String.valueOf(name));

        myPDFListView = (ListView) findViewById(R.id.myList);
        uploadPDFS = new ArrayList<>();

        viewAllFiles();
        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uploadPDF uploadPDF = uploadPDFS.get(position);

                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadPDF.getUrl()));
                startActivity(intent);

            }
        });
        //Rating Bar
        SmileyRating smileRating=(SmileyRating)findViewById(R.id.smile_rating);
        smileRating.setSmileySelectedListener(new SmileyRating.OnSmileySelectedListener() {

            @Override
            public void onSmileySelected(SmileyRating.Type type) {
                if (SmileyRating.Type.GREAT == type) {
                    Toast.makeText(StudentPeerReview.this,"GREAT",Toast.LENGTH_SHORT).show();
                }
                if (SmileyRating.Type.GOOD == type) {
                    Toast.makeText(StudentPeerReview.this,"GOOD",Toast.LENGTH_SHORT).show();
                }
                if (SmileyRating.Type.BAD == type) {
                    Toast.makeText(StudentPeerReview.this,"BAD",Toast.LENGTH_SHORT).show();
                }
                if (SmileyRating.Type.OKAY == type) {
                    Toast.makeText(StudentPeerReview.this,"OKAY",Toast.LENGTH_SHORT).show();
                }
                if (SmileyRating.Type.TERRIBLE == type) {
                    Toast.makeText(StudentPeerReview.this,"TERRIBLE",Toast.LENGTH_SHORT).show();
                }

                int rating = type.getRating();
            }
        });
    }
    private void viewAllFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadForReview");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    uploadPDF uploadPDF = postSnapshot.getValue(com.example.studenthub.uploadPDF.class);
                    uploadPDFS.add(uploadPDF);
                }
                String[] uploads = new String[uploadPDFS.size()];
                for (int i=0; i<uploads.length; i++){
                    uploads[i] = uploadPDFS.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,uploads){

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent ) {
                        View view = super.getView(position, convertView, parent);
                        TextView myText = (TextView) view.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);

                        return view;
                    }
                };
                myPDFListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
