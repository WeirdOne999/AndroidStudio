package com.example.myfirstjava.main;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Text extends GameEntity {

    private Paint _paint;


    private String text;

    public Text(int color, int size, Paint.Align test){
        _paint = new Paint();
        _paint.setColor(color);
        _paint.setTextSize(size);
        _paint.setTextAlign(test);
    }

    public void setText(String text, Vector2 position){
        this.text = text;
        setPosition(position);
    }



    @Override
    public void onUpdate(float dt, GameScene gamescene) {

    }

    @Override
    public void onRender(Canvas canvas) {
        canvas.drawText(text, _position.x, _position.y, _paint);
    }
}
