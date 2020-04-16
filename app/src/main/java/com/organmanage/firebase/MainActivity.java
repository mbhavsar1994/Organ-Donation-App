package com.organmanage.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

     Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private  FirebaseDatabase firebaseDatabase;
    private  DatabaseReference databaseReference;
    String uid, name, city, phone, country,userType,email;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        auth = FirebaseAuth.getInstance();
        // User is logged in
        if (auth.getCurrentUser() != null)
            user =  auth.getCurrentUser();
        else {

            auth.signOut();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        uid = user.getUid();

     //   Toast.makeText(MainActivity.this,uid,Toast.LENGTH_LONG).show();

        databaseReference=firebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               name = dataSnapshot.child("Users").child(uid).child("name").getValue(String.class);
//                email = dataSnapshot.child("Users").child(uid).child("email").getValue(String.class);
//                city = dataSnapshot.child("Users").child(uid).child("city").getValue(String.class);
//                country = dataSnapshot.child("Users").child(uid).child("country").getValue(String.class);
                userType = dataSnapshot.child("Users").child(uid).child("userType").getValue(String.class);
            //    phone = dataSnapshot.child("Users").child(uid).child("phone").getValue(String.class);


                TextView textView= (TextView)findViewById(R.id.tv_hello);
                textView.setText( "Welcome back " + name);


                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        progressBar.setVisibility(View.GONE);
                        if (userType.equals("Recepient"))
                        {

                            startActivity(new Intent(MainActivity.this, Recepient.class));

                        }
                        else if (userType.equals("Donar")   ){

                            startActivity(new Intent(MainActivity.this, DonarActivity.class));

                        }
                        else if (userType.equals("Doctor")){

                            startActivity(new Intent(MainActivity.this, DoctorActivity.class));
                        }

                    }
                }, 3000);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//
//        Button btn= (Button) findViewById(R.id.btn_signout);
//        btn.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View view)  {
//            auth.signOut();
//
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            finish();
//        }
//});

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (auth.getCurrentUser() == null)

        {
            auth.signOut();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
                intent= new Intent(MainActivity.this,LoginActivity.class);
                this.startActivity(intent);
                finish();
                break;
            case R.id.reset:
                if (userType.equals("Recepient"))
                {

                    startActivity(new Intent(MainActivity.this, Recepient.class));

                }
                else if (userType.equals("Donar")   ){

                    startActivity(new Intent(MainActivity.this, DonarActivity.class));

                }
                else if (userType.equals("Doctor")){

                    startActivity(new Intent(MainActivity.this, DoctorActivity.class));
                }

                finish();

                break;

        }
        return(super.onOptionsItemSelected(item));
    }


}
