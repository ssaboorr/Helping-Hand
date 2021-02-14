package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationManager;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Maps_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView t1;
    TextView t2;
    TextView t3;
    Geocoder geocoder;
    List<Address> addresses;
    Intent i;
    Location location;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    TextToSpeech mytts;
    String s1 = "Say the place where you want to navigate ";
    String s2 = "Say 1 for going to particular location";
    String s3 = "Say zero for getting your current location";
    String s4 = "Redirecting to u On Location page to turn on Location please take a help  from someone or press right top corner";
    private static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_);
        t1 = findViewById(R.id.gt);
        t2 = findViewById(R.id.l1);
        t3 = findViewById(R.id.l2);
        initializeTTS();
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    say(s4);
                }
            }, 3000);

            i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(i);
                    onResume();
                    optionfn();

                }
            }, 9000);

        } else {
            optionfn();
        }
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void optionfn() {
        Handler h1 = new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                choice();
            }
        }, 4000);
    }


    private void choice() {
        final Handler h2 = new Handler();
        say(s3);
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(s2);
                h2.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                location_taken();
                            }
                        }, 4000);
            }
        }, 4000);

    }

    private void place() {
        final Handler h2 = new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(s1);
                h2.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                speak();
                            }
                        }, 4000);
            }
        }, 4000);

    }


    private void initializeTTS() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Maps_Activity.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                    ;
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("You are on google map page");
                }
            }
        });
    }

    private void say(String s) {
        if (Build.VERSION.SDK_INT >= 21) {
            mytts.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mytts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }

    private void location_taken() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 2);
    }
    private void location_options() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 3);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    t1.setText(result.get(0));
                    say("Say the option by which are you travelling that is driving or walking or bicycling");
                    Handler h3 = new Handler();
                    h3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            location_options();
                        }
                    }, 6000);
                    break;
                case 2:
                    ArrayList<String> result1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result1.get(0).equalsIgnoreCase("1")) {
                        place();
                    } else if (result1.get(0).equalsIgnoreCase("zero")) {
                        Locationgetter();
                    } else {
                        say("Said the wrong number Taking you to Main page");
                        Intent i=new Intent(this,MainActivity.class);
                        startActivity(i);
                        break;
                    }
                case 3:
                    ArrayList<String> result2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String getdata=result2.get(0);
                    if (getdata.contains("driving")||getdata.contains("drive"))
                    {
                        googlemaps();
                    }
                    else if (getdata.contains("walking")||getdata.contains("walk"))
                    {
                        googlemaps1("w");
                    }
                    else if (getdata.contains("bicycling")|| getdata.contains("bicycle"))
                    {
                        googlemaps1("b");
                    }
            }
        }

    }

    private void Locationgetter() {
        Float f=Float.valueOf(t2.getText().toString());
        Float f1=Float.valueOf(t3.getText().toString());
        geocoder=new Geocoder(this,Locale.getDefault());
        try {
            addresses=geocoder.getFromLocation(f,f1,1);
            String address=addresses.get(0).getAddressLine(0);
            String area=addresses.get(0).getLocality();
            String city=addresses.get(0).getAdminArea();
            String country=addresses.get(0).getCountryName();
            String postalcode=addresses.get(0).getPostalCode();
            String Fulladdress=address+","+area+","+city+","+country+","+postalcode;
            t1.setText(address);
            say(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {

        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }

        super.onStop();
    }

    private void googlemaps() {

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + t1.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
    private void googlemaps1(String data) {

        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + t1.getText().toString()+"&mode="+data);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        } else {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                t2.setText(String.valueOf(location.getLatitude()));
                                t3.setText(String.valueOf(location.getLongitude()));

                            }
                        }

                    });
        }
    }



    private void requestPermissions() {

        ActivityCompat.requestPermissions(Maps_Activity.this,new
                String[]{ACCESS_FINE_LOCATION},RequestPermissionCode);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("Main2Activity","Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Main2Activity","Connection failed"+connectionResult.getErrorCode());
    }
    }

