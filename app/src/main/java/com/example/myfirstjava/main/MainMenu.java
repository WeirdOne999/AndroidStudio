package com.example.myfirstjava.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;


import android.widget.FrameLayout;


public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    private Button _helpButton;
    private Button _startButton;
    private UIEntity uiEntity;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.mainmenu);
        _helpButton = findViewById(R.id.help_btn);
        _helpButton.setOnClickListener(this);
        _startButton = findViewById(R.id.start_btn);
        _startButton.setOnClickListener(this);



        FrameLayout container = findViewById(R.id.ui_entity_container);


        // Instantiate UIEntity with the background image
        uiEntity = new UIEntity(this, container, R.drawable.village);
        uiEntity.setSize(4350,375);
    }

    @Override
    public void onClick(View v){
        if (v == _helpButton){
            startActivity(new Intent().setClass(this,HelpPage.class));
        } else if (v == _startButton){
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiEntity.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiEntity.pause();
    }

}
