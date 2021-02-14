package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.google.firebase.storage.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewImageActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    Button pr;
    private FirebaseStorage mstorage;
    private ProgressBar progressBar;
    TextToSpeech mytts;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private ValueEventListener mDBListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar=(ProgressBar)findViewById(R.id.progress_circular);

        mUploads=new ArrayList<>();
        mstorage=FirebaseStorage.getInstance();
        FirebaseAuth firebaseAuth;
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads"+uid);
        mDBListener=mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Upload upload=postSnapshot.getValue(Upload.class);
                    upload.setkey(postSnapshot.getKey());
                    mUploads.add(upload);
                }
                mAdapter=new ImageAdapter(ViewImageActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(ViewImageActivity.this);
                progressBar.setVisibility(View.INVISIBLE);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewImageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        initializeTextToSpeech();

    }
    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(ViewImageActivity.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
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
    public void onItemclick(int position) {
        Toast.makeText(this, "clicked at position"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSpeak(int position) {
            Upload selecteditem=mUploads.get(position);
            String s=selecteditem.getImgName();
            say(s);
    }

    @Override
    public void onDeleteClick(int position) {
        Upload item=mUploads.get(position);
        final String selectedkey=item.getkey();
        StorageReference imageref=mstorage.getReferenceFromUrl(item.getImgUrl());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedkey).removeValue();
                Toast.makeText(ViewImageActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}


