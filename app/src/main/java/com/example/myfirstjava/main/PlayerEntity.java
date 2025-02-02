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

class ItemStructure{
    enum ItemType{
        SWORD,
        AXE,
        PICKAXE
    }

    enum LevelType{
        WOODEN,
        STONE,
        IRON,
        GOLD,
        DIAMOND
    }

    public ItemStructure(int id, ItemType item, LevelType level){
        this.id = id;
        this.itemType = item;
        this.levelType = level;
    }

    public ItemType itemType;
    public LevelType levelType;
    public int id;
}

public class PlayerEntity extends GameEntity {

    //private boolean _isHolding = false;
    private int currentID = -1;
    private boolean render = false;
    public ButtonStructure[] cursordrawable = new ButtonStructure[4];
    public ItemStructure[] itemCursorDrawable = new ItemStructure[15];
    public GameEntity _entityCache;

    public int currentEntity = -1;

    public PlayerEntity(){
        /*
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        _sprite = Bitmap.createScaledBitmap(bmp,200,200,true);
        */
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.skeletonattack);
        _sprite = Bitmap.createScaledBitmap(bmp,5,5,true);
        //setSize(new Vector2(5,5));
        //R.drawable.skeletonattack,4,5,5,9,12

        //setAnimatedSprite(Bitmap.createScaledBitmap(bmp,100 * 10,100* 10,true),bmp.getWidth() / 32,bmp.getWidth() / 32,12,0,0);
        setLayer(-1);
        cursordrawable[0] = new ButtonStructure(R.drawable.minibunny, ButtonStructure.type.CHICKEN,5);
        cursordrawable[1] = new ButtonStructure(R.drawable.miniwolf,ButtonStructure.type.LLAMA,10);
        cursordrawable[2] = new ButtonStructure(R.drawable.minibear,ButtonStructure.type.IRONGOLEM,15);
        cursordrawable[3] = new ButtonStructure(R.drawable.miniboar,ButtonStructure.type.SHEEP,15);

        itemCursorDrawable[14] = new ItemStructure(R.drawable.woodensword, ItemStructure.ItemType.SWORD, ItemStructure.LevelType.WOODEN);
        itemCursorDrawable[13] = new ItemStructure(R.drawable.woodenaxe, ItemStructure.ItemType.AXE, ItemStructure.LevelType.WOODEN);
        itemCursorDrawable[12] = new ItemStructure(R.drawable.woodenpickaxe, ItemStructure.ItemType.PICKAXE, ItemStructure.LevelType.WOODEN);

        itemCursorDrawable[11] = new ItemStructure(R.drawable.stonesword, ItemStructure.ItemType.SWORD, ItemStructure.LevelType.STONE);
        itemCursorDrawable[10] = new ItemStructure(R.drawable.stoneaxe, ItemStructure.ItemType.AXE, ItemStructure.LevelType.STONE);
        itemCursorDrawable[9] = new ItemStructure(R.drawable.stonepickaxe, ItemStructure.ItemType.PICKAXE, ItemStructure.LevelType.STONE);

        itemCursorDrawable[8] = new ItemStructure(R.drawable.ironsword, ItemStructure.ItemType.SWORD, ItemStructure.LevelType.IRON);
        itemCursorDrawable[7] = new ItemStructure(R.drawable.ironaxe, ItemStructure.ItemType.AXE, ItemStructure.LevelType.IRON);
        itemCursorDrawable[6] = new ItemStructure(R.drawable.ironpickaxe, ItemStructure.ItemType.PICKAXE, ItemStructure.LevelType.IRON);

        itemCursorDrawable[5] = new ItemStructure(R.drawable.goldsword, ItemStructure.ItemType.SWORD, ItemStructure.LevelType.GOLD);
        itemCursorDrawable[4] = new ItemStructure(R.drawable.goldaxe, ItemStructure.ItemType.AXE, ItemStructure.LevelType.GOLD);
        itemCursorDrawable[3] = new ItemStructure(R.drawable.goldpickaxe, ItemStructure.ItemType.PICKAXE, ItemStructure.LevelType.GOLD);

        itemCursorDrawable[2] = new ItemStructure(R.drawable.diamondsword, ItemStructure.ItemType.SWORD, ItemStructure.LevelType.DIAMOND);
        itemCursorDrawable[1] = new ItemStructure(R.drawable.diamondaxe, ItemStructure.ItemType.AXE, ItemStructure.LevelType.DIAMOND);
        itemCursorDrawable[0] = new ItemStructure(R.drawable.diamondpickaxe, ItemStructure.ItemType.PICKAXE, ItemStructure.LevelType.DIAMOND);

    }

    public void ChangeCursorSprite(){



    }


    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        Log.d("CCS", "" + MainGameScene.instance.ChangeCursorSpritei);
        Bitmap bmp = null;
        int amountOfCharacters = 4;
        //s
        if (MainGameScene.instance.ChangeCursorSpritei >= 0 && MainGameScene.instance.ChangeCursorSpritei < amountOfCharacters){
            bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(),cursordrawable[MainGameScene.instance.ChangeCursorSpritei].id );

            int tempsize = (int)getScreenHeight() / 5 * bmp.getHeight() / 96;
            setAnimatedSprite(Bitmap.createScaledBitmap(bmp,tempsize,tempsize,true),bmp.getHeight() / 96,bmp.getWidth() / 96,12,0,0);
            Log.d("BITMAPCURSOR","" + MainGameScene.instance.ChangeCursorSpritei + " " + _animatedSprite + " " + _sprite);
        }
        if(MainGameScene.instance.ChangeCursorSpritei >= amountOfCharacters){
            bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(),itemCursorDrawable[MainGameScene.instance.ChangeCursorSpritei -4].id );
            bmp = Bitmap.createScaledBitmap(bmp,100,100,true);
            setSprite(bmp);
            _animatedSprite = null;
            //setAnimatedSprite(Bitmap.createScaledBitmap(bmp,100 * 10,100* 10,true),1,1,12,0,0);
        }

        if (bmp == null) {
            return; // Handle the error appropriately
        }

        // Scale the bitmap and set as animated sprite
        //Log.d("BitmapSize", bmp.getWidth() + " " + bmp.getHeight());




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


//&& MainGameSurfaceView.instance.characteramounts[MainGameScene.instance.ChangeCursorSpritei] > 0
            //TODO: COLLISION
            if(MainGameScene.instance.ChangeCursorSpritei < amountOfCharacters ){
                if (MainGameScene.instance.Egg >= cursordrawable[MainGameScene.instance.ChangeCursorSpritei].cost && MainGameScene.instance.Planting && MainGameSurfaceView.instance.characteramounts[MainGameScene.instance.ChangeCursorSpritei] > 0){
                    if (MainGameScene.instance.ChangeCursorSpritei >= 0 && render){
                        for (int i = 0; i < gamescene._gameEntities.size();i++) {
                            if (gamescene._gameEntities.get(i) != this) {
                                if (gamescene._gameEntities.get(i) instanceof Holder) {
                                    if (this.touching(gamescene._gameEntities.get(i))) {
                                        Log.d("TOUCHHOLDER","" + i);
                                        Holder temp = (Holder)gamescene._gameEntities.get(i);
                                        if (temp._mob == null){
                                            MainGameSurfaceView.instance.characteramounts[MainGameScene.instance.ChangeCursorSpritei]--;

                                            LivingEntity tempGE = null;
                                            switch (cursordrawable[MainGameScene.instance.ChangeCursorSpritei].entity){
                                                case CHICKEN:
                                                    tempGE = new Chicken(temp.getPosition(),temp.getLayer());
                                                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.chicken);
                                                    break;
                                                case LLAMA:
                                                    tempGE = new Llama(temp.getPosition(),temp.getLayer());
                                                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.llama);
                                                    break;
                                                case SHEEP:
                                                    tempGE = new Sheep(temp.getPosition(),temp.getLayer());
                                                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.sheep);
                                                    break;
                                                case IRONGOLEM:
                                                    tempGE = new IronGolem(temp.getPosition(),temp.getLayer());
                                                    AudioClass.getInstance().PlaySFX(GameActivity.instance, R.raw.irongloem);
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

            }

            currentID = -1;
            render = false;
            MainGameScene.instance.ChangeCursorSpritei = -1;
            MainGameScene.instance.Planting = false;
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
        if(MainGameScene.instance.Planting)super.onRender(canvas);
    }
}

