package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ModuleList extends AppCompatActivity {
    DatabaseReference reff;
    //ListView moduleList;
    //String items[] = new String [] {"Module List", "Module 1"};
    String module_array[] = new String[] {"Module1", "Module2", "Module3", "Module4", "Module5",
            "Module6", "Module7", "Module8", "Module9", "Module10", "Module11", "Module12"};
    //ArrayList<String> moduleItem = new ArrayList<String>();
    ArrayList<String> items = new ArrayList<String>();
    //ArrayAdapter myAdapter1;
    TextView modulesTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        ListView listView = (ListView) findViewById(R.id.moduleList);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        modulesTextView = (TextView) findViewById(R.id.modulesTextView);

        TextView topMsg = findViewById(R.id.modulesTextView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Toast.makeText(ModuleList.this, "ID: " + user_id, Toast.LENGTH_SHORT).show();

        reff = FirebaseDatabase.getInstance().getReference().child("User").
                child(user_id);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Name").getValue() != null) {
                    String username = dataSnapshot.child("Name").getValue().toString();
                    modulesTextView.setText(username + "'s Modules");
                }
                else{
                    modulesTextView.setText("No username");
                }

                //max of 12 modules

                //first delete the previous list
                items.clear();
                adapter.notifyDataSetChanged();

                if(dataSnapshot.child("C_Modules").child("Module1").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module1").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module2").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module2").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module3").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module3").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module4").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module4").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module5").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module5").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module6").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module6").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module7").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module7").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module8").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module8").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module9").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module9").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module10").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module10").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module11").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module11").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("C_Modules").child("Module12").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("C_Modules").child("Module12").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        /*

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //max of 12 modules

                //first delete the previous list
                items.clear();
                adapter.notifyDataSetChanged();

                if(dataSnapshot.child("Module1").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module1").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }


                if(dataSnapshot.child("Module2").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module2").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module3").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module3").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module4").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module4").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module5").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module5").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module6").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module6").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module7").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module7").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module8").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module8").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module9").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module9").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module10").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module10").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module11").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module11").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }

                if(dataSnapshot.child("Module12").child("Title").getValue() != null){
                    items.add(dataSnapshot.child("Module12").child("Title").getValue().toString());
                    adapter.notifyDataSetChanged();

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

         */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //Toast.makeText(ModuleList.this, module_array[position], Toast.LENGTH_SHORT).show();
                Intent jumpToModule = new Intent(ModuleList.this, ModulesNewPage.class);
                jumpToModule.putExtra("module_name", items.get(position));
                jumpToModule.putExtra("module_number", module_array[position]);
                startActivity(jumpToModule);

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


}
