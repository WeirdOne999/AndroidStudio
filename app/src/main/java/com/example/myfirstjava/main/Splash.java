package com.example.myfirstjava.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.myfirstjava.R;

public class Splash extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashpage);
        Thread splashThread = new Thread(){
            @Override
            public void run(){
                super.run();
                try{
                    sleep(3000);
                    startActivity(new Intent().setClass(Splash.this,MainMenu.class));
                } catch (InterruptedException e ){
                    throw new RuntimeException(e);
                }
            }
        };
        splashThread.start();
    }
}
