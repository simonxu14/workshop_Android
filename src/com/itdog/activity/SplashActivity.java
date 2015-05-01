package com.itdog.activity;
import com.itdog.acticity.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {  
	  
    private final int SPLASH_DISPLAY_LENGHT = 6000; // —”≥Ÿ¡˘√Î  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.splash);  

        new Handler().postDelayed(new Runnable() {  
            public void run() {  
                Intent mainIntent = new Intent(SplashActivity.this,  
                        MainActivity.class);  
                SplashActivity.this.startActivity(mainIntent);  
                SplashActivity.this.finish();  
            }  
  
        }, 0);          
  
    }  
}  
