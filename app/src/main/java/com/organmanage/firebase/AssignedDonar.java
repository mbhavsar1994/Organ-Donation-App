package com.organmanage.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AssignedDonar extends AppCompatActivity  {

    private FirebaseAuth auth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  ListView mListView;
    private  String recepientGuid,orgTyp,recipient_reqGuid,recepient_name,recepient_bldGrp;
    private ArrayList<donar>  donar_list;
    private  ArrayList<String> recept_guids;
    private  String  id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_donar);

        auth = FirebaseAuth.getInstance();

        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        if(bundle!=null){

            recepientGuid= bundle.getString("usrGuid");
            recipient_reqGuid=bundle.getString("Guid");
             orgTyp=bundle.getString("OrganType");
            recepient_name=bundle.getString("name");
            recepient_bldGrp=bundle.getString("bloodGrp");
            TextView tv_og= (TextView)findViewById(R.id.tv_reqorgn);
            tv_og.setText(orgTyp);
        }
        TextView tv_blg= (TextView)findViewById(R.id.tv_rep_blg);
        tv_blg.setText(recepient_bldGrp);
        TextView tv_rep_name=(TextView)findViewById(R.id.tv_rep_name);
        tv_rep_name.setText(recepient_name);
        recept_guids= new ArrayList<String>();
        recept_guids.add(recepientGuid);
        recept_guids.add(recipient_reqGuid);
        mListView = (ListView) findViewById(R.id.list_view_donar);


        TextView textView = new TextView(getApplicationContext());
        textView.setText("     Name        City     ");
        textView.setTextSize(18);
        textView.setFreezesText(true);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        mListView.addHeaderView(textView);
        databaseReference = FirebaseDatabase.getInstance().getReference("Donars");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donar_list= new ArrayList<donar>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    for(DataSnapshot snapshot:postSnapshot.getChildren()){

                        String orgnTyp= snapshot.child("organType").getValue().toString();
                        String isDonated= snapshot.child("isDonated").getValue().toString();
                       if(isDonated.equals("false") && orgTyp.equals(orgnTyp)){
                           id= snapshot.getKey();
                          String name= snapshot.child("name").getValue().toString();
                          String city= snapshot.child("city").getValue().toString();
                          String uId= postSnapshot.getKey();
                          donar d= new donar();
                          d.id=id;
                          d.name=name;
                          d.organType=orgnTyp;
                          d.city=city;
                          d.userGuid=uId;
                          donar_list.add(d);
                          DoctorDonarAdapter adapter=new DoctorDonarAdapter(donar_list,recept_guids,getApplicationContext());
                          mListView.setAdapter(adapter);
                    }
                }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });







    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null)

        {

            auth.signOut();

            startActivity(new Intent(AssignedDonar.this, LoginActivity.class));
        }
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
                intent= new Intent(AssignedDonar.this,LoginActivity.class);
                this.startActivity(intent);
                finish();
                break;
            case R.id.reset:
                intent= new Intent(AssignedDonar.this,DoctorActivity.class);
                this.startActivity(intent);
                finish();

                break;

        }
        return(super.onOptionsItemSelected(item));
    }
}
