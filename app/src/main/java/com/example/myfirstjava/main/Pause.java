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

public class Pause extends GameEntity {

    public Pause(){
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);

        setSprite(Bitmap.createScaledBitmap(bmp,(int)getScreenHeight()/10,(int)getScreenHeight()/10,true));
        setPosition(new Vector2(getScreenWidth()/10 * 9,getScreenHeight()/10 * 1));
    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);



    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }

    @Override
    public void onTap() {
        super.onTap();
        //Log.d("TOUEVENT", "PAUSE touched!");
        if (!BackDialog.isShowing()){

            BackDialog backDialog = new BackDialog();
            backDialog.show(GameActivity.instance.getSupportFragmentManager(),"Back dialog");

        }
    }
}
