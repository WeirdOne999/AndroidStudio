package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Egg extends PhysicsEntity{

    float timer = 0;
    boolean isTouchingGround = false;
    private GameScene gamescene;
    public Egg(int layer, Vector2 position){

        addImpulse(new Vector2(0,-200));
        setPosition(position);
        setLayer(layer);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.egg);
        _sprite = Bitmap.createScaledBitmap(bmp,50,50,true);

    }
    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        this.gamescene = gamescene;
        timer += dt;
        if (!isTouchingGround){
            for (int i = 0; i < gamescene._gameEntities.size();i++){
                if (gamescene._gameEntities.get(i) != this){
                    if (gamescene._gameEntities.get(i) instanceof Holder)  {
                        if (this.isColliding(gamescene._gameEntities.get(i))){
                            if(timer > 2) isTouchingGround = true;
                        }

                    }
                }
            }
            SetPositions(dt);

            setPosition(_position);
            if(timer >= 1 && !isTouchingGround)SetGravity(dt);
        }



    }

    @Override
    public void onTap() {
        super.onTap();
        gamescene.addVariable("Egg",1);
        canDestroy();
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
