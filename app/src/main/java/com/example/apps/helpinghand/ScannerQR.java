package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.MailTo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class ScannerQR extends AppCompatActivity {
  public static TextView resultext;
  public static TextView resultext1;
  Button btn_start;
  String data;
  int value=0;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scanner_qr);
    btn_start = (Button) findViewById(R.id.btn_scan);
    resultext = (TextView) findViewById(R.id.result_text);
    resultext1 = (TextView) findViewById(R.id.international);

    btn_start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), Scanner.class));
      }
    });
    data = resultext.getText().toString();


  }


}

