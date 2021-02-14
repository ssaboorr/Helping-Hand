package com.example.apps.helpinghand;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.speech.tts.TextToSpeech;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class Mute_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton speak,speak1,delete,logout;
    EditText word,no;
    TextToSpeech mytts;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mute_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        speak=(ImageButton)findViewById(R.id.p_button);
        speak1=(ImageButton)findViewById(R.id.n_button);
        word=(EditText)findViewById(R.id.mspeak);
        no=(EditText)findViewById(R.id.nspeak);
        delete=(ImageButton)findViewById(R.id.clear);
        logout=findViewById(R.id.logout);

        initializeTextToSpeech();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=word.getText().toString();
                say(s);
            }
        });
        speak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=no.getText().toString();
                say(s);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word.setText("");
                no.setText("");
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0)
                {
                    Toast.makeText(Mute_page.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    mytts.setLanguage(Locale.ENGLISH);

                }
            }
        });
    }

    private void say(String message) {
        if (Build.VERSION.SDK_INT>=21)
        {
            mytts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            mytts.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mute_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_personal_detail) {
            // Handle the camera action

            Intent intent= new Intent(Mute_page.this,Personal_Details.class);
            startActivity(intent);

        } else if (id == R.id.nav_greetings) {


            Intent intent= new Intent(Mute_page.this,Greetings.class);
            startActivity(intent);

        } else if (id == R.id.nav_tp) {


            Intent intent= new Intent(Mute_page.this,Travels_Places.class);
            startActivity(intent);

        } else if (id == R.id.nav_user) {

            Intent intent= new Intent(Mute_page.this,User_choice.class);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
