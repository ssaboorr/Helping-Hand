package com.example.apps.helpinghand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.ArrayList;
import java.util.Locale;

public class Translation extends AppCompatActivity {
    TextView sourcelang, sourcetext, transatelang, translatetext;
    String text,result;
    EditText srctxt;
    Button transbtn;
    TextToSpeech mytts;
    ArrayList<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        sourcelang = (TextView) findViewById(R.id.detectlang);
        sourcetext = (TextView) findViewById(R.id.detecttext);
        transatelang = (TextView) findViewById(R.id.Translang);
        translatetext = (TextView) findViewById(R.id.Transtext);
        srctxt = (EditText) findViewById(R.id.txtsource);
        transbtn = (Button) findViewById(R.id.btntrans);
        initializeTextToSpeech();
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                say("Say  the language name in which you want to translate");
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                    }
                }, 4000);
            }
        }, 2000);
        transbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identifylang();
            }
        });
    }

    private void identifylang() {
        text = srctxt.getText().toString();
        say("The Source text which will be translated is:" + text);
        FirebaseLanguageIdentification identification = FirebaseNaturalLanguage.getInstance()
                .getLanguageIdentification();
        sourcelang.setText("Detecting..");
        identification.identifyLanguage(text).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s.equals("und")) {
                    Toast.makeText(Translation.this, "Language Not Identified", Toast.LENGTH_SHORT).show();
                } else {
                    getLangocde(s);
                }
            }
        });
    }

    private void getLangocde(String language) {
        int langcode;
        switch (language) {
            case "af":
                langcode = FirebaseTranslateLanguage.AF;
                sourcetext.setText("Afrikaans");
                break;
            case "ar":
                langcode = FirebaseTranslateLanguage.AR;
                sourcetext.setText("Arabic");
                break;
            case "be":
                langcode = FirebaseTranslateLanguage.BE;
                sourcetext.setText("Belarusian");
                break;
            case "bg":
                langcode = FirebaseTranslateLanguage.BG;
                sourcetext.setText("Bulgarian");
                break;
            case "bn":
                langcode = FirebaseTranslateLanguage.BN;
                sourcetext.setText("Bengali");
                break;
            case "ca":
                langcode = FirebaseTranslateLanguage.CA;
                sourcetext.setText("Catalan");
                break;
            case "cs":
                langcode = FirebaseTranslateLanguage.CS;
                sourcetext.setText("Czech");
                break;
            case "cy":
                langcode = FirebaseTranslateLanguage.CY;
                sourcetext.setText("Welsh");
                break;
            case "da":
                langcode = FirebaseTranslateLanguage.DA;
                sourcetext.setText("Danish");
                break;
            case "de":
                langcode = FirebaseTranslateLanguage.DE;
                sourcetext.setText("German");
                break;
            case "el":
                langcode = FirebaseTranslateLanguage.EL;
                sourcetext.setText("Greek");
                break;
            case "en":
                langcode = FirebaseTranslateLanguage.EN;
                sourcetext.setText("English");
                break;
            case "hi":
                langcode = FirebaseTranslateLanguage.HI;
                sourcetext.setText("Hindi");
                break;
            case "eo":
                langcode = FirebaseTranslateLanguage.EO;
                sourcetext.setText("Esperanto");
                break;
            case "es":
                langcode = FirebaseTranslateLanguage.ES;
                sourcetext.setText("Spanish");
                break;
            case "et":
                langcode = FirebaseTranslateLanguage.ET;
                sourcetext.setText("Estonian");
                break;
            case "fa":
                langcode = FirebaseTranslateLanguage.FA;
                sourcetext.setText("Persian");
                break;
            case "fi":
                langcode = FirebaseTranslateLanguage.FI;
                sourcetext.setText("Finnish");
                break;
            case "fr":
                langcode = FirebaseTranslateLanguage.FR;
                sourcetext.setText("French");
                break;
            case "ga":
                langcode = FirebaseTranslateLanguage.GA;
                sourcetext.setText("Irish");
                break;
            case "gl":
                langcode = FirebaseTranslateLanguage.GL;
                sourcetext.setText("Galician");
                break;
            case "gu":
                langcode = FirebaseTranslateLanguage.GU;
                sourcetext.setText("Gujarati");
                break;
            case "he":
                langcode = FirebaseTranslateLanguage.HE;
                sourcetext.setText("tHebrew");
                break;
            case "hr":
                langcode = FirebaseTranslateLanguage.HR;
                sourcetext.setText("Croatian");
                break;
            case "ht":
                langcode = FirebaseTranslateLanguage.HT;
                sourcetext.setText("Haitian");
                break;
            case "hu":
                langcode = FirebaseTranslateLanguage.HU;
                sourcetext.setText("Hungarian");
                break;
            case "id":
                langcode = FirebaseTranslateLanguage.ID;
                sourcetext.setText("Indonesian");
                break;
            case "it":
                langcode = FirebaseTranslateLanguage.IT;
                sourcetext.setText("Icelandic");
                break;
            case "is":
                langcode = FirebaseTranslateLanguage.IS;
                sourcetext.setText("Italian");
                break;
            case "ja":
                langcode = FirebaseTranslateLanguage.JA;
                sourcetext.setText("Japanese");
                break;
            case "ka":
                langcode = FirebaseTranslateLanguage.KA;
                sourcetext.setText("Georgian");
                break;
            case "kn":
                langcode = FirebaseTranslateLanguage.KN;
                sourcetext.setText("Kannada");
                break;
            case "ko":
                langcode = FirebaseTranslateLanguage.KO;
                sourcetext.setText("Korean");
                break;
            case "lt":
                langcode = FirebaseTranslateLanguage.LT;
                sourcetext.setText("Lithuanian");
                break;
            case "lv":
                langcode = FirebaseTranslateLanguage.LV;
                sourcetext.setText("Latvian");
                break;
            case "mk":
                langcode = FirebaseTranslateLanguage.MK;
                sourcetext.setText("Macedonian");
                break;
            case "mr":
                langcode = FirebaseTranslateLanguage.MR;
                sourcetext.setText("Marathi");
                break;
            case "ms":
                langcode = FirebaseTranslateLanguage.MS;
                sourcetext.setText("Malay");
                break;
            case "mt":
                langcode = FirebaseTranslateLanguage.MT;
                sourcetext.setText("Maltese");
                break;
            case "nl":
                langcode = FirebaseTranslateLanguage.NL;
                sourcetext.setText("Dutch");
                break;
            case "no":
                langcode = FirebaseTranslateLanguage.NO;
                sourcetext.setText("Norwegian");
                break;
            case "pl":
                langcode = FirebaseTranslateLanguage.PL;
                sourcetext.setText("Polish");
                break;
            case "PT":
                langcode = FirebaseTranslateLanguage.PT;
                sourcetext.setText("Portuguese");
                break;
            case "ro":
                langcode = FirebaseTranslateLanguage.RO;
                sourcetext.setText("Romanian");
                break;
            case "ru":
                langcode = FirebaseTranslateLanguage.RU;
                sourcetext.setText("Russian");
                break;
            case "sk":
                langcode = FirebaseTranslateLanguage.SK;
                sourcetext.setText("Slovak");
                break;
            case "sl":
                langcode = FirebaseTranslateLanguage.SL;
                sourcetext.setText("Slovenian");
                break;
            case "sq":
                langcode = FirebaseTranslateLanguage.SQ;
                sourcetext.setText("Albanian");
                break;
            case "sv":
                langcode = FirebaseTranslateLanguage.SV;
                sourcetext.setText("Swedish");
                break;
            case "sw":
                langcode = FirebaseTranslateLanguage.SW;
                sourcetext.setText("Swahili");
                break;
            case "ta":
                langcode = FirebaseTranslateLanguage.TA;
                sourcetext.setText("Tamil");
                break;
            case "te":
                langcode = FirebaseTranslateLanguage.TE;
                sourcetext.setText("Telugu");
                break;
            case "th":
                langcode = FirebaseTranslateLanguage.TH;
                sourcetext.setText("Thai");
                break;
            case "tl":
                langcode = FirebaseTranslateLanguage.TL;
                sourcetext.setText("Tagalog");
                break;
            case "tr":
                langcode = FirebaseTranslateLanguage.TR;
                sourcetext.setText("Turkish");
                break;
            case "uk":
                langcode = FirebaseTranslateLanguage.UK;
                sourcetext.setText("Ukrainian");
                break;
            case "ur":
                langcode = FirebaseTranslateLanguage.UR;
                sourcetext.setText("Urdu");
                break;
            case "vi":
                langcode = FirebaseTranslateLanguage.VI;
                sourcetext.setText("Vietnamese");
                break;
            case "zh":
                langcode = FirebaseTranslateLanguage.ZH;
                sourcetext.setText("Chinese");
                break;
            default:
                langcode = 0;
        }
        translateText(langcode);
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }

    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(Translation.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say("You are on Translation  page");
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

    private void translateText(int langcode) {
        translatetext.setText("Translating..");
        int langc = functiontranslate(result);
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(langcode)
                .setTargetLanguage(langc)
                .build();
        final FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance()
                .getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(final String s) {
                        translatetext.setText(s);
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                say("The Translated text is"+s);
                            }
                        },2000);
                    }
                });
            }
        });
    }

    private int functiontranslate(String Language) {
        int langcode;
        switch (Language) {
            case "Arabic":
                langcode = FirebaseTranslateLanguage.AR;
                break;
            case "Bengali":
                langcode = FirebaseTranslateLanguage.BN;
                break;
            case "German":
                langcode = FirebaseTranslateLanguage.DE;
                break;
            case "Greek":
                langcode = FirebaseTranslateLanguage.EL;
                break;
            case "English":
                langcode = FirebaseTranslateLanguage.EN;
                break;
            case "Hindi":
                langcode = FirebaseTranslateLanguage.HI;
                break;
            case "esperanto":
                langcode = FirebaseTranslateLanguage.EO;
                break;
            case "Spanish":
                langcode = FirebaseTranslateLanguage.ES;
                break;
            case "Persian":
                langcode = FirebaseTranslateLanguage.FA;
                break;
            case "French":
                langcode = FirebaseTranslateLanguage.FR;
                break;
            case "Gujarati":
                langcode = FirebaseTranslateLanguage.GU;
                break;
            case "Hungarian":
                langcode = FirebaseTranslateLanguage.HU;
                break;
            case "Indonesian":
                langcode = FirebaseTranslateLanguage.ID;
                break;
            case "Icelandic":
                langcode = FirebaseTranslateLanguage.IT;
                break;
            case "Italian":
                langcode = FirebaseTranslateLanguage.IS;
                break;
            case "Japanese":
                langcode = FirebaseTranslateLanguage.JA;
                break;
            case "Georgian":
                langcode = FirebaseTranslateLanguage.KA;
                break;
            case "Kannada":
                langcode = FirebaseTranslateLanguage.KN;
                break;
            case "Korean":
                langcode = FirebaseTranslateLanguage.KO;
                break;
            case "Marathi":
                langcode = FirebaseTranslateLanguage.MR;
                break;
            case "Dutch":
                langcode = FirebaseTranslateLanguage.NL;
                break;
            case "Polish":
                langcode = FirebaseTranslateLanguage.PL;
                break;
            case "Portuguese":
                langcode = FirebaseTranslateLanguage.PT;
                break;
            case "Romanian":
                langcode = FirebaseTranslateLanguage.RO;
                break;
            case "Russian":
                langcode = FirebaseTranslateLanguage.RU;
                break;
            case "Slovak":
                langcode = FirebaseTranslateLanguage.SK;
                break;
            case "Slovenian":
                langcode = FirebaseTranslateLanguage.SL;
                break;
            case "Albanian":
                langcode = FirebaseTranslateLanguage.SQ;
                break;
            case "Swedish":
                langcode = FirebaseTranslateLanguage.SV;
                break;
            case "Tamil":
                langcode = FirebaseTranslateLanguage.TA;
                break;
            case "Telugu":
                langcode = FirebaseTranslateLanguage.TE;
                break;
            case "Thai":
                langcode = FirebaseTranslateLanguage.TH;
                break;
            case "Turkish":
                langcode = FirebaseTranslateLanguage.TR;
                break;
            case "Ukrainian":
                langcode = FirebaseTranslateLanguage.UK;
                break;
            case "Urdu":
                langcode = FirebaseTranslateLanguage.UR;
                break;
            case "Vietnamese":
                langcode = FirebaseTranslateLanguage.VI;
                break;
            case "Chinese":
                langcode = FirebaseTranslateLanguage.ZH;
                break;
            default:
                langcode = 0;
        }
        return langcode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    result=results.get(0);
                    break;
            }
        }
    }
}
