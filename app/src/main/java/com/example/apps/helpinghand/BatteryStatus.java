package com.example.apps.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class BatteryStatus extends AppCompatActivity {
        TextView batstats,batplug,bathealth,batvoltb,battemp,battech,batlevel;
        IntentFilter intentFilter;
        TextToSpeech mytts;
        int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_status);
        batstats=findViewById(R.id.txtStatus);
        batplug=findViewById(R.id.txtplug);
        bathealth=findViewById(R.id.txthealth);
        batvoltb=findViewById(R.id.txtvoltage);
        battemp=findViewById(R.id.txttemp);
        battech=findViewById(R.id.txttech);
        batlevel=findViewById(R.id.txtlevel);
        initializeTTS();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Saying Battery Information");
            }
        },2000);
        intentFilter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        final BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, final Intent intent) {
                status(intent);
               plugged(intent);
                level(intent);
                bathealth(intent);
               voltage(intent);
               temp(intent);
               tech(intent);
                tech(intent);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(count==1){
                            speakevent();
                            count++;
                        }
                    }
                },2000);

                }



            };
        BatteryStatus.this.registerReceiver(broadcastReceiver,intentFilter);

        }

    private void speakevent() {
        final String health=bathealth.getText().toString().trim();
        final String plugged=batplug.getText().toString().trim();
        final String level=batlevel.getText().toString().trim();
        final String statuss=batstats.getText().toString().trim();
        final String voltage=batvoltb.getText().toString().trim();
        final String temp=battemp.getText().toString().trim();
        final String tech=battech.getText().toString().trim();
        final Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say(statuss);
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        say(plugged);
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                say(level);
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        say(health);
                                        h.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                say(voltage);
                                                h.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        say(temp);
                                                        h.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                say(tech);

                                                            }
                                                        },3000);
                                                    }
                                                },4000);
                                            }
                                        },4000);
                                    }
                                },3000);
                            }
                        },3000);
                    }
                },3000);
            }
        },4000);
    }

    public void bathealth(Intent intent){
            int health=intent.getIntExtra(BatteryManager.EXTRA_HEALTH,-1);
            switch (health){
                case BatteryManager.BATTERY_HEALTH_COLD:
                    bathealth.setText("Battery Health:Cold");

                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    bathealth.setText("Battery Health:Good");

                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    bathealth.setText("Battery Health:Dead");

                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    bathealth.setText("Battery Health:OverHeat");

                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    bathealth.setText("Battery Health:Over Voltage");

                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    bathealth.setText("Battery Health:Unknown");

                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    bathealth.setText("Battery Health:Failure");

                    break;
                default:
                    break;
            }
        }
        public void temp(Intent intent){
            int temp=intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
            battemp.setText("Battery Temperature:"+temp+"°C");

        }
        public void tech(Intent intent){
            String tech=intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            battech.setText("Battery Technology:"+tech);

        }
        public  void voltage(Intent intent){

            int volt=intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);
            batvoltb.setText("Battery Voltage:"+volt+"mV");

        }
        public void plugged(Intent intent){
            int chargeplug=intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
            boolean isusb=chargeplug==BatteryManager.BATTERY_PLUGGED_USB||chargeplug==BatteryManager.BATTERY_PLUGGED_AC;
            if(isusb){
                batplug.setText("Battery Plugged:USB");


            }else {
                batplug.setText("Battery Plugged:AC");


            }

        }
        public void level(Intent intent){
            int level=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int scale=intent.getIntExtra(BatteryManager.EXTRA_SCALE,-1);
            float batper=(level/(float)scale)*100;
            batlevel.setText("Battery Level:"+batper+"%");


        }
        public void status(Intent intent){
            int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            boolean ischarging=status==BatteryManager.BATTERY_STATUS_CHARGING||
                    status==BatteryManager.BATTERY_STATUS_FULL;
            if(ischarging){
                batstats.setText("Battery Status:Charging");


            }
            else {
                batstats.setText("Battery Status:Charging Full");

            }
        }
    private void initializeTTS() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(BatteryStatus.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                    ;
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("You are on battery page");
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
    }

