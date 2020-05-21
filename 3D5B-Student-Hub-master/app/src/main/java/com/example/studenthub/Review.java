package com.example.studenthub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Review extends AppCompatActivity {
    DatabaseReference reff;
    DatabaseReference comment_reff;
    DatabaseReference user_reff;
    RatingBar ratingBar;
    EditText enterRatingText;
    EditText ratingInfo;
    EditText ModuleName;
    Button submitRatingButton;
    Button updateRatingButton;
    float rating_sum = 0;
    float number_of_ratings = 0;
    float average_rating = 0;
    float number_of_comments = 0;
    Button postCommentButton;
    EditText postCommentText;
    RecyclerView commentDisplay;
    ArrayList<ReviewDisplay> comments = new ArrayList<>();
    DatabaseReference mDatabase;
    MyRecyclerViewAdapter adapter;
    ListView commentThread;
    ArrayAdapter myAdapter1;
    String activeUser = "Admin";
    String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Bundle bn = getIntent().getExtras();
        String modulename = bn.getString("modulename");


        ModuleName = (EditText) findViewById(R.id.ModuleName);
        ModuleName.setText(String.valueOf(modulename));
        disableEditText((ModuleName));
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        submitRatingButton = (Button) findViewById(R.id.submitRatingButton);
        ratingBar.setRating(0);
        ratingInfo = (EditText) findViewById(R.id.ratingInfo);
        disableEditText((ratingInfo));
        //commentThread = (ListView) findViewById(R.id.commentThread);
        postCommentButton = (Button) findViewById(R.id.postCommentButton);
        postCommentText = (EditText) findViewById(R.id.postCommentText);
        commentDisplay = (RecyclerView) findViewById(R.id.CommentRecycler);
        commentDisplay.setHasFixedSize(true);
        commentDisplay.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, comments);
        commentDisplay.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_reff = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        user_reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Name").getValue() != null) {
                    username = dataSnapshot.child("Name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        comment_reff = FirebaseDatabase.getInstance().getReference().child("Modules").
                child("Computer Engineering").child("Year 3").child(modulename).
                child("Reviews Page").child("Comments");

        comment_reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ReviewDisplay> comms = new ArrayList<>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {


                    if (userSnapshot.getKey().contains("Number of Comments")){
                        number_of_comments = Float.parseFloat(userSnapshot.getValue().toString());
                        continue;
                    }else{
                        String username = userSnapshot.child("username").getValue().toString();
                        String comment = userSnapshot.child("comment").getValue().toString();
                        String rating = userSnapshot.child("rating").getValue().toString();
                        float rate = Float.parseFloat(rating);
                        comments.add(new ReviewDisplay(username,comment,rate));
                    }


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Modules").
                child("Computer Engineering").child("Year 3").child(modulename).
                child("Reviews Page").child("Rating");


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rating_sum = Float.parseFloat(dataSnapshot.child("Rating Sum").getValue().toString());
                average_rating = Float.parseFloat(dataSnapshot.child("Average Rating").getValue().toString());
                String rating_string = dataSnapshot.child("Average Rating").getValue().toString();
                number_of_ratings = Float.parseFloat(dataSnapshot.child("Number of Ratings").getValue().toString());
                ratingInfo.setText(String.format("%.2f", average_rating));
                ratingBar.setRating(average_rating);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ratingInfo.setText("Error updating");

            }
        });



        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float star_value = ratingBar.getRating();
                //Toast.makeText(Review.this, "Stars: " + star_value, Toast.LENGTH_SHORT).show();
                if(star_value < 0 || star_value > 5){
                    Toast.makeText(Review.this, "Error: Rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
                }
                else{
                    rating_sum = rating_sum + star_value;
                    number_of_ratings++;
                    average_rating = rating_sum / number_of_ratings;
                    ratingBar.setRating(average_rating);
                    String string_rating = String.format("%.02f", average_rating);
                    //ratingInfo.setText("Overall Rating:" + string_rating);
                    reff.child("Average Rating").setValue(average_rating);
                    reff.child("Number of Ratings").setValue(number_of_ratings);
                    reff.child("Rating Sum").setValue(rating_sum);

                }
            }
        });


        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = postCommentText.getText().toString();
                if (TextUtils.isEmpty(comment)){
                    Toast.makeText(Review.this, "Comment cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                float star_value = ratingBar.getRating();
                submitRatingButton.callOnClick();

                number_of_comments++;
                comment_reff.child("Number of Comments").setValue(number_of_comments);
                DatabaseReference newRef = comment_reff.push();

                ReviewDisplay newRev = new ReviewDisplay(username,comment,star_value);
                newRef.setValue(newRev);
                Toast.makeText(Review.this, "Comment posted.", Toast.LENGTH_SHORT).show();
                postCommentText.setText("");
                recreate();

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(Review.this, "Stars: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        //editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
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
