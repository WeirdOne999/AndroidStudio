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

public class ProjectileEntity extends PhysicsEntity{

    boolean hasGravity;
    float speed;
    Vector2 direction;
    public ProjectileEntity(GameEntity parent, Vector2 direction, float speed, boolean gravity){
        setLayer(parent.getLayer());
        setPosition(parent.getPosition());
        hasGravity = gravity;
        this.speed = speed;
        this.direction = direction;
    }

    public void SpriteSet(int id, int size){
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), id);
        setSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight()/size,(int)getScreenHeight()/size,true));

    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        if (hasGravity) SetGravity(dt);
        addImpulse(direction.normalize().multiply(speed));
        SetPositions(dt);

        for (int i = 0; i < gamescene._gameEntities.size();i++){
            if (gamescene._gameEntities.get(i) != this){
                if (gamescene._gameEntities.get(i) instanceof Border)  {
                    if (this.touching(gamescene._gameEntities.get(i))){
                        destroy();
                    }
                }

                if (gamescene._gameEntities.get(i) instanceof Floor)  {
                    if (this.isColliding(gamescene._gameEntities.get(i))){
                        //FLOOR
                        touchFloor();
                    }
                }

                if (gamescene._gameEntities.get(i) instanceof LivingEntity)  {
                    if (this.cheaperIsColliding(gamescene._gameEntities.get(i))){

                        LivingEntity temp = (LivingEntity)gamescene._gameEntities.get(i);
                        if (temp.isEnemy){
                            //Log.d("PROJTOUCH","ENEMY");
                            //ENEMY
                            touchEnemy(temp);
                        }else{
                            //Log.d("PROJTOUCH","PLANT");
                            //PLANT
                            touchPlant(temp);
                        }
                    }
                }
            }
        }

    }

    public void touchFloor(){

    }
    public void touchEnemy(LivingEntity enemy){

    }

    public void touchPlant(LivingEntity plant){

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }
}
