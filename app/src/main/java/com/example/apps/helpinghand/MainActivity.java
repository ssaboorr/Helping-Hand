package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.BlockingDeque;

public class MainActivity extends AppCompatActivity {
    TextToSpeech mytts;
    String s="Are you blind person";
    ArrayList<String> results;
    FirebaseAuth mAuth;
    Button mbtn;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtn=findViewById(R.id.ebtn);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null)
        {
           retrive();
        }
        else {
            initializeTextToSpeech();
            askuser();
        }
        startService(new Intent(getApplicationContext(), LockService.class));
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,mute_log.class);
                startActivity(intent);
                if (mytts.isSpeaking())
                {
                    mytts.stop();
                }
                finish();
            }
        });
    }

    private void askuser() {

        final Handler h1=new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Are You A Blind User");
                h1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                    }
                },3000);
            }
        },5000);
    }

   private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0)
                {
                    Toast.makeText(MainActivity.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    mytts.setLanguage(Locale.ENGLISH);
                    say("Welcome to Helping Hand");
                }
            }
        });
    }

    private void say(String s) {
        if (Build.VERSION.SDK_INT>=21)
        {
            mytts.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            mytts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }
    }


    private void speak() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null){
            switch (requestCode) {
                case 1:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String s = results.get(0);
                    if (s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("I am")) {
                        String status="yes";

                        Intent intent = new Intent(MainActivity.this, Blind_Page.class);
                        intent.putExtra("status",status);
                        startActivity(intent);
                        finish();
                        break;
                    } else {

                        Intent intent= new Intent(MainActivity.this,Blind_Page.class);
                        startActivity(intent);

                    }
            }
        }
    }
    public void  retrive()
    {
       final String email=mAuth.getCurrentUser().getEmail();
        reference= FirebaseDatabase.getInstance().getReference().child("User_Info").child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    String uemail=dataSnapshot1.child("uemail").getValue().toString();
                    if (email.equalsIgnoreCase(uemail))
                    {
                        String ustatus=dataSnapshot1.child("status").getValue().toString();
                        if (ustatus.equalsIgnoreCase("yes"))
                        {
                            startActivity(new Intent(MainActivity.this,Blind_Page.class));
                            finish();
                        }
                        else if (ustatus.equalsIgnoreCase("no"))
                        {
                            startActivity(new Intent(MainActivity.this,Mute_page.class));
                            finish();
                        }
                    }
                }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
