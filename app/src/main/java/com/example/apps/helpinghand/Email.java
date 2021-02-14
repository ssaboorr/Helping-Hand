package com.example.apps.helpinghand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Email extends AppCompatActivity {
    String s ;
    String s1 = " ";
    TextToSpeech mytts;
    TextView t1;
    TextView t2;
    TextView t3;
    String z1="Say the gmail whom to send";
    String z2="Say the subject";
    String z3="Say the message";
    ProgressBar progressBar = null;
    Context context = null;
    String to,sub,mssg,add,r;
    String mssg1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        t1 = findViewById(R.id.gt);
        t2 = findViewById(R.id.l1);
        t3 = findViewById(R.id.l2);
        context = this;

        initializeTTS();

        Handler h1=new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                calling1();
            }
        },3000);

    }

    private void calling1() {
        say(z1);
        Handler h1 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                to();

            }
        }, 3000);
    }
    private void calling2() {
        final Handler h2 = new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(z2);
                h2.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                subject();
                            }
                        }, 4000);
            }
        }, 5000);
    }
    private void calling3() {
        final Handler h2 = new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(z3);
                h2.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                message();
                            }
                        }, 4000);
            }
        }, 5000);
    }





    private void sendemail(String to, String sub, String mssg) {
        String email=to;
        String subject=sub;
        String message=mssg;
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
        javaMailAPI.execute();
    }


    private void initializeTTS() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Email.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("You are on email page");
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

    private void to() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }

    private void subject() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 3);
    }

    private void message() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 4);
    }
    private void check() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 2);
    }
    private void check1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 6);
    }
    private void append() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 5);
    }

    public void convertToSpell(String words) {
        add = "";
        String word = words.toUpperCase();
        for (int i = 0; i < word.length() - 10; i++) {
            add = add + word.charAt(i);
            add = add + s1;
        }
        add = add + "gmail.com";

        mytts.setSpeechRate((float) 0.7f);
        String p1 = null;
        if (add.contains("Z")) {
            p1 = add.replace("Z", "Zed");

            say(p1);
        }
        else {
            say(add);
        }
        final Handler d=new Handler();
        d.postDelayed(new Runnable() {
            @Override
            public void run() {

                say("Is a corrrect gmail to send say yes or no");
                d.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mytts.setSpeechRate((float) 1.0f);
                        check();

                    }
                },4000);
            }
        },6000);

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    s=result.get(0);
                    r=s.replace(" ","");
                    t1.setText(r);
                    convertToSpell(s);
                    break;
                case 2:
                    ArrayList<String> result1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result1.get(0).equalsIgnoreCase("yes")){
                        to=r;
                        calling2();
                    }
                    else{
                        say("SAY THE EMAIL AGAIN");
                        Handler h1=new Handler();
                        h1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                to();
                            }
                        },3000);
                    }break;
                case 3:
                    ArrayList<String> result2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    sub=result2.get(0);
                    t2.setText(sub);
                    calling3();
                    break;
                case 4:
                    ArrayList<String> result3 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mssg=result3.get(0);
                    t3.setText(mssg);
                    say("Do you want to add more message");
                    Handler h1=new Handler();
                    h1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            check1();
                        }
                    },3000);


                    break;
                case 5:

                    ArrayList<String> result4 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mssg1=result4.get(0);
                    mssg1=mssg+mssg1;
                    sendemail(to,sub,mssg);
                    break;
                case 6:
                    ArrayList<String> result5 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result5.get(0).equalsIgnoreCase("yes")){
                        append();
                    }
                    else
                    {
                        sendemail(to,sub,mssg);
                    }
            }
        }
    }
}
