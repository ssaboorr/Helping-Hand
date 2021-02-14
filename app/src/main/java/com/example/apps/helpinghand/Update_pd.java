package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update_pd extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    TextView tdata;
    String uid,data;
    DatabaseReference reff;
    EditText update;
    String s;
    ImageButton deletebtn,updatebtn;
    String rename,readdr,rephone,reage,rentn,reemail,rebg,replace;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pd);
        spinner=(Spinner)findViewById(R.id.spinner);
        tdata=(TextView)findViewById(R.id.pddelete);
        update=(EditText)findViewById(R.id.updatetext);
        deletebtn=(ImageButton)findViewById(R.id.deleteud);
        updatebtn=(ImageButton)findViewById(R.id.updateud);


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.data, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);


        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete();
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            data = adapterView.getItemAtPosition(i).toString();
            tdata.setText(data);
            if (data.equalsIgnoreCase("name")) {
                data = "uname";
            }
            if (data.equalsIgnoreCase("phone")) {
                data = "uphone";
            }
            if (data.equalsIgnoreCase("email")) {
                data = "uemail";
            }
            if (data.equalsIgnoreCase("age")) {
                data = "uage";
            }
            if (data.equalsIgnoreCase("Address")) {
                data = "uaddr";
            }
            if (data.equalsIgnoreCase("nationality")) {
                data = "untn";
            }
            if (data.equalsIgnoreCase("blood group")) {
                data = "ubg";
            }
            if (data.equalsIgnoreCase("place")) {
                data = "uplace";
            }
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select the data", Toast.LENGTH_SHORT).show();

    }
    private void Delete() {
        retrivevalue();
        if (readdr == null) {
            count = count++;
        }
        if (reage == null) {
            count = count++;
        }
        if (reemail == null) {
            count = count++;
        }
        if (rebg == null) {
            count = count++;
        }
        if (rename == null) {
            count = count++;
        }
        if (rentn == null) {
            count = count++;
        }
        if (replace == null) {
            count = count++;
        }
        if (rephone == null) {
            count = count++;
        }
        if (count < 3) {
            Toast.makeText(this, "You cannot delete more than 2 information", Toast.LENGTH_SHORT).show();
        } else {
            reff = FirebaseDatabase.getInstance().getReference().child("Mute_pd").child("save" + uid);
            reff.child(data).setValue("");
        }
    }
    private void Update(){
        s=update.getText().toString();
        reff=FirebaseDatabase.getInstance().getReference().child("Mute_pd").child("save"+uid);
        reff.child(data).setValue(s);
    }
    private void retrivevalue() {
        reff= FirebaseDatabase.getInstance().getReference().child("Mute_pd").child("save"+uid);
        reff.addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(Update_pd.this, "Error while retriving", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
