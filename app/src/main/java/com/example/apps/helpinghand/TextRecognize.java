package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionText.Element;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class TextRecognize extends AppCompatActivity {
    CameraView cameraView;
    AlertDialog alertDialog;
    TextView display;

    ArrayList<String> results;
    TextToSpeech mytts;
    final Handler h=new Handler();
    Bitmap bitmap;
    int i;
    ArrayList<String> results2;
    RelativeLayout relativeLayout;
    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraView.start();
            }
        }, 300);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognize);
        cameraView = (CameraView) findViewById(R.id.camera_view);
        display = (TextView) findViewById(R.id.display1);
        initializeTextToSpeech();
        relativeLayout=findViewById(R.id.relativetext);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("By Which you want to recognize text Say one for camera and Two for image");
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak1();
                    }
                },5000);
            }
        },3000);
        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setMessage("Please Wait")
                .setContext(this)
                .build();


        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                alertDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
                bitmap.setHeight(400);
                bitmap.setWidth(400);

                cameraView.stop();
                recognizeText(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(TextRecognize.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("you are on Text Recognize page");

                }
            }
        });
    }

    private void say(String message) {
        if (Build.VERSION.SDK_INT >= 21) {
            mytts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mytts.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void speak1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }
    private void speak2() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 3);
    }

    private void recognizeText(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();

        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        drawTextResult(firebaseVisionText);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", e.getMessage());
            }
        });

    }

    private void drawTextResult(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(this, "No Text Found", Toast.LENGTH_SHORT).show();
            return;
        } else {
            for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                String text1 = block.getText();
                display.setText(text1);
                say(text1);
            }
            alertDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (results.get(0).equalsIgnoreCase("one")||results.get(0).equalsIgnoreCase("1"))
                    {
                        say("you image will be capture in 7 sec automatically");
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                cameraView.start();
                                cameraView.captureImage();
                            }
                        },7000);

                    }
                    else if (results.get(0).equalsIgnoreCase("tu")||results2.get(0).equalsIgnoreCase("2"))
                    {
                        say("Pick the image by taking someone help");
                        pickup();

                    }
                    break;
                case 2:
                    Uri uri=data.getData();
                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                       relativeLayout.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               recognizeText(bitmap);
                           }
                       });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    private void pickup() {
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,2);
    }
}