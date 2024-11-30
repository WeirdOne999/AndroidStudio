package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class PlayerEntity extends GameEntity {

    //private boolean _isHolding = false;
    private int currentID = -1;


    public PlayerEntity(){
        /*
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        _sprite = Bitmap.createScaledBitmap(bmp,200,200,true);
        */
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.skeletonattack);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(100,100));
        //R.drawable.skeletonattack,4,5,5,9,12
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,100 * 10,100* 10,true),4,5,12,5,7);
        setLayer(-1);
    }


    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);

        /*
        for (int i = 0; i < gamescene._gameEntities.size();i++){
            if (gamescene._gameEntities.get(i) != this){
                if (gamescene._gameEntities.get(i) instanceof Holder)  {
                    if (this.isColliding(gamescene._gameEntities.get(i))){
                        Holder temp = (Holder)gamescene._gameEntities.get(i);
                        temp.destroy();
                    }

                }
            }
        }
        
         */

        //sprite.update(dt);
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if (motionEvent == null) return;


        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);

        if (currentID == -1 && (motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) ||
        motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            currentID = pointerId;
        } else if (currentID != -1 && (motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP) ||
                motionEvent.getAction() == MotionEvent.ACTION_UP){
            currentID = -1;
        }
        if (currentID != -1){
            for (int i = 0; i < motionEvent.getPointerCount(); i++){
                if (motionEvent.getPointerId(i) == currentID){
                    _position = new Vector2(motionEvent.getX(i),motionEvent.getY(i));
                }
            }

        }
    }
}
