package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.Objects;

public class Personal_Details extends AppCompatActivity {
    ImageButton sud;
    DatabaseReference reference;
    String rename,readdr,rephone,reage,rentn,reemail,uid,rebg,replace;
    ImageButton name,addr,phone,age,email,bg,place,nationality;
    TextToSpeech mytts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__details);
        sud=findViewById(R.id.savepd);
        registerForContextMenu(sud);
        name=(ImageButton)findViewById(R.id.name1);
        addr=(ImageButton)findViewById(R.id.addr1);
        phone=(ImageButton)findViewById(R.id.phone1);
        age=(ImageButton)findViewById(R.id.age1);
        email=(ImageButton)findViewById(R.id.email1);
        bg=(ImageButton)findViewById(R.id.bg1);
        place=(ImageButton)findViewById(R.id.place1);
        nationality=(ImageButton)findViewById(R.id.ntn1);
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        initializeTextToSpeech();
        retrivevalue();
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rename!=null){
                    say(rename);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
       addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readdr!=null){
                    say(readdr);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rephone!=null){
                    say(rephone);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reage!=null){
                    say(reage);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rentn!=null){
                    say(rentn);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (replace!=null){
                    say(replace);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rebg!=null){
                    say(rebg);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reemail!=null){
                    say(reemail);
                }
                else
                {
                    Toast.makeText(Personal_Details.this, "You have to fill the data first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose Your Option");
        getMenuInflater().inflate(R.menu.pd_menu,menu);


    }
    private void initializeTextToSpeech() {
        mytts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts.getEngines().size()==0)
                {
                    Toast.makeText(Personal_Details.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.savepd:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Personal_Details.this,Save_pd.class);
                startActivity(intent);
                return true;

            case R.id.update_del:
                Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(Personal_Details.this,Update_pd.class);
                startActivity(intent1);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    private void retrivevalue() {
        reference= FirebaseDatabase.getInstance().getReference().child("Mute_pd").child("save"+uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User Exists
                    readdr=dataSnapshot.child("uaddr").getValue().toString();
                    reage=dataSnapshot.child("uage").getValue().toString();
                    reemail=dataSnapshot.child("uemail").getValue().toString();
                    rename=dataSnapshot.child("uname").getValue().toString();
                    rentn=dataSnapshot.child("untn").getValue().toString();
                    rephone=dataSnapshot.child("uphone").getValue().toString();
                    rebg= (dataSnapshot.child("ubg").getValue()).toString();
                    replace=dataSnapshot.child("uplace").getValue().toString();
                }
                else {
                    Toast.makeText(Personal_Details.this, "Insert Your Information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
