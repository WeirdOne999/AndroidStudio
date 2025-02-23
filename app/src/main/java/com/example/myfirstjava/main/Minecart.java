package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Minecart extends PhysicsEntity{
    private Vibrator _vibrator;
    float speed = 30;
    boolean touchEnemy = false;
    private static boolean isFirstCollision = false;
    Context context;
    public Minecart(int layer, Vector2 position){
        context = GameActivity.instance;
        setLayer(layer);
        setPosition(position);

        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.minecart);
        setSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight()/10,(int)getScreenHeight()/10,true));

        _vibrator = (Vibrator) GameActivity.instance.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @SuppressLint("NewApi")
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        if(touchEnemy)addImpulse(new Vector2(1,0).multiply(speed));
        SetPositions(dt);

        for (int i = 0; i < gamescene._gameEntities.size();i++){
            if (gamescene._gameEntities.get(i) != this){
                //dies when it touches border
                if (gamescene._gameEntities.get(i) instanceof Border)  {
                    if (this.touching(gamescene._gameEntities.get(i))){
                        destroy();
                    }
                }
                //make touch enemey
                if (gamescene._gameEntities.get(i) instanceof LivingEntity)  {
                    if (this.cheaperIsColliding(gamescene._gameEntities.get(i))){

                        LivingEntity temp = (LivingEntity)gamescene._gameEntities.get(i);
                        if (temp.isEnemy){
                            //Log.d("PROJTOUCH","ENEMY");
                            temp.destroy();
                            touchEnemy = true;
                            AudioClass.getInstance().PlaySFX(context, R.raw.minecart);
                            _vibrator.vibrate(VibrationEffect.createOneShot(500,100));
                            if (!isFirstCollision) {
                                isFirstCollision = true;
                                AudioClass.getInstance().StopBackgroundMusic();
                                AudioClass.getInstance().PlayBackgroundMusic(context, R.raw.pigstep, true);
                            }
                        }
                    }
                }
            }
        }
    }
}
