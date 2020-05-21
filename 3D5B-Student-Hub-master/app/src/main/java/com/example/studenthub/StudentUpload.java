package com.example.studenthub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StudentUpload extends AppCompatActivity {
    TextView mtext;
    TextView SolutionsModule;
    EditText editPDF;
    Button mUpload;
    Button mBack;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_upload);

        mtext = findViewById(R.id.textView9);
        String text = "Please provide a brief title and description regarding the solution you want to upload. " +
                    "The title should include the module name and number whereas the description should " +
                    "consist of the exam paper year and the question number.";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

            }
        };
        ss.setSpan(clickableSpan, 0, 226, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mtext.setText(ss);
        mtext.setMovementMethod(LinkMovementMethod.getInstance());


        Bundle bn = getIntent().getExtras();
        final String name = bn.getString("module_name");
        SolutionsModule = (TextView) findViewById(R.id.SolutionsModule);
        SolutionsModule.setText(String.valueOf(name));

        editPDF = (EditText) findViewById(R.id.txt_PDF);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadForReview");
        mUpload = (Button) findViewById(R.id.btn_upload);
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();

            }
        });
        /*mBack = (Button) findViewById(R.id.backButton);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent back = new Intent(StudentUpload.this, SolutionsDownload.class);
                startActivity(back);
        }
    });*/
}






    private void selectPDFFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData()!=null){

            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setTitle("Uploading...");

        StorageReference reference = storageReference.child("uploadForReview/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url = uri.getResult();

                        uploadPDF uploadPDF = new uploadPDF(editPDF.getText().toString(), url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadPDF);
                        Toast.makeText(StudentUpload.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+ (int)progress+ "%");


            }
        });

    }
}
