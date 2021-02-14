package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Locale;

public class Blind_log extends AppCompatActivity {

    TextView mtext;
    TextView mtext1;
    TextToSpeech mytts;
    String s="Your email";
    String s1="Your password";
    String n="Are You A New User";
    String data="Please Set The Mobile data On or Wifi";
    private int count=1;
    ArrayList<String> results;
    ArrayList<String> results2;
    ArrayList<String> results3;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_log);
        mtext=findViewById(R.id.buname);
        mtext1=findViewById(R.id.bpassword);
        initializeTextToSpeech();
        Handler h1=new Handler();
        firebaseAuth=FirebaseAuth.getInstance();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(data);
            }
        },3000);
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                call3();
            }
        },1000);

    }

    private void call3() {
        final Handler h2= new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {

                say(n);
                h2.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                speak3();
                                call2();
                            }
                        },4000);
            }
        },5000);

    }



    private void call2() {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                set();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                        call1();
                    }
                },2000);
            }
        },6000);

    }

    private void set() {
        if(count==2){
            finish();
        }else {
            say(s);
        }
    }

    private void call1() {
        final Handler h1=new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                set1();
                h1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak1();
                    }
                },2000)  ;
            }
        },6000);
    }

    private void set1() {
        if (count==2){
            finish();
        }else{
            say(s1);
        }
    }


    private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0)
                {
                    Toast.makeText(Blind_log.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    mytts.setLanguage(Locale.ENGLISH);
                    say("you are on login page");
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
    private void speak1(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,2);
    }
    private void speak(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,1);
    }
    private void speak3() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null){
            switch (requestCode){
                case 1:
                    results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mtext.setText(results.get(0).replace(" ",""));
                    break;
                case 2:
                    results2=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mtext1.setText(results2.get(0));
                    String s3=results.get(0).toString();
                    String s4=results2.get(0).toString();
                    check(s3,s4);
                    break;
                case 3:
                    results3=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String s5=results3.get(0).toString();
                    String s6=s5.toLowerCase();
                    move(s6);
                    break;
            }

        }
    }

    private void move(String s6) {
        if (s6.equals("new user")|| s6.equals("yes")|| s6.equals("i am")){
            Intent intent= new Intent(Blind_log.this,Blind_reg.class);
            startActivity(intent);
            count=count+1;
            finish();

        }


    }



    private void check(String s3, String s4) {
        String s5=s3.toLowerCase();
        String s6=s4.toLowerCase();
        final String uname=s5.replace(" ","");
        final String upass=s6.replace(" ","");
        mtext.setText(uname);
        mtext1.setText(upass);
        final int[] count = {0};

        firebaseAuth.signInWithEmailAndPassword(uname,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
                if (uname.isEmpty() ||upass.isEmpty())
                {
                    validate(uname,upass);
                }
                if(task.isSuccessful()){
                    say("Login Successfully");
                    Handler h1=new Handler();
                    h1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent= new Intent(Blind_log.this,Blind_Page.class);
                            startActivity(intent);
                            finish();
                        }
                    },2000);
               }
                else{
                    Toast.makeText(Blind_log.this, "Unsucessfully", Toast.LENGTH_SHORT).show();
                    say("invalid try again");
                    count[0] = count[0] +1;
                    if (count[0] ==2){
                        say("Many No of atttempts try Again");
                        Handler h3=new Handler();
                        h3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },2000);
                    }
                    call2();

                }
            }
        });

}

    private void validate(String uname, String upass) {
        if (uname.isEmpty())
        {
           say("email can not be empty");
        }
        if (upass.isEmpty())
        {
            say("password cannot be empty");
        }
    }

}
