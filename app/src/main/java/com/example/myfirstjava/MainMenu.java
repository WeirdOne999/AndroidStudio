package com.example.myfirstjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends Activity implements View.OnClickListener{

    private Button _helpButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        _helpButton = findViewById(R.id.help_btn);
        _helpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v == _helpButton){
            startActivity(new Intent().setClass(this,HelpPage.class));
        }
    }

}
