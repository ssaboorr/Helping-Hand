package com.example.apps.helpinghand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Calling extends AppCompatActivity {
    TextView t1;
    TextView t2;
    String s;
    String s1;
    String name;
    String num;
    String num1;
    String number;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        t2=(TextView)findViewById(R.id.Number);
        t1=(TextView)findViewById(R.id.Name);
        s=(getIntent().getExtras().getString("h"));
        s1= String.valueOf(s.charAt(0));
        t2.setText(s);
        boolean b=checkAlphabets(s1);
        if(b==true){
            fetchcontacts();
        }else{
            number=s;
            call();
        }}



    private boolean checkAlphabets(String s) {
        return (s!=null)&&(!s.equals("")&& (s.matches("^[a-zA-Z]*$")));
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
        if (name.equalsIgnoreCase(s)) {
            num1=num.replace("+91","");
            t1.setText(num1);
            number=num1;
            call();
        }

    }
    private void call() {
        Intent call=new Intent(Intent.ACTION_CALL);


        if (number.trim().isEmpty()){
            Toast.makeText(this, "enter valid number", Toast.LENGTH_SHORT).show();
        }else {
            call.setData(Uri.parse("tel:"+number));
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Please Grant Permission", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        else {
            startActivity(call);
        }
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(Calling.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
}
