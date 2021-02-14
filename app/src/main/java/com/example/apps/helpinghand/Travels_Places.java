package com.example.apps.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class Travels_Places extends AppCompatActivity {
    ImageButton sub,hos,air,beach,shop,taxi,mu,mov,hotel,res;
    TextToSpeech mytts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels__places);
        sub=(ImageButton)findViewById(R.id.subway);
        hos=(ImageButton)findViewById(R.id.hospital);
        air=(ImageButton)findViewById(R.id.airplane);
        beach=(ImageButton)findViewById(R.id.beach);
        shop=(ImageButton)findViewById(R.id.shopping);
        taxi=(ImageButton)findViewById(R.id.Taxi);
        mu=(ImageButton)findViewById(R.id.muesum);
        mov=(ImageButton)findViewById(R.id.movies);
        hotel=(ImageButton)findViewById(R.id.Hotel);
        res=(ImageButton)findViewById(R.id.restaurant);
        initializeTextToSpeech();
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                say("Subway");
            }
        });
        hos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Hospital");
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Hotel");
            }
        });
        beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Beach");
            }
        });
        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Taxi stand");
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Shopping Centre");
            }
        });
        mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Musuem");
            }
        });
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Restaurant");
            }
        });
        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Airport");
            }
        });
        mov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    say("Movies");
            }
        });
    }

    private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0)
                {
                    Toast.makeText(Travels_Places.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
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
}
