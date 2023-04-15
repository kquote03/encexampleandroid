package com.kquote03.throwawayenc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CryptoUtils bill = new CryptoUtils(this);
        try {
            IvParameterSpec iv = bill.generateIv();
            SecretKey key = bill.getKeyFromPassword("example","isniffsalt");
            FileOutputStream f1 = openFileOutput("messagefile",MODE_PRIVATE);
            f1.write("Did you know that the witch-woman Jenka once had a brother?".getBytes());
            f1.close();
            bill.printFile("messagefile", bill.getFilestreamLen("messagefile"));
            bill.encrypt(key,"messagefile","messagefile", iv);
            printFile("messagefile", bill.getFilestreamLen("messagefile"));
            bill.decrypt(key, "messagefile", "messagefile", iv);
            printFile("messagefile", bill.getFilestreamLen("messagefile"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void printFile(String file, int length) throws IOException {
        FileInputStream f = openFileInput(file);
        int h;
        int count = 0;
        byte[] a = new byte[length];
        while((h = f.read()) != -1) {
            Log.d("DEBUG", String.valueOf(h));
            a[count++] = (byte)h;
        }
        Log.d("DEBUG",new String(a));
        f.close();
    }
}