package com.example.myfirstjava.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {

    Vector<GameEntity> _gameEntities = new Vector<>();





    @Override
    public void onCreate() {
        super.onCreate();
        _gameEntities.add(new BackgroundEntity(R.drawable.gamescene));
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 5; j++){
                float spacing = 165;
                int size = 165;
                _gameEntities.add(new Holder(new Vector2(i * spacing,j * spacing),size));
            }
        }
    }

    @Override
    public void onUpdate(float dt) {
        for (GameEntity i : _gameEntities){
            i.onUpdate(dt);
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
