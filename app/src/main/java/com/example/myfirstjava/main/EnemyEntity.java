package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.sql.Struct;

class animationHolder{

    public void setAnimationHolder(int id, int _row,int _col, int _start, int _end, int _fps){
        Bitmap _bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), id);
        bmp = _bmp;
        row = _row;
        col = _col;
        start = _start;
        end = _end;
        fps = _fps;
    }

    public Bitmap bmp;
    public int row;
    public int col;
    public int start;
    public int end;
    public int fps;
}

public class EnemyEntity extends LivingEntity{

    Vector2 direction;
    float speed;
    float size;



    public animationHolder walk = new animationHolder();
    public animationHolder attack = new animationHolder();
    public animationHolder idle = new animationHolder();

    public void SetSprite(animationHolder anim){
        setAnimatedSprite(Bitmap.createScaledBitmap(anim.bmp,(int)(getScreenHeight() *size),(int)(getScreenHeight() * size),true),anim.row,anim.col,anim.start,anim.end,anim.fps);
        _animatedSprite.setNew(anim.start,anim.end);
    }

    public EnemyEntity(float health,float speed, float size){
        isEnemy = true;
        SetHealth(health);
        direction = new Vector2(-1,0);
        this.speed = speed;
        this.size = size;
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        addImpulse(direction.normalize().multiply(speed));
        SetPositions(dt);
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
