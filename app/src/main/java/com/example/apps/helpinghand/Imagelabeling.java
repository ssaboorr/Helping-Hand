package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class Imagelabeling extends AppCompatActivity {
    CameraView cameraView;
    AlertDialog alertDialog;
    TextView display;
    Button btncapture;
    ImageView imageView;

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
        setContentView(R.layout.activity_imagelabeling);
        cameraView = (CameraView) findViewById(R.id.camera_view);
        display=(TextView)findViewById(R.id.display1);

        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setMessage("Please Wait")
                .setContext(this)
                .build();

        btncapture = (Button) findViewById(R.id.btn_capture);
        btncapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.start();
                cameraView.captureImage();

            }
        });
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
                bitmap = Bitmap.createScaledBitmap(bitmap, 400,400, false);
                bitmap.setHeight(400);
                bitmap.setWidth(400);

                cameraView.stop();
                imagelabeling(bitmap);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void imagelabeling(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionOnDeviceImageLabelerOptions options =
         new FirebaseVisionOnDeviceImageLabelerOptions.Builder()
       .setConfidenceThreshold(0.7f)
       .build();
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
     .getOnDeviceImageLabeler(options);
        labeler.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            firebaseimage(labels);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Imagelabeling.this, "No image found", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });

    }

    private void firebaseimage(List<FirebaseVisionImageLabel> labels) {
        for (FirebaseVisionImageLabel label: labels) {
            String text = label.getText();
            String entityId = label.getEntityId();
            float confidence = label.getConfidence();
            display.setText("Text:"+text+"\n"+entityId+"\n"+confidence);
        }
            alertDialog.dismiss();

    }
}
