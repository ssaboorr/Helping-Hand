package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mute_log extends AppCompatActivity {
    EditText rname;
    EditText rpass;
    Button rbtn;
    DatabaseReference firebaseDatabase;
    FirebaseAuth firebaseAuth;
    ImageButton rimg;
    TextView newuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mute_log);
        rname=(EditText)findViewById(R.id.remail);
        rpass=(EditText)findViewById(R.id.rpass);
        rbtn=(Button)findViewById(R.id.rbtn);
        rimg=(ImageButton)findViewById(R.id.btRegister);
        newuser=(TextView)findViewById(R.id.newuser);
        rbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             callfirebase();
            }
        }));
        firebaseAuth=FirebaseAuth.getInstance();
        rimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mute_log.this,Mute_reg.class);
                startActivity(intent);
            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mute_log.this,Mute_reg.class));
            }
        });
    }

    private void callfirebase() {
        String uemail=rname.getText().toString().trim();
        String  upass=rpass.getText().toString().trim();
        if (uemail.isEmpty() || upass.isEmpty()) {
            validate(uemail,upass);
        }else {
            firebaseAuth.signInWithEmailAndPassword(uemail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent= new Intent(mute_log.this,Mute_page.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }


    }

    private void validate(String uemail, String upass) {
        if (uemail.isEmpty())
        {
            rname.setError("email cannot be empty");
        }
         if (upass.isEmpty())
        {
            rpass.setError("password cannot be empty");
        }
    }
}
