package com.organmanage.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DoctorActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  ListView mListView;
    private  ArrayList<recipient> list_recepint;
    String ReqDate,name,organtype, id,  usrid,bloodGrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        auth = FirebaseAuth.getInstance();

          if(auth.getCurrentUser() ==null){

              startActivity(new Intent(getApplicationContext(),LoginActivity.class));
          }

        mListView = (ListView) findViewById(R.id.list_view_recepient);

        TextView textView = new TextView(getApplicationContext());
        textView.setText("     Name        Organ Type     ");
        textView.setTextSize(18);
        textView.setFreezesText(true);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        mListView.addHeaderView(textView);


        databaseReference = FirebaseDatabase.getInstance().getReference("Recepient");
        //  Log.e("Get Data", post.<YourMethod>());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_recepint= new ArrayList<recipient>();
                recipient re;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    for(DataSnapshot snapshot:postSnapshot.getChildren()){
                         String isAssigned= snapshot.child("isAssigned").getValue().toString();
                       if(isAssigned.equals("false") ){
                            usrid= postSnapshot.getKey();
                            id= snapshot.getKey();
                            ReqDate=snapshot.child("requirdDateTill").getValue().toString();
                            name= snapshot.child("name").getValue().toString();
                            organtype= snapshot.child("organType").getValue().toString();
                            bloodGrp= snapshot.child("bloodGroup").getValue().toString();
                           re= new recipient();
                           re.setUserGuid(usrid);
                           re.setId(id);
                           re.setName(name);
                           re.setOrganType(organtype);
                           re.setRequirdDateTill(ReqDate);
                           re.setBloodGroup(bloodGrp);
                           list_recepint.add(re);
                       }


                    }
                    DoctorRecepientAdapter adapter=new DoctorRecepientAdapter(list_recepint,getApplicationContext());
                    mListView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
        case R.id.logout:
            auth.signOut();
             intent= new Intent(DoctorActivity.this,LoginActivity.class);
            this.startActivity(intent);
            finish();
            break;
        case R.id.reset:
             intent= new Intent(DoctorActivity.this,DoctorActivity.class);
            this.startActivity(intent);
            finish();

            break;

    }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null)

        {

            auth.signOut();

            startActivity(new Intent(DoctorActivity.this, LoginActivity.class));
        }
    }
}
