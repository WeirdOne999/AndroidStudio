package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
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

    enum State{
        WALK,
        ATTACK,
        IDLE
    }

    State currentState;
    State prevState;


    Vector2 direction;
    float speed;
    float currentspeed;
    float size;

    LivingEntity touchedPlant;

    float yOffset = getScreenHeight() / 12;

    public animationHolder walk = new animationHolder();
    public animationHolder attack = new animationHolder();
    public animationHolder idle = new animationHolder();

    public LivingEntity touchedPLant;

    public void SetSprite(animationHolder anim){
        float temp = 0;
        setAnimatedSprite(Bitmap.createScaledBitmap(anim.bmp,
                (int)(getScreenHeight() * (size * anim.col) ),
                (int)(getScreenHeight() * (size * anim.row)),
                false),anim.row,anim.col,anim.start,anim.end,anim.fps);
        _animatedSprite.setNew(anim.start,anim.end);
        _animatedSprite.offset = new Vector2(0,yOffset);

    }

    public EnemyEntity(float health,float speed, float size){
        isEnemy = true;
        SetHealth(health);
        direction = new Vector2(-1,0);
        this.speed = speed;
        currentspeed = speed;
        this.size = size;
        currentState = State.WALK;
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        addImpulse(direction.normalize().multiply(currentspeed));
        SetPositions(dt);
        boolean isTouchingPlant = false;
        touchedPlant = null;
        for (int i = 0; i < gamescene._gameEntities.size();i++){
            if (gamescene._gameEntities.get(i) != this){

                if (gamescene._gameEntities.get(i) instanceof LivingEntity)  {
                    if (this.isColliding(gamescene._gameEntities.get(i))){

                        LivingEntity temp = (LivingEntity)gamescene._gameEntities.get(i);
                        if (!temp.isEnemy){
                            isTouchingPlant = true;
                            touchedPlant = temp;
                        }
                    }
                }
            }
        }

        //ENTER STATE
        if (currentState != prevState){
            if (prevState!= null) Log.d("ENEMYSTATECHECK", currentState.toString() + " " + prevState.toString());
            if (currentState == State.IDLE){

                currentspeed = 0;
                SetSprite(idle);
                _animatedSprite.setLopping(true);
                //Log.d("ENEMYSTATE", "IDLE ENTER");
            }
            else if (currentState == State.WALK){

                currentspeed = speed;
                SetSprite(walk);
                _animatedSprite.setLopping(true);
                //Log.d("ENEMYSTATE", "WALK ENTER");
            }
            else if (currentState == State.ATTACK){
                SetSprite(attack);
                //Log.d("ENEMYSTATE", "ATTACK ENTER");
            }

            prevState = currentState;
            //Log.d("ENEMYSTATEAFTER", currentState.toString() + " " + prevState.toString());

        }
    }

    public boolean isAnim(animationHolder anim) {return anim.bmp.sameAs(_animatedSprite._bmp);}

    public void attack(float dt, GameScene gameScene,LivingEntity ge){
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
