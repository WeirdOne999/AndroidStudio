package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.util.Random;

public class Egg extends PhysicsEntity {

    float timer = 0;
    boolean isTouchingGround = false;
    private GameScene gamescene;

    public int amountIn = 0;

    public Egg(int layer, Vector2 position){

        addImpulse(new Vector2(new Random().nextInt(301) -150,-300));
        setPosition(position);
        setLayer(layer);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.egg);
        setSize(new Vector2(10,10));
        setSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight()/15,(int)getScreenHeight()/15,true));
        canTap = true;
        amountIn = new Random().nextInt(5)+1;
    }

    public void ON(int layer, Vector2 position){
        isTouchingGround = false;
        alive();
        addImpulse(new Vector2(new Random().nextInt(301) -150,-300));
        setPosition(position);
        setLayer(layer);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.egg);
        setSize(new Vector2(10,10));
        setSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight()/15,(int)getScreenHeight()/15,true));
        canTap = true;
        amountIn = new Random().nextInt(5)+1;
    }


    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        this.gamescene = gamescene;
        super.onUpdate(dt, gamescene);
        timer += dt;
        if (!isTouchingGround){
            for (int i = 0; i < gamescene._gameEntities.size();i++){
                if (gamescene._gameEntities.get(i) != this){
                    if (gamescene._gameEntities.get(i) instanceof Floor)  {
                        if (this.isColliding(gamescene._gameEntities.get(i))){

                            if(timer >= 1)isTouchingGround = true;
                        }

                    }
                }
            }
            SetPositions(dt);

            setPosition(_position);
            if(timer >= 1 && !isTouchingGround)SetGravity(dt);
        }
        else{
            for (int i = 0; i < gamescene._gameEntities.size();i++){
                if (gamescene._gameEntities.get(i) != this){
                    if (gamescene._gameEntities.get(i) instanceof Egg)  {
                        Egg temp = (Egg)gamescene._gameEntities.get(i);
                        if(!temp.isTouchingGround) continue;
                        if (this.cheaperIsColliding(gamescene._gameEntities.get(i))){

                            temp.amountIn += this.amountIn;
                            destroy();
                        }

                    }
                }
            }
        }

        //Log.d("EGGPOS", _position.x + " " + _position.y);

    }


    @Override
    public void onTap() {
        super.onTap();
        //Log.d("TOUEVENT", "EGG touched!");
        gamescene.addVariable("Egg",amountIn);
        this.destroy();
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
