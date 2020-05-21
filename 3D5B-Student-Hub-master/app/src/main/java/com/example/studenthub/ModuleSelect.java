package com.example.studenthub;



import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.view.View;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModuleSelect extends Activity {
    DatabaseReference reff;
    private Button b1;
    // these are the global variables
    Spinner classSpinner, divSpinner;
    // string variable to store selected values
    String selectedClass, selectedDiv;
    String selectedYear = "Year 1";
    String selectedCourse = "Integrated Engineering";
    ArrayList<String> items = new ArrayList<String>();




    ArrayList<String> selectedItems;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moduleselect);
        //create an ArrayList object to store selected items it
        selectedItems=new ArrayList<String>();


        //test
        ListView chl=(ListView) findViewById(R.id.checkable_list);
        //set multiple selection mode
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // String[] items={"Module 1","Module 2","Module 3","Module 4","Module 5","Module 6"};
        //supply data itmes to ListView
        // final ArrayAdapter<String> aa=new ArrayAdapter<String>(this,R.layout.checkable_list_layout,R.id.txt_title,items);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.checkable_list_layout,R.id.txt_title, items);
        //chl.setAdapter(aa);
        chl.setAdapter(adapter);

        //set OnItemClickListener
        chl.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selectedItem = ((TextView) view).getText().toString();
                if(selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });

        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        divSpinner = (Spinner) findViewById(R.id.divSpinner);

        reff = FirebaseDatabase.getInstance().getReference().child("Modules").child(selectedCourse).child(selectedYear);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //first delete the previous list
                items.clear();
                adapter.notifyDataSetChanged();

                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    String current_module = userSnapshot.getKey().toString();
                    items.add(current_module);
                    adapter.notifyDataSetChanged();
                }
                // Toast.makeText(ModuleSelect.this, "Something has happened Updating", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // Class Spinner implementing onItemSelectedListener
        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedClass = parent.getItemAtPosition(position).toString();
                switch (selectedClass)
                {
                    case "Year 1":
                        // assigning div item list defined in XML to the div Spinner
                        divSpinner.setAdapter(new ArrayAdapter<String>(ModuleSelect.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.items_div_class_1)));
                                selectedYear = selectedClass;
                                // Toast.makeText(ModuleSelect.this, selectedYear + ", Course: " + selectedDiv, Toast.LENGTH_SHORT).show();
                        break;

                    case "Year 2":
                        divSpinner.setAdapter(new ArrayAdapter<String>(ModuleSelect.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.items_div_class_2)));
                                selectedYear = selectedClass;
                                // Toast.makeText(ModuleSelect.this, selectedYear + ", Course: " + selectedDiv, Toast.LENGTH_SHORT).show();
                        break;

                    case "Year 3":
                        divSpinner.setAdapter(new ArrayAdapter<String>(ModuleSelect.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.items_div_class_3)));
                                selectedYear = selectedClass;
                                // Toast.makeText(ModuleSelect.this, selectedYear + ", Course: " + selectedDiv, Toast.LENGTH_SHORT).show();
                        break;

                    case "Year 4":
                        divSpinner.setAdapter(new ArrayAdapter<String>(ModuleSelect.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                getResources().getStringArray(R.array.items_div_class_4)));
                                selectedYear = selectedClass;
                                // Toast.makeText(ModuleSelect.this, selectedYear + ", Course: " + selectedDiv, Toast.LENGTH_SHORT).show();
                        break;
                }

                //set divSpinner Visibility to Visible
                divSpinner.setVisibility(View.VISIBLE);

                reff = FirebaseDatabase.getInstance().getReference().child("Modules").child(selectedCourse).child(selectedYear);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //first delete the previous list
                        items.clear();
                        adapter.notifyDataSetChanged();

                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            String current_module = userSnapshot.getKey().toString();
                            items.add(current_module);
                            adapter.notifyDataSetChanged();
                        }
                        // Toast.makeText(ModuleSelect.this, "Something has happened Updating", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });

        // Div Spinner implementing onItemSelectedListener
        divSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDiv = parent.getItemAtPosition(position).toString();
                /*
                    Now that we have both values, lets create a Toast to
                    show the values on screen
                */
                /*
                Toast.makeText(ModuleSelect.this, "\n Class: \t " + selectedClass +
                        "\n Div: \t" + selectedDiv, Toast.LENGTH_LONG).show();

                */
                selectedCourse = selectedDiv;
                // Toast.makeText(ModuleSelect.this, selectedYear + ", Course: " + selectedCourse, Toast.LENGTH_SHORT).show();

                reff = FirebaseDatabase.getInstance().getReference().child("Modules").child(selectedCourse).child(selectedYear);

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //first delete the previous list
                        items.clear();
                        adapter.notifyDataSetChanged();

                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            String current_module = userSnapshot.getKey().toString();
                            items.add(current_module);
                            adapter.notifyDataSetChanged();
                        }
                        // Toast.makeText(ModuleSelect.this, "Something has happened Updating", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }


        });





        b1 = (Button) findViewById(R.id.goback);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Need to add all the modules to this users data base
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                reff = FirebaseDatabase.getInstance().getReference().child("User");
                reff.child(user_id).child("A_Year").setValue(selectedYear);
                reff.child(user_id).child("B_Course").setValue(selectedCourse);
                for(int i = 0; i < selectedItems.size(); i++){
                    reff.child(user_id).child("C_Modules").child("Module" + (i + 1)).child("Title").setValue(selectedItems.get(i));
                    reff.child(user_id).child("C_Modules").child("Module" + (i + 1)).child("Attendance").child("Number Attended").setValue(0);
                    reff.child(user_id).child("C_Modules").child("Module" + (i + 1)).child("Attendance").child("Number Missed").setValue(0);
                    reff.child(user_id).child("C_Modules").child("Module" + (i + 1)).child("Attendance").child("Percentage").setValue(0);
                }
                Intent Login = new Intent(ModuleSelect.this, Login.class);
                startActivity(Login);
            }

        });

    }
    /*

    public void showSelectedItems(View view){
        String selItems="";
        for(String item:selectedItems){
            if(selItems=="")
                selItems=item;
            else
                selItems+="/"+item;
        }


        Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();
    }


     */



}

