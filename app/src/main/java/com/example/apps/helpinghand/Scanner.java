package com.example.apps.helpinghand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.zxing.Result;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void handleResult(Result result) {
        ScannerQR.resultext.setText(result.getText());
        data = result.getText().toString();
        checkdata(data);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    private void checkdata(String data) {
        if (data.contains("https:")) {
            if (data.contains("yout")) {
                youtube();
            } else if (data.contains("twitter")) {
                twitter();
            } else if (data.contains("facebook")) {
                facebook();
            } else if (data.contains("instagram")) {
                Insta();
            } else if (data.contains("snapchat")) {
                Snapchat();
            } else if (data.contains("tiktok")) {
                tiktok();
            } else if (data.contains("whatsapp")) {
                whatsapp();
            } else if (data.contains("maps")) {
                maps();
            } else {
                URLL();
            }
        } else if (data.contains("mailto")) {
            mailto(data);
        } else if (data.contains("tel")) {
            phone(data);
        } else if (data.contains("SMSTO")) {
            sms(data);
        } else if (data.contains("VCARD")) {
            vcard();
        } else if (data.contains("MECARD")) {
            mcard();
        } else if (data.contains("WIFI")) {
            wifi(data);
        } else if (data.contains("EVENT")) {
            event(data);
        } else if (data.contains("bitcoin")) {
            bitcoin(data);
        } else if (data.matches("-?\\d+(\\.\\d+)?")) {
            product(data);

        } else {
            //say("Text Barcode is:"+data);
        }
    }

    private void product1(String data) {
        String words4;
        Document document= null;
        try {
            document = Jsoup.connect("https://barcodesdatabase.org/barcode/"+data).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        words4=document.title();
        ScannerQR.resultext1.setText(words4);
    }

    private void bitcoin(String data) {
        String[] distribute = data.split(":");
        String dist1 = distribute[1];
        String[] dist2 = dist1.split("=");
        String dis3 = dist2[0];
        String dist3 = dis3.substring(0, dis3.length() - 6);
        String dis4 = dist2[1];
        ScannerQR.resultext.setText("Bitcoins\n" + "Adrdess:" + dist3 + "\n" + "Amount:" + dis4);
        onBackPressed();
    }

    private void event(String data) {
    String[] event=data.split(":");
    String event1=event[1];
    String event2=event[2];
    String event22=event2.replace("LOCATION","");
    String event3=event[3];
    String event4=event[4];
    String event5=event[5];
    ScannerQR.resultext.setText("Event:"+event2);//+"\n"+event2);//+"\n"+event2);+"\n"+event5+"\n");
    }

    private void wifi(String data) {
        String[] wifi = data.split(";");
        String wifi1 = wifi[0];
        String wifi2 = wifi[1];
        String wifi3 = wifi[2];
        String wifi4 = wifi1.substring(8);
        String wifi5 = wifi2.substring(2);
        String wifi6 = wifi3.substring(2);
        ScannerQR.resultext.setText("Wifi\n" + "SSID(Network Name):" + wifi4 + "\n" + "Password:" + wifi6 + "\n" + "Type:" + wifi5);
        onBackPressed();
    }

    private void mcard() {
    }

    private void vcard() {
    }

    private void sms(String data) {
        String[] sms = data.split(":");
        String sms1 = sms[1];
        String sms2 = sms[2];
        ScannerQR.resultext.setText("SMS To:\n" + "Number:" + sms1 + "\n" + "Message:" + sms2);
        onBackPressed();
    }

    private void phone(String data) {
        String telephone = data.substring(4);
        ScannerQR.resultext.setText("Telephone Number:\n" + telephone);
        onBackPressed();
    }

    private void mailto(String data) {
        String[] mail = data.split("=");
        String mail1 = mail[0];
        String mail11 = mail1.substring(7, mail1.length() - 8);
        String mail2 = mail[1];
        String mail22;
        mail22 = mail2.replace("&body", "");
        String mail3 = mail[2];
        ScannerQR.resultext.setText("Email id:" + mail11 + "\n" + "Subject:" + mail22 + "\n" + "Body:\n" + mail3);
        onBackPressed();
    }

    private void URLL() {
    }

    private void maps() {
    }

    private void tiktok() {
    }

    private void whatsapp() {
    }

    private void Snapchat() {
    }

    private void facebook() {
    }

    private void twitter() {
    }


    private void youtube() {
    }

    private void Insta() {
    }

    private void product(String data) {
        new doit().execute();
    }

    public class doit extends AsyncTask<Void, Void, Void> {
        String words, words1, words2, part1, words3, part2, words4,s;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                s=data;
                product1(data);
                Document doc = Jsoup.connect("https://in-en.openfoodfacts.org/product/"+s).get();
                Elements element=doc.select("h1");
                words=element.text();
                Document doc1 = Jsoup.connect("https://www.buycott.com/upc/"+s).get();
                Elements element1=doc1.select("h2");
                Elements addele=doc1.select("table[class=table product_info_table]");
                words3=addele.text();
                String[] part=words3.split("EAN");
                part2=part[0];
                words1=element1.text();
                Document doc2 = Jsoup.connect("https://in-en.openfoodfacts.org/product/"+s).get();
                Elements element2=doc2.select("div[class=medium-12 large-8 xlarge-8 xxlarge-8 columns]");
                words2=element2.text();
                String[] parts=words2.split("→ ");
                part1=parts[0];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ScannerQR.resultext.setText("Products Name:"+words+"\nInformation:"+words1+"\n"+part2+"\n"+part1);

            onBackPressed();


        }
    }

}
