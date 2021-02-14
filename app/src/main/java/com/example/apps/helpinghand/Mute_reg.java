package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Mute_reg extends AppCompatActivity {
    EditText name;
    EditText password;
    EditText email;
    Button b1;
    DatabaseReference databaseReference;
    DatabaseReference firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mute_reg);
        name = (EditText) findViewById(R.id.ename);
        password = (EditText) findViewById(R.id.epassword);
        email = (EditText) findViewById(R.id.eemail);
        b1 = (Button) findViewById(R.id.ebtn2);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("mutetable");
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Info");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uemail = email.getText().toString().trim();
                String upass = password.getText().toString().trim();
                String uname=name.getText().toString();
                if (uemail.isEmpty() || upass.isEmpty()|| uname.isEmpty())
                {
                    validate(uemail,upass,uname);
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(uemail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String uemail = email.getText().toString().trim();
                            String upass = password.getText().toString().trim();
                            String uname=name.getText().toString();
                            if (task.isSuccessful()) {
                                Toast.makeText(Mute_reg.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                                databasesave(uemail, upass, uname);
                            } else {
                                Toast.makeText(Mute_reg.this, "Unsucessfully", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
        });

    }

    private void validate(String uemail, String upass, String uname) {
        if (uemail.isEmpty())
        {
            email.setError("email cannot be empty");
        }
        if (upass.isEmpty())
        {
            password.setError("password cannot be empty");
        }
        if (uname.isEmpty())
        {
            name.setError("name cannot be empty");
        }
    }


    private void databasesave(String uemail, String upass, String uname) {
        String id=databaseReference.push().getKey();
        String statuss="no";
        SavemuteReg savemuteReg=new SavemuteReg(uname,statuss,uemail,upass) ;
        databaseReference.child("Users").child(id).setValue(savemuteReg);
        Toast.makeText(this, "Sucessfully", Toast.LENGTH_SHORT).show();
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Mute_reg.this,mute_log.class));
            }
        },2000);
    }
}