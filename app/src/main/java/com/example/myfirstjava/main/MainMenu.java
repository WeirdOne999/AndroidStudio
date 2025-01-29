package com.example.myfirstjava.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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


//RETRIEVES XML ELEMENTS AND SURFACE VIEW ELEMENTS TO MERGE TOGETHER
public class MainMenu extends AppCompatActivity implements View.OnClickListener{

    private Button _helpButton;
    private Button _startButton;
    private UIEntity uiEntity;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        AudioClass.getInstance().PlayBackgroundMusic(this, R.raw.c418, true);
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
        uiEntity = new UIEntity(this, container, R.drawable.village);




        // Instantiate UIEntity with the background image

        uiEntity.setSize(4350,375);
    }

    @Override
    public void onClick(View v){
        AudioClass.getInstance().PlaySFX(this, R.raw.ui);
        if (v == _helpButton){
            startActivity(new Intent().setClass(this,HelpPage.class));
        } else if (v == _startButton){
            startActivity(new Intent().setClass(this, GameActivity.class));
            GameScene.enter(MainGameScene.class);
            AudioClass.getInstance().StopBackgroundMusic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiEntity.resume();
        AudioClass.getInstance().StopBackgroundMusic();
        AudioClass.getInstance().StopAllSFX();
        AudioClass.getInstance().PlayBackgroundMusic(this, R.raw.c418, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uiEntity.pause();
    }

}
