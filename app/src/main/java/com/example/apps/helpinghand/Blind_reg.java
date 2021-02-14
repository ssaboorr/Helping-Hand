package com.example.apps.helpinghand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class Blind_reg extends AppCompatActivity {

    TextView name;
    TextView phone;
    TextView email1;
    TextView password1;
    TextView status1;
    String s1="Your name Please";
    String s2="Your Phone number please";
    String s3="Your email please";
    String s4="Your Password please";

    TextToSpeech mytts1;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_reg);
        name=findViewById(R.id.bname);
        phone=findViewById(R.id.bphone);
        email1=findViewById(R.id.bemail);
        password1=findViewById(R.id.bpass);
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Info");
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                says("Help me with Your Detail");
            }
        },3000);

        initialize();
        Handler h1=new Handler();
        h1.postDelayed(new Runnable() {
            @Override
            public void run() {
                callname();
            }
        },3000);

    }

    private void callname() {
        final Handler h2= new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                says(s1);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        name1();

                    }
                },3000);
            }
        },4000);

    }

    private void callphone() {
        final Handler h2= new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                says(s2);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        phone();

                    }
                },3000);
            }
        },4000);

    }

    private void callemail() {
        final Handler h2= new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {

                says(s3);
                h2.postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                email();
                            }
                        },3000);
            }
        },4000);

    }

    private void callpass() {
        final Handler h2= new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {

                says(s4);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pass();
                    }
                },3000);
            }
        },4000);

    }

    private void initialize() {
        mytts1=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(mytts1.getEngines().size()==0)
                {
                    Toast.makeText(Blind_reg.this,"There is no TTS engine",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    mytts1.setLanguage(Locale.ENGLISH);
                    says("You are On Signup");

                }
            }
        });
    }
    private void says(String message) {
        if (Build.VERSION.SDK_INT>=21)
        {
            mytts1.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else {
            mytts1.speak(message,TextToSpeech.QUEUE_FLUSH,null);
        }
    }
    private void name1(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,1);
    }
    private void phone(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,2);
    }
    private void email(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,3);
    }
    private void pass(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,4);
    }
    private void yesname(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,5);
    }
    private  void yesphone(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,6);
    }
    private void yesmail(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,7);
    }
    private void yespass(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hii say something");
        startActivityForResult(intent,8);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && data!=null){
            switch (requestCode){
                case 1:
                    ArrayList<String> rname=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    says("I'm repeating your name say yes or no");
                    pname(rname.get(0));
                    break;
                case 2:
                    ArrayList<String> rphone=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    says("I'm repeating your phone no say yes or no");
                    pphone(rphone.get(0));
                    break;
                case 3:
                    ArrayList<String> remail=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    says("I'm repeating your email say yes or no");
                    pemail(remail.get(0));
                    break;
                case 4:
                    ArrayList<String> rpass=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    says("I'm repeating your password say yes or no");
                    ppass(rpass.get(0));
                    break;
                case 5:
                    ArrayList<String> yn=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String yn1=yn.get(0).toString();
                    String yn2=yn1.toLowerCase();
                    if(yn2.equals("yes")|| yn2.equals("ok")){

                        callphone();
                    }else {
                        callname();
                    }
                    break;
                case 6:
                    ArrayList<String> yp=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String yp1=yp.get(0).toString();
                    String yp2=yp1.toLowerCase();
                    if(yp2.equals("yes")|| yp2.equals("ok")){
                        callemail();
                    }else {
                        callphone();
                    }break;
                case 7:
                    ArrayList<String> ye=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String ye1=ye.get(0).toString();
                    String ye2=ye1.toLowerCase();
                    if(ye2.equals("yes")|| ye2.equals("ok")){
                        callpass();
                    }else {
                        callemail();
                    }break;
                case 8:
                    ArrayList<String> ypa=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String ypa1=ypa.get(0).toString();
                    String ypa2=ypa1.toLowerCase();
                    if(ypa2.equals("yes")|| ypa2.equals("ok")){
                        Handler h=new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                firebasesave();
                                says("Thank You For Registration ");
                                Handler h=new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(Blind_reg.this,Blind_log.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },1000);
                            }
                        },2000);
                    }else {
                        callpass();
                    }break;
            }

        }
    }

    private void firebasesave() {
        firebaseAuth=FirebaseAuth.getInstance();
        String authpassword=password1.getText().toString().trim();
        String authmail=email1.getText().toString().trim();
        String amail=authmail.replace(" ","");
        String apass=authpassword.replace(" ","");
        firebaseAuth.createUserWithEmailAndPassword(amail,apass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(Blind_reg.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                    databasesave();
                }
                else {
                    Toast.makeText(Blind_reg.this, "Unsucessfully", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void databasesave() {
        String id=databaseReference.push().getKey();
        String authphone=phone.getText().toString().trim();

        String authname=name.getText().toString().trim();

        String authmail1=email1.getText().toString().trim();

        String authpassword1=password1.getText().toString().trim();
            String statuss="yes";

        SaveBlindReg saveBlindReg=new SaveBlindReg(authname,authphone,statuss,authmail1,authpassword1) ;
        databaseReference.child("Users").child(id).setValue(saveBlindReg);
        Toast.makeText(this, "Sucessfull", Toast.LENGTH_SHORT).show();
    }

    private void ppass(String s) {
        final String upass = s;
        String removep=upass.replace(" ","").toLowerCase();
        password1.setText(removep);
        final Handler h2 = new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                says(upass);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yespass();

                    }
                }, 4000);
            }
        }, 3000);

    }
    private void pemail(String s){
        final String uemail=s;
        String removem=uemail.replace(" ","").toLowerCase();
        email1.setText(removem);
        final Handler h3 = new Handler();
        h3.postDelayed(new Runnable() {
            @Override
            public void run() {
                says(uemail);
                h3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yesmail();

                    }
                }, 4000);
            }
        }, 3000);
    }
    private void pphone(String s) {
        final String uphone=s;
        String removeph=uphone.replace(" ","").toLowerCase();
        phone.setText(removeph);
        final Handler h4= new Handler();
        h4.postDelayed(new Runnable() {
            @Override
            public void run() {
                says(uphone);
                h4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yesphone();

                    }
                },4000);
            }
        },3000);

    }

    private void pname(String s) {
        final String uname=s;
        String removen=uname.replace(" ","");
        name.setText(removen);
        final Handler h2= new Handler();
        h2.postDelayed(new Runnable() {
            @Override
            public void run() {
                says(uname);
                h2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yesname();

                    }
                },4000);
            }
        },3000);

    }}
