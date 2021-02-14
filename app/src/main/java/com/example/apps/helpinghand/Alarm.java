package com.example.apps.helpinghand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Alarm extends AppCompatActivity {
    TextToSpeech mytts;
    TextView hours, minutes,repeats,ap;
     ArrayList<String> results,results2,results3,results4,results5;
     String hour,minute,repeat,apm,days;
     boolean apdays;
    final ArrayList<Integer> alarmdays=new ArrayList<>();
     Handler h=new Handler();
     String saydays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        hours = (TextView) findViewById(R.id.Name);
        minutes = (TextView) findViewById(R.id.Number);
        repeats= (TextView) findViewById(R.id.txtdays);
        ap = (TextView) findViewById(R.id.txtap);
        initializeTextToSpeech();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Say Hours");
                hours();
            }
        },2000);
    }

    private void minutes() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                speak1();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        say("Do you want me to repeat all days");
                        repeat();
                    }
                },3000);
            }
        },2000);
    }

    private void repeat() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                speak2();
            }
        },3000);
    }

    private void ap() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
               speak3();
            }
        },3000);
    }

    private void hours() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
               speak();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        say("Say minutes");
                        minutes();
                    }
                },3000);
            }
        },2000);
    }


    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Alarm.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("you are on Alarm page");
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
        startActivityForResult(intent, 2);
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }
    private void speak2() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 3);
    }
    private void speak3() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 4);
    }
    private void speak4() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    hour=results.get(0);
                    if (hour.equalsIgnoreCase("tu")){
                        hour="2";
                    }
                    if (hour.equalsIgnoreCase("zero")){
                        hour="0";
                    }
                    hours.setText(hour);
                    break;
                case 2:
                    results2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    minute=results2.get(0);
                    if (minute.equalsIgnoreCase("tu")){
                        minute="2";
                    }
                    if (minute.equalsIgnoreCase("zero")){
                        minute="0";
                    }
                    minutes.setText(minute);
                    break;
                case 3:
                    results3 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    repeat=results3.get(0);
                    if (repeat.contains("yes")||repeat.contains("ok")||repeat.contains("haa"))
                    {
                        repeats.setText("Set for All Days");
                        repeatdays();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                say("Say AM or PM");
                                ap();
                            }
                        },2000);
                    }
                    else {
                        say("Say the Particular days or day");
                        days();
                    }

                    break;
                case 4:
                    results4 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    apm=results4.get(0);
                    if (apm.equalsIgnoreCase("pm")){
                        apdays=true;
                    }
                    else {
                        apdays=false;
                    }
                    ap.setText(String.valueOf(apdays));
                    alarmset(hour,minute,apdays);

                    break;
                case 5:
                    results5 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    days=results5.get(0).trim();
                    if (days.contains("mon")||days.contains("Mon")){
                        alarmdays.add(Calendar.MONDAY);
                        saydays="Mondays";
                    }
                    if (days.contains("tue")||days.contains("Tue")){
                        alarmdays.add(Calendar.TUESDAY);
                        saydays=saydays+",Tuesday";
                    }
                    if (days.contains("wed")||days.contains("Wed")){
                        alarmdays.add(Calendar.WEDNESDAY);
                        saydays=saydays+",Wednesday";
                    }
                    if (days.contains("thurs")||days.contains("Thurs")){
                        alarmdays.add(Calendar.THURSDAY);
                        saydays=saydays+",Thursday";
                    }
                    if (days.contains("fri")||days.contains("Fri")){
                        alarmdays.add(Calendar.FRIDAY);
                        saydays=saydays+",Friday";
                    }
                    if (days.contains("sat")||days.contains("Sat")){
                        alarmdays.add(Calendar.SATURDAY);
                        saydays=saydays+",Saturday";
                    }
                    if (days.contains("sun")||days.contains("Sun")){
                        alarmdays.add(Calendar.SUNDAY);
                        saydays=saydays+",Sunday";
                    }
                    repeats.setText(saydays);

            }
        }
    }

    private void days() {
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
               speak4();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        say("Say am or pm");
                        ap();
                    }
                },4000);
            }
        },3000);
    }

    private void repeatdays() {
        alarmdays.add(Calendar.SUNDAY);
        alarmdays.add(Calendar.MONDAY);
        alarmdays.add(Calendar.TUESDAY);
        alarmdays.add(Calendar.WEDNESDAY);
        alarmdays.add(Calendar.THURSDAY);
        alarmdays.add(Calendar.FRIDAY);
        alarmdays.add(Calendar.SATURDAY);
        saydays="All Repeating days";
    }

    private void alarmset(String hour, String minute, boolean apdays) {
        int inthour=Integer.parseInt(hour);
        int intminute=Integer.parseInt(minute);
        switch (inthour)
        {
            case 13:
                inthour=1;
                break;
            case 14:
                inthour=2;
                break;
            case 15:
                inthour=3;
                break;
            case 16:
                inthour=4;
                break;
            case 17:
                inthour=5;
                break;
            case 18:
                inthour=6;
                break;
            case 19:
                inthour=7;
                break;
            case 20:
                inthour=8;
                break;
            case 21:
                inthour=9;
                break;
            case 22:
                inthour=10;
                break;
            case 23:
                inthour=11;
                break;
            case 24:
                inthour=12;
                break;

        }
        final boolean days= Boolean.parseBoolean(ap.getText().toString());
        final String ampm;
        final Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR,inthour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,intminute);
        intent.putExtra(AlarmClock.EXTRA_DAYS, alarmdays);
        intent.putExtra(AlarmClock.EXTRA_IS_PM,apdays);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE,"Wake up");
        say("You Alarm is set for"+hour+"Hour"+minute+"minutes ");
        if (String.valueOf(days).equalsIgnoreCase("fasle"))
        {
            ampm="AM";
        }
        else
        {
            ampm="PM";
        }
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(saydays+"and"+ampm);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                },4000);
            }
        },3000);

    }

    }
