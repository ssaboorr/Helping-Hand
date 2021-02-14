package com.example.apps.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class Greetings extends AppCompatActivity {
    TextToSpeech mytts;
    ImageButton gm, ge, gn, gb, hello, how, cong, thank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);
        gm = (ImageButton)findViewById(R.id.name1);
        ge = (ImageButton)findViewById(R.id.age1);
        gn = (ImageButton)findViewById(R.id.gn);
        gb =(ImageButton) findViewById(R.id.gb);
        hello = (ImageButton)findViewById(R.id.hello);
        cong =(ImageButton) findViewById(R.id.congrats);
        thank =(ImageButton) findViewById(R.id.tk);
        how =(ImageButton) findViewById(R.id.howare);

        initializeTextToSpeech();
        gm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Good Morning", Toast.LENGTH_SHORT).show();

                say("Good morning");
            }
        });
        ge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Good Evening", Toast.LENGTH_SHORT).show();

                say("Good Evening");
            }
        });
        gn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Good Night", Toast.LENGTH_SHORT).show();

                say("Good Night");
            }
        });
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "How are You", Toast.LENGTH_SHORT).show();

                say("How are You");
            }
        });
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Hello", Toast.LENGTH_SHORT).show();

                say("Hello");
            }
        });
        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Good Bye", Toast.LENGTH_SHORT).show();

                say("Good Bye");
            }
        });
       thank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Than You", Toast.LENGTH_SHORT).show();

                say("Thank You");
            }
        });
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Greetings.this, "Congrats", Toast.LENGTH_SHORT).show();

                say("Congrats");
            }
        });

    }
    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Greetings.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mytts.setLanguage(Locale.ENGLISH);

                }
            }
        });
    }
    private void say(String s) {
        if (Build.VERSION.SDK_INT>=21)
        {
            mytts.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
            mytts.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            mytts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }
    }
    }


