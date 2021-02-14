package com.example.apps.helpinghand;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScreenReceiver extends BroadcastReceiver {

    String uid;
    public static boolean wasScreenOn = true;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    public void onReceive(final Context context, final Intent intent) {
        Log.e("LOB", "onReceive");
        firebaseAuth = FirebaseAuth.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            wasScreenOn = false;
            Log.e("LOB","wasScreenOn"+wasScreenOn);
            Log.e("Screen ", "shutdown now");
           /* Intent i = new Intent(context, MainActivity.class);  //MyActivity can be anything which you want to start on bootup...
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);*/

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // and do whatever you need to do here
            wasScreenOn = true;

            Log.e("Screen ", "awaked now");
            if (firebaseAuth.getCurrentUser()!=null) {
                retrive(context);
            } else {
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }

        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.e("LOB", "userpresent");
            //  Log.e("LOB","wasScreenOn"+wasScreenOn);


        }

    }

    public void retrive(final Context context) {
        final String email = firebaseAuth.getCurrentUser().getEmail();
        reference = FirebaseDatabase.getInstance().getReference().child("User_Info").child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String uemail = dataSnapshot1.child("uemail").getValue().toString();
                        if (email.equalsIgnoreCase(uemail)) {
                            String ustatus = dataSnapshot1.child("status").getValue().toString();
                            if (ustatus.equalsIgnoreCase("yes")) {

                                Intent i = new Intent(context, Blind_Page.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}