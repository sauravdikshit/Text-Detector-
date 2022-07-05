package com.weirdinventor.textdetector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class Splashscreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_TIME = 5000;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        final Handler handler = new Handler();

        //SharedPreferences preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
     //  String FristTime = preferences.getString("FristTimeInstall","");
     //   if (FristTime.equals("Yes")){
         //   Intent intent =new Intent(Splashscreen.this, Dashboard.class);
          //  startActivity(intent);


     //   }else {
         //   SharedPreferences.Editor editor=preferences.edit();
         //   editor.putString("FristTimeInstall","Yes");
         //  editor.apply();
       // }


        handler.postDelayed(new Runnable() {
          @Override
           public void run() {

              Intent intent=new Intent(Splashscreen.this,Dashboard.class);

                Splashscreen.this.startActivity(intent);
                Splashscreen.this.finish();

              handler.removeCallbacks(this);
              Navigation.findNavController(view).navigate(R.id.splashscreen3);


          }
        },SPLASH_DISPLAY_TIME);
    }

}