package com.example.myfirstjava.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;


public class MainMenu extends Activity implements View.OnClickListener{

    private Button _helpButton;
    private Button _startButton;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        _helpButton = findViewById(R.id.help_btn);
        _helpButton.setOnClickListener(this);
        _startButton = findViewById(R.id.start_btn);
        _startButton.setOnClickListener(this);
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

}
