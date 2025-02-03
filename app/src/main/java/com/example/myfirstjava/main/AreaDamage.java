package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class AreaDamage extends GameEntity {
    float damage;
    boolean enemy = false;
    public AreaDamage(float size,Vector2 position,float damage, boolean targetEnemy){
        float scale = ((int)getScreenHeight() / 12) * size;
        enemy = targetEnemy;
        setSize(new Vector2(scale,scale));
        setPosition(position);

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);

        setSprite(Bitmap.createScaledBitmap(bmp,(int)scale,(int)scale,true));

        this.damage = damage;
    }

    public void ON(float size,Vector2 position,float damage, boolean targetEnemy){
        alive();
        enemy = targetEnemy;
        float scale = ((int)getScreenHeight() / 12) * size;
        setSize(new Vector2(scale,scale));
        setPosition(position);

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);

        setSprite(Bitmap.createScaledBitmap(bmp,(int)scale,(int)scale,true));

        this.damage = damage;
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        destroy();
        for (int i = 0; i < gamescene._gameEntities.size();i++){
            if (gamescene._gameEntities.get(i) != this){

                if (gamescene._gameEntities.get(i) instanceof LivingEntity)  {
                    if (this.touching(gamescene._gameEntities.get(i))){

                        LivingEntity temp = (LivingEntity)gamescene._gameEntities.get(i);
                        if (temp.isEnemy){
                            //temp.destroy();
                            if(enemy) temp.SetHealth(temp.getHealth() - damage);
                        }else{
                            if(!enemy) temp.SetHealth(temp.getHealth() - damage);
                        }

                    }
                }
            }
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        //super.onRender(canvas);
    }
}
