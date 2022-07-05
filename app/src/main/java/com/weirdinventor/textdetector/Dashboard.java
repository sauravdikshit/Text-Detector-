package com.weirdinventor.textdetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void captureactivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void chooseimage(View view) {

        Intent intent = new Intent(this, Gallery.class);
        startActivity(intent);
    }

    public void speech(View view) {
        Intent intent = new Intent(this, Texttospeech.class);
        startActivity(intent);
    }

    public void shareapp(View view) {
        Intent shareApkIntent = new Intent();
        shareApkIntent.setAction(Intent.ACTION_SEND);
        shareApkIntent.setType("text/plain");
        String shareBody = " Download this Application Now :-https://drive.google.com/file/d/0B9dnqJkfbVi6ck5oVTJBdEhQLWc/view?usp=sharing&hl=en";
        String sharesub = "Text Detector App";
        shareApkIntent.putExtra(Intent.EXTRA_SUBJECT, sharesub);
        shareApkIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareApkIntent, "Share Using"));


    }


}
