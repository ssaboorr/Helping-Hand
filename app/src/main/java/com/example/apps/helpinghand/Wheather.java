package com.example.apps.helpinghand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class Wheather extends AppCompatActivity {
    TextView city;
    TextToSpeech mytts;
    TextView result;
    String teemp,teemp1;
    double teemp2;
    class Weather extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void search() {

        String cname = city.getText().toString().trim();
        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q=" + cname + "&appid=b6907d289e10d714a6e88b30761fae22").get();
            Log.i("content", content);
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String maintemperature=jsonObject.getString("main");
           final double visibility;
            Log.i("weatherData", weatherData);

            JSONArray array = new JSONArray(weatherData);
            String main = "";
            String description = "";
            String temperature="";
            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("description");
                description=weatherPart.getString("description");
            }
            JSONObject mainPart=new JSONObject(maintemperature);
            temperature=mainPart.getString("temp");
            visibility=Double.parseDouble(jsonObject.getString("visibility"));
            int visibilityinkg=(int)visibility/10000;
            Log.i("Temperature",temperature);

            Log.i("main", main);
            Log.i("description", description);
            teemp=description;
            teemp1=temperature;
            teemp2=visibility;
            result.setText("Main:"+main+"" +
                    "\nDescription:"+description+
                    "\nTemperature:"+temperature+"°C"+
                    "\nVisibility:"+visibilityinkg+"K"
            );
            say(main);
            Handler h=new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(teemp.contains("smoke") ||teemp.contains("sunny")){
                        say("Its"+teemp+" Today Nice weather");
                    }
                    else if (teemp.contains("rain")){
                        say("Its "+teemp+" go out With Umbrella");
                    }
                    else if(teemp.contains("Haze")|| teemp.contains("winter"))
                        say("Its "+teemp+" Outside wear your jacket");
                    else{
                        say("Its"+teemp);
                    }
                }

            },2000);
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    say(teemp1+"degree Celsius");
                }
            },5000);
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(teemp2<1000){
                    say("fog");}
                    else if(teemp2>=1000 ||teemp2<=2000){
                        say("Mist");
                    }
                    else if(teemp2>=2000 ||teemp2<=5000){
                        say("Haze");
                    }
                    else if(teemp2>=0 ||teemp2<=1000){
                        say("Danger");
                    }


                }
            },7000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Wheather.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("you are on Weather page");
                }
            }
        });
    }

    private void say(String message) {
        if (Build.VERSION.SDK_INT >= 21) {
            mytts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mytts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void speak1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheather);
        city = findViewById(R.id.cityname);
        result=findViewById(R.id.result);
        initializeTextToSpeech();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Say City name");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak1();
                    }
                },4000);
            }
        }, 2000);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    city.setText(results.get(0));
                    search();
                    break;
            }

        }
    }
}