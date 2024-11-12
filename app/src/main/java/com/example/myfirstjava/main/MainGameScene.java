package com.example.myfirstjava.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {

    public static MainGameScene instance = null;



    private final Vector2 gridOffset = new Vector2(350,100);

    @Override
    public void onCreate() {
        super.onCreate();
        _gameEntities.add(new BackgroundEntity(R.drawable.gamescene));

        //Create Grid Holders
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 5; j++){
                float spacing = 165;
                int size = (int) spacing + 5;
                _gameEntities.add(new Holder(new Vector2(i * spacing + gridOffset.x,j * spacing + gridOffset.y),size));
            }
        }
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);

        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if (motionEvent == null) return;
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !BackDialog.isShowing()){
            BackDialog backDialog = new BackDialog();
            backDialog.show(GameActivity.instance.getSupportFragmentManager(),"Back dialog");
        }
    }



    @Override
    public void onRender(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#b2d4ff"));
        for (GameEntity i : _gameEntities){
            i.onRender(canvas);
        }
    }
}
