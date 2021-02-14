package com.example.apps.helpinghand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Whatsapp extends AppCompatActivity {
    TextToSpeech mytts;
    TextView t1;
    String setname;
    String n ;
    String s = "Say zero For whatsapp video Call";
    String s1 ="Say one For whatsapp voice call";
    String r;
    ArrayList<String> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);
        t1 = findViewById(R.id.vt2);

        initializeTTS();
        final Handler h1 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("i'll give you the option what are the things to be peform");
                h1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        say(s);
                        h1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                say(s1);
                                h1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        option();
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }, 3000);
    }
    private void whstaspp_video_call(String name1) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        //here you have to pass whatsApp contact  number  as  number..
        String name2=name1;
        String name =  fetchcontacts(name2);
        int videocall = getContactIdForWhatsAppVideoCall(name, Whatsapp.this);
        t1.setText(String.valueOf(videocall));
        if (videocall != 0) {
            intent.setDataAndType(Uri.parse("content://com.android.contacts/data/" + videocall),
                    "vnd.android.cursor.item/vnd.com.whatsapp.video.call");
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        }
        else {
            say("This Number Does not Has Whatsapp");
        }
    }

    private void whatsapp_voice_call(String name1) {
        String mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call";


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        //here you have to pass whatsApp contact  number  as  contact_number ..
        String name2=name1;
        String name = fetchcontacts(name2);

        int whatsappcall = getContactIdForWhatsAppCall(name, Whatsapp.this);
        if (whatsappcall != 0) {
            intent.setDataAndType(Uri.parse("content://com.android.contacts/data/" + whatsappcall),
                    "vnd.android.cursor.item/vnd.com.whatsapp.voip.call");
            intent.setPackage("com.whatsapp");
            startActivity(intent);

        }
        else
        {
            say("This Number Does not Has Whatsapp");
        }
    }


    private int getContactIdForWhatsAppCall(String name, Whatsapp whatsapp) {

        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data._ID},
                ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.voip.call"},
                ContactsContract.Contacts.DISPLAY_NAME);

        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            System.out.println("9999999999999999          name  " + name + "      id    " + phoneContactID);
            return phoneContactID;
        } else {
            System.out.println("8888888888888888888          ");
            return 0;
        }
    }

    private int getContactIdForWhatsAppVideoCall(String name, Whatsapp whatsapp) {
        Cursor cursor = getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Data._ID},
                ContactsContract.Data.DISPLAY_NAME + "=? and " + ContactsContract.Data.MIMETYPE + "=?",
                new String[]{name, "vnd.android.cursor.item/vnd.com.whatsapp.video.call"},
                ContactsContract.Contacts.DISPLAY_NAME);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data._ID));
            return phoneContactID;
        } else {
            System.out.println("8888888888888888888          ");
            return 0;
        }
    }

    private void initializeTTS() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Whatsapp.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                    ;
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("You are on a whatsapp");
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

    private void option() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }
    private void selectname() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 2);
    }


    private String fetchcontacts(String name2) {
        n=name2;
        ArrayList<String> contacts = new ArrayList<>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String n1 = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            if (n1.equalsIgnoreCase(n)) {
                setname = n1;

            }

        }
        return setname;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    say("Say the name");

                    Handler h2= new Handler();
                    h2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            selectname();
                        }
                    },2000);
                    break;
                case 2:
                    ArrayList<String> result1= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    r=result1.get(0);
                    if (result.get(0).equalsIgnoreCase("1")) {
                        whatsapp_voice_call(r);
                    } else {
                        whstaspp_video_call(r);
                    }
            }

        }
    }
}
