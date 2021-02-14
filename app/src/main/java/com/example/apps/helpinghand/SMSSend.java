package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SMSSend extends AppCompatActivity {
    TextView phone,mssg;
    TextToSpeech mytts;
    ArrayList<String> results,resultnumber,resultmessage,append,mdata;
     String number,mssgdataa,message,mesage1,name,num,num1,number1,numeric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smssend);
        phone=(TextView)findViewById(R.id.Name);
        mssg=(TextView)findViewById(R.id.Number);
        initializeTextToSpeech();
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                numberdata();
            }
        },2000);

    }
    private void numberdata(){
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Say Your Phone number or name");
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                    }
                },3000);
            }
        },4000);
    }

    private void mssg() {
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Say Your Message ");
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak2();
                    }
                },3000);
            }
        },4000);

    }

    private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0)
                {
                    Toast.makeText(SMSSend.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    mytts.setLanguage(Locale.ENGLISH);
                    say("You are on Sms page");
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
    private void speak1() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,2);
    }
    private void speak2() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,3);
    }
    private void speak3() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,4);
    }
    private void speak4() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,5);
    }
    private boolean checkAlphabets(String s) {
        return (s!=null)&&(!s.equals("")&& (s.matches("^[a-zA-Z]*$")));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null){
            switch (requestCode) {
                case 1:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    number = results.get(0);
                    boolean b=checkAlphabets(number);
                    if (b==true){
                        fetchcontacts();
                        say("The text will be send to "+numeric);
                        final Handler h=new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                say("Im Saying You Phone number is it correct say yes or no");
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        datacheck(number1);
                                    }
                                },4000);
                            }

                        },3000);

                        phone.setText(number1);

                    }
                    else {
                    say("Im Saying You Phone number is it correct say yes or no");
                    phone.setText(number+"h");
                    datacheck(number);}
                    break;
                case 2:
                    resultnumber=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String no = resultnumber.get(0);
                    if (no.contains("yes")|| no.contains("ok")){
                        mssg();
                    }else {
                        numberdata();
                    }
                    break;
                case 3:
                    resultmessage=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                     message = resultmessage.get(0);
                     mssg.setText(message);
                    say("Do you want to append the message more");
                    messagecheck();
                    break;
                case 4:
                    append=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mssgdataa=append.get(0);
                    if (mssgdataa.contains("yes")|| mssgdataa.contains("ok")){
                        Handler h=new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                speak4();
                            }
                        },2000);

                    }
                    else {
                            Sendsms();
                    }
                    break;
                case 5:
                    mdata=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mesage1=mdata.get(0);
                    mesage1=message+mesage1;
                    mssg.setText(mesage1);
                    Sendsms();
                    break;

            }

        }
    }

    private void Sendsms() {
        int permissioncheck= ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permissioncheck== PackageManager.PERMISSION_GRANTED){
            Mysms();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }

    }

    private void Mysms() {
        String txtphone = phone.getText().toString().trim();
        String txtmessage = mssg.getText().toString();
        mssg.setText(txtphone);
        phone.setText(txtmessage);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(txtphone, null, txtmessage, null, null);
            Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
            say("Message Sent Successfully");
        }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Mysms();
                }
                else {
                  say("You don't have a required permission");
                }

                break;
        }
    }

    private void messagecheck() {
        final Handler h2 = new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                speak3();

            }
        }, 4000);

    }


    private void datacheck(final String number) {
        final Handler h2 = new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(number);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       speak1();

                    }
                }, 5000);
            }
        }, 5000);

    }

    private void fetchcontacts() {
        ArrayList<String> contacts= new ArrayList<>();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(uri,projection,null,null,null);
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i("@huzzi", "Name-" + name + "Num-" + num);
            contacts.add(name + "\n" + num);
            check();
        }


    }

    private void check() {
        if (name.equalsIgnoreCase(number)) {
            num1 = num.replace("+91", "");
            numeric=name;
            number1 = num1;

        }


    }
}