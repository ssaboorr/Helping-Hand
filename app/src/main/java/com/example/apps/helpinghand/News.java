package com.example.apps.helpinghand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class News extends AppCompatActivity {
    TextView t1, t2, t3;
    String news_url;
    String url, description,title;
    Element content;
    ImageView imageView;
    String urlimage;
    Button mbtn;
    String response;
    public  int i;
    TextToSpeech mytts;
    ArrayList<String> results;
    ScrollView sv;
    RelativeLayout function;
     String content1;
     JSONArray articles;
    RelativeLayout tv;
     ArrayList<String> results1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        t1 = (TextView) this.findViewById(R.id.txttitle);
        t2 = (TextView) this.findViewById(R.id.txtdescription);
        t3 = (TextView) this.findViewById(R.id.txtcontent);
        initializeTextToSpeech();
        imageView = (ImageView) findViewById(R.id.img);

        sv=findViewById(R.id.svvvv);
        tv=findViewById(R.id.tdata);
        function=findViewById(R.id.data);

        cases();
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                say("Next ");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(News.this,News.class));
                    }
                },2000);



            }
        });
        function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                say("Next ");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(News.this,News.class));
                    }
                },2000);


            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                say("Next ");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(News.this,News.class));
                    }
                },2000);


            }
        });
        function.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                say("What Do you Want Title content or description");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                    }
                },2000);
                return false;
            }
        });
        sv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                say("What Do you Want Title content or description");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                    }
                },2000);
                return false;
            }
        });
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                say("What Do you Want Title content or description");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        speak();
                    }
                },2000);
                return false;
            }
        });


    }

    private void cases() {
        Random r=new Random();
        int random=r.nextInt(7)+1;
        switch (random) {
            case 1:
                news_url = "http://newsapi.org/v2/top-headlines?country=in&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
            case 2:
                news_url="http://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
            case 3:
                news_url="http://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
            case 4:
                news_url="http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
            case 5:
                news_url="http://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
            case 6:
                news_url="http://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
            case 7:
                news_url="http://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=0cefae9d78a349adb7437f33425936a6";
                new News.AsyncHttpTask().execute(news_url);
                break;
        }
    }
    private void initializeTextToSpeech() {
        mytts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (mytts.getEngines().size() == 0) {
                    Toast.makeText(News.this, "There is no TTS engine", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    mytts.setLanguage(Locale.ENGLISH);
                    say(" News ");
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

    public class AsyncHttpTask
            extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                response = streamtostring(urlConnection.getInputStream());
                parsResult(response);
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    String streamtostring(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        String result = "";

        while ((data = bufferedReader.readLine()) != null) {
            result += data;
        }
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    private void parsResult(String result) {
        JSONObject response = null;
        try {
            Random r=new Random();
            response = new JSONObject(result);
             articles = response.optJSONArray("articles");
            int random=r.nextInt(articles.length()+1);
            JSONObject article = articles.optJSONObject(random);
            description = article.optString("description");
            content1 = article.optString("content");
             title = article.optString("title");
            urlimage = article.optString("urlToImage");
            t1.setText(title);
            t3.setText(content1);
            t2.setText(description);
            loadimage(urlimage);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 1);
    }
    private void speak1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii say something");
        startActivityForResult(intent, 2);
    }

    private void loadimage(String url) {
        if (url.isEmpty()) {
            Toast.makeText(this, "URL not found", Toast.LENGTH_SHORT).show();
        } else {
            Image image = new Image(imageView);
            image.execute(url);
        }
    }

    private class Image extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public Image(ImageView imageView) {
            this.image = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 1:
                    results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String s = results.get(0);
                    if (s.equalsIgnoreCase("content") || s.equalsIgnoreCase("contents")) {
                        String des = t3.getText().toString();
                        say(des);
                    }if (s.equalsIgnoreCase("title") || s.equalsIgnoreCase("titles")) {
                        String des = t1.getText().toString();
                        say(des);
                    }
                    if (s.equalsIgnoreCase("descriptions") || s.equalsIgnoreCase("description")) {
                        String con = t2.getText().toString();
                        say(con);
                    }
                    break;

            }
        }
    }
}

