package com.organmanage.firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class Recepient extends AppCompatActivity {


private TabLayout tabLayout;
private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepient);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() ==null){

            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

         tabLayout = findViewById(R.id.re_tab_layout);

        ViewPager viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter pageAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null)

        {

            auth.signOut();

            startActivity(new Intent(Recepient.this, LoginActivity.class));
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
                intent= new Intent(Recepient.this,LoginActivity.class);
                this.startActivity(intent);
                finish();
                break;
            case R.id.reset:
                intent= new Intent(getApplicationContext(),DoctorActivity.class);
                this.startActivity(intent);
                finish();

                break;

        }
        return(super.onOptionsItemSelected(item));
    }
}
