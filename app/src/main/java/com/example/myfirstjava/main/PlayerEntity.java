package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.sql.Struct;

class ButtonStructure{
    enum type{
        CHICKEN,
        LLAMA,
        IRONGOLEM,
        SHEEP
    }
    public ButtonStructure(int id, type entity,int cost){
        this.id = id;
        this.entity = entity;
        this.cost = cost;
    }

    public int id;
    public type entity;
    public int cost;
}

public class PlayerEntity extends GameEntity {

    //private boolean _isHolding = false;
    private int currentID = -1;
    private boolean render = false;
    public ButtonStructure[] cursordrawable = new ButtonStructure[4];

    public GameEntity _entityCache;

    public int currentEntity = -1;

    public PlayerEntity(){
        /*
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        _sprite = Bitmap.createScaledBitmap(bmp,200,200,true);
        */
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.skeletonattack);
        //_sprite = Bitmap.createScaledBitmap(bmp,size,size,true);
        setSize(new Vector2(100,100));
        //R.drawable.skeletonattack,4,5,5,9,12

        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,100 * 10,100* 10,true),bmp.getWidth() / 32,bmp.getWidth() / 32,12,0,0);
        setLayer(-1);
        cursordrawable[0] = new ButtonStructure(R.drawable.minibunny, ButtonStructure.type.CHICKEN,10);
        cursordrawable[1] = new ButtonStructure(R.drawable.miniwolf,ButtonStructure.type.LLAMA,15);
        cursordrawable[2] = new ButtonStructure(R.drawable.minibear,ButtonStructure.type.IRONGOLEM,20);
        cursordrawable[3] = new ButtonStructure(R.drawable.miniboar,ButtonStructure.type.SHEEP,20);

    }

    public void ChangeCursorSprite(){



    }


    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        Log.d("CCS", "" + MainGameScene.instance.ChangeCursorSpritei);
        Bitmap bmp = null;
        if (MainGameScene.instance.ChangeCursorSpritei >= 0 && MainGameScene.instance.ChangeCursorSpritei < 4) bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), cursordrawable[MainGameScene.instance.ChangeCursorSpritei].id);

        if (bmp == null) {

            return; // Handle the error appropriately
        }

        // Scale the bitmap and set as animated sprite
        //Log.d("BitmapSize", bmp.getWidth() + " " + bmp.getHeight());
        setAnimatedSprite(Bitmap.createScaledBitmap(bmp,100 * 10,100* 10,true),bmp.getHeight() / 96,bmp.getWidth() / 96,12,0,0);



        //sprite.update(dt);
        MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
        if (motionEvent == null) return;


        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);

        if (currentID == -1 && (motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) ||
        motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            currentID = pointerId;
            if (MainGameScene.instance.ChangeCursorSpritei >= 0) render = true;
        } else if (currentID != -1 && (motionEvent.getAction() == MotionEvent.ACTION_POINTER_UP) ||
                motionEvent.getAction() == MotionEvent.ACTION_UP){



            //TODO: COLLISION
            if (MainGameScene.instance.Egg >= cursordrawable[MainGameScene.instance.ChangeCursorSpritei].cost ){
                if (MainGameScene.instance.ChangeCursorSpritei >= 0 && render){
                    for (int i = 0; i < gamescene._gameEntities.size();i++) {
                        if (gamescene._gameEntities.get(i) != this) {
                            if (gamescene._gameEntities.get(i) instanceof Holder) {
                                if (this.touching(gamescene._gameEntities.get(i))) {
                                    Log.d("TOUCHHOLDER","" + i);
                                    Holder temp = (Holder)gamescene._gameEntities.get(i);
                                    if (temp._mob == null){
                                        MainGameScene.instance.addVariable("Egg",-cursordrawable[MainGameScene.instance.ChangeCursorSpritei].cost);
                                        LivingEntity tempGE = null;
                                        switch (cursordrawable[MainGameScene.instance.ChangeCursorSpritei].entity){
                                            case CHICKEN:
                                                tempGE = new Chicken(temp.getPosition(),temp.getLayer());
                                                break;
                                            case LLAMA:
                                                tempGE = new Llama(temp.getPosition(),temp.getLayer());
                                                break;
                                            case SHEEP:
                                                tempGE = new Sheep(temp.getPosition(),temp.getLayer());
                                                break;
                                            case IRONGOLEM:
                                                tempGE = new Llama(temp.getPosition(),temp.getLayer());
                                                break;
                                        }
                                        temp._mob = tempGE;
                                        tempGE.onHolder = temp;
                                        gamescene._gameEntityCache.add(tempGE);
                                        break;
                                    }


                                }
                            }
                        }
                    }
                }
            }

            currentID = -1;
            render = false;
            MainGameScene.instance.ChangeCursorSpritei = -1;
        }
        if (currentID != -1){
            for (int i = 0; i < motionEvent.getPointerCount(); i++){
                if (motionEvent.getPointerId(i) == currentID){
                    _position = new Vector2(motionEvent.getX(i),motionEvent.getY(i));

                }
            }

        }
    }

    @Override
    public void onRender(Canvas canvas) {
        if(render)super.onRender(canvas);
    }
}

