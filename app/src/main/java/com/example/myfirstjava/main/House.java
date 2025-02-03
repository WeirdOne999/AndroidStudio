package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class House extends GameEntity {
    Context context;
    private Vibrator _vibrator;
    public House(Vector2 pos, int layer){
        context = GameActivity.instance;
        Vector2 spritesize = new Vector2(getScreenWidth() / 12,getScreenHeight() * 3);
        setPosition(pos);
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.holder);
        setSprite(Bitmap.createScaledBitmap(bmp,(int)spritesize.x,(int)spritesize.y,true));
        setLayer(layer);
        setSize(new Vector2((int)spritesize.x,(int)spritesize.y));
        EndDialog.create();
        _vibrator = (Vibrator) GameActivity.instance.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @SuppressLint("NewApi")
    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        Log.d("HOUSE", "IN UPDATE");
        for(GameEntity i : gamescene._gameEntities){
            if (i instanceof EnemyEntity){
                if (this.touching(i)){
                        MainGameScene.instance.wonLost(false);
                        Log.d("LOST","LOST");
                    if(!EndDialog.isShowing()){
                        EndDialog endDialog = new EndDialog();
                        endDialog.show(GameActivity.instance.getSupportFragmentManager(),"End Dialog");
                        _vibrator.vibrate(VibrationEffect.createOneShot(1000,200));
                        AudioClass.getInstance().PlaySFX(context, R.raw.lost);
                    }

                }
            }

        }
    }

    @Override
    public void onRender(Canvas canvas) {
    }
}
