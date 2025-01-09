package com.example.myfirstjava.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;


public class HelpPage extends AppCompatActivity implements View.OnClickListener{

    private Button _backbtn;
    //back_btn
    private Button _startButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.helppage);
        _backbtn = findViewById(R.id.back_btn);
        _backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == _backbtn){
            startActivity(new Intent().setClass(this,MainMenu.class));
        }
    }

}
