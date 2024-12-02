package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class House extends GameEntity {
    public House(Vector2 pos, int layer){
        Vector2 spritesize = new Vector2(getScreenWidth() / 12,getScreenHeight() * 3);
        setPosition(pos);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);
        setSprite(Bitmap.createScaledBitmap(bmp,(int)spritesize.x,(int)spritesize.y,true));
        setLayer(layer);
        setSize(new Vector2((int)spritesize.x,(int)spritesize.y));
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        for(GameEntity i : gamescene._gameEntities){
            if (i instanceof EnemyEntity){
                if (this.touching(i)){
                        MainGameScene.instance.wonLost(false);
                    EndDialog endDialog = new EndDialog();
                    endDialog.show(GameActivity.instance.getSupportFragmentManager(),"End Dialog");
                }
            }

        }
    }

    @Override
    public void onRender(Canvas canvas) {
    }
}
