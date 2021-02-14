package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;

public class Blind_Page extends AppCompatActivity   {
    TextView mtext;Button mbtn;
    private TextToSpeech mytts;
    String s;
    String ret;
    Intent maps;
    IntentFilter intentFilter=null;
    int count=1;
    RequestQueue requestQueue;
    RequestQueue mrequestqueue;
    RelativeLayout function2;
    ScrollView scrollView;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind__page);
        mtext = findViewById(R.id.bptext);
       scrollView=findViewById(R.id.sv);
        function2=findViewById(R.id.data);
        requestQueue= Volley.newRequestQueue(this);
        mrequestqueue=Volley.newRequestQueue(this);
        mAuth=FirebaseAuth.getInstance();
        initializeTTS();
        scrollView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mytts.setPitch(1.0f);
               speak();
           }
       });
        function2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mytts.setPitch(1.0f);
                speak();
            }
        });


         BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intentFilter=new IntentFilter(Intent.ACTION_BATTERY_LOW);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean ischarging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
                int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
                float batper=(level/(float)scale)*100;
                if (batper<20) {
                        if (ischarging){
                            if(count<=3)
                            say("Charging is connected");
                            count++;
                        }
                        else {
                            say("low Battery Please Charge");
                        }
                }

            }
        };
         registerReceiver(broadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (mAuth.getCurrentUser()!=null) {
            retrive();
        }

    }

    private void retrive() {
        final String email = mAuth.getCurrentUser().getEmail();
        reference = FirebaseDatabase.getInstance().getReference().child("User_Info").child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String uemail = dataSnapshot1.child("uemail").getValue().toString();
                        if (email.equalsIgnoreCase(uemail)) {
                            String uname= dataSnapshot1.child("uname").getValue().toString();
                            say("Hello"+uname);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void initializeTTS() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Blind_Page.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                    ;
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("Hello i'm here to help You");
                }
            }
        });
    }

    private void say(String s) {
        if (Build.VERSION.SDK_INT >= 21) {
            mytts.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mytts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }
    private void speak1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null) {
            switch (requestCode) {
                case 1:
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mtext.setText(result.get(0));
                    processResult(result.get(0));
                    break;
                case 2:
                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String jokelang=results.get(0);
                    if (jokelang.contains("hindi")|| jokelang.contains("Hindi")){
                            jsonParsehindi();
                    }
                    else {
                        jsonParse();
                    }
                    break;
            }
        }

    }

    @SuppressLint("LongLogTag")
    private void processResult(String s) {
        s = s.toLowerCase();
        if (s.indexOf("what") != -1) {
            if (s.indexOf("your name") != -1 || s.indexOf("name") != -1) {
                say("My name is Jenix");
            }
        }
        if (s.indexOf("what") != -1) {
            if (s.indexOf("the time") != -1 || s.indexOf("time is now") != -1 || s.indexOf("is the tiime") != -1) {
                Date d = new Date();
                String time = DateUtils.formatDateTime(this, d.getTime(), DateUtils.FORMAT_SHOW_TIME);
                say("The time is" + time);
            }
        }

        if (s.indexOf("call") != -1) {
            String no = s.replace("call", "");
            say("Calling" + no);
            ret = s.substring(5);
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Blind_Page.this, Calling.class);
                    i.putExtra("h", ret);
                    startActivity(i);
                }
            }, 3000);
        }
        if (s.indexOf("maps") != -1 && s.indexOf("map") != -1) {
            maps = new Intent();
            Intent i = new Intent(this, Maps_Activity.class);
            String s4 = "Redirecting to u On Location page to turn on Location please take a help  from someone or press right top corner";
            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!enabled) {
                say(s4);
                maps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                final Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(maps);
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                say("Say the map feature again to use on main page");
                            }
                        }, 3000);


                    }
                }, 7000);
            } else {
                startActivity(i);
            }

        }


        if (s.indexOf("whatsapp") != -1) {
            Intent i = new Intent(Blind_Page.this, Whatsapp.class);
            startActivity(i);
        }
        if (s.indexOf("email") != -1) {
            Intent i = new Intent(Blind_Page.this, Email.class);
            startActivity(i);
        }
        if (s.indexOf("weather") != -1) {
            Intent i = new Intent(Blind_Page.this, Wheather.class);
            startActivity(i);
        }
        if (s.indexOf("joke")!=-1){
            say("Which joke Do you like to Listen: English or Hindi");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speak1();
                }
            },5000);
        }
        if (s.indexOf("fact")!=-1){
            say("Let me find for u. ");
            Handler h=new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    jsonparsefacts();
                }
            },1000);
        }
        if (s.indexOf("logout")!=-1||s.equalsIgnoreCase("logout"))
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),Blind_log.class);
            startActivity(intent);
        }
        if (s.indexOf("object detection")!=-1)
        {
            Intent intent=new Intent(getApplicationContext(),ObjectDetection.class);
            startActivity(intent);
        }
        if (s.indexOf("text recognise")!=-1)
        {
            Intent intent=new Intent(getApplicationContext(),TextRecognize.class);
            startActivity(intent);
        }
    }


    private void jsonparsefacts() {
        String url="https://api.myjson.com/bins/q765a";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Random r=new Random();
                            JSONArray  jsonArray=response.getJSONArray("facts");
                            int random=r.nextInt(jsonArray.length()+1);
                            JSONObject jokes=jsonArray.getJSONObject(random);
                            String value=jokes.getString("value");
                            mtext.setText(value);
                            mytts.setPitch(0.6f);
                            mytts.setLanguage(Locale.ENGLISH);
                            say(value);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }


    private void jsonParse() {
        String url="https://api.myjson.com/bins/1h7u9u";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Random r=new Random();
                            JSONArray  jsonArray=response.getJSONArray("jokes");
                            int random=r.nextInt(jsonArray.length()+1);
                            JSONObject jokes=jsonArray.getJSONObject(random);
                            String value=jokes.getString("value");
                            String[] data=value.split("\n");
                            String data1=data[0];
                            final String dat2=data[1];
                            mtext.setText(value);
                            mytts.setPitch(0.6f);
                            mytts.setLanguage(Locale.ENGLISH);
                            say(data1+"..?"+dat2);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
    private void jsonParsehindi() {
        String url="https://api.myjson.com/bins/zeg2m";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Random r=new Random();
                            JSONArray  jsonArray=response.getJSONArray("jokes");
                            int random=r.nextInt(jsonArray.length()+1);
                            JSONObject jokes=jsonArray.getJSONObject(random);
                            String value=jokes.getString("value");
                            mtext.setText(value);
                            mytts.setPitch(0.6f);
                            mytts.setLanguage(Locale.ENGLISH);
                            say(value);

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}
