package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Save_pd extends AppCompatActivity {
    EditText name,age,address,phone,ntn,email,bg,place;
    DatabaseReference databaseReference;
    String uid;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pd);
        name=(EditText)findViewById(R.id.pdname);
        address=(EditText)findViewById(R.id.pdaddress);
        phone=(EditText)findViewById(R.id.pdphone);
        age=(EditText)findViewById(R.id.pdage);
        ntn=(EditText)findViewById(R.id.pdnationality);
       email=(EditText)findViewById(R.id.pdemail);
       bg=(EditText)findViewById(R.id.pdbg);
       place=(EditText)findViewById(R.id.pdplace);
       b1=findViewById(R.id.pdbtn);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
       databaseReference= FirebaseDatabase.getInstance().getReference("Mute_pd");
       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addvalue();
               Toast.makeText(Save_pd.this, "Submitted Sucessfully", Toast.LENGTH_SHORT).show();
           }
       });


    }



    public  void addvalue(){
        Savetechinfo save=new Savetechinfo(name.getText().toString(),phone.getText().toString()
                ,email.getText().toString(),age.getText().toString(),ntn.getText().toString(),address.getText().toString(),
                bg.getText().toString(),place.getText().toString());
        databaseReference.child("save"+uid).setValue(save);

    }

}
