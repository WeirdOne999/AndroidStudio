package com.example.myfirstjava.main;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.service.notification.ZenPolicy;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class MainGameScene extends GameScene {


    public boolean canReset = false;
    public static MainGameScene instance = null;

    public GameEntity player;

    //Audio Conditions

    public int screenWidth;
    public int screenHeight;

    private Vector2 gridOffset = new Vector2(750,350);


    private float _enemySpawnTimer = 5;
    private float _totalEnemyTimer = 0;

    public int Egg;

    HashMap<String, Integer>material = new HashMap<>();
    public int Stone;
    public int Iron;
    public int Gold;
    public int Diamond;
    public int Wood;
    public int Birch_Wood;
    public int Oak_Wood;
    public int Pale_Wood;
    public int Crimson_Wood;
    Text text_FPS;
    Text text_eggCount;

    boolean won = false;
    boolean lost = false;

    public int ChangeCursorSpritei;
    public boolean Planting = false;

    Context context;
    @Override
    public void onCreate() {    
        super.onCreate();
        onEnter();
        material.put("Wood",100);
        material.put("BirchWood",100);
        material.put("OakWood",100);
        material.put("PaleWood",100);
        material.put("CrimsonWood",100);
        material.put("Stone",100);
        material.put("Iron",100);
        material.put("Gold",100);
        material.put("Diamond",100);
        MainGameSurfaceView.instance.createMaterialGrid();

    }

    @Override
    public void onEnter() {
        super.onEnter();
        context = GameActivity.instance;
        for (int i = _gameEntities.size() - 1;i >= 0; i--){
            //_gameEntities.get(i).destroy();
            _gameEntities.remove(i);
        }
        for (int i = _gameEntityCache.size() - 1;i >= 0; i--){
            //_gameEntityCache.get(i).destroy();
            _gameEntities.remove(i);
        }
        Egg = 200;
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        instance = this;
        gridOffset = new Vector2(screenWidth / 2.9f,screenHeight / 3.2f);

        _gameEntities.add(new BackgroundEntity(R.drawable.netherback,0));

        for (int j = 0; j < 5; j++){

            _gameEntities.add(new Floor(new Vector2(screenWidth/2,j * (screenHeight / 11) + gridOffset.y + 50),j));
        }

        _gameEntities.add(new Border(new Vector2(0,screenHeight / 2),1));
        _gameEntities.add(new Border(new Vector2(screenWidth,screenHeight / 2),1));

        //Create Grid Holders
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 5; j++){

                float spacing = screenHeight / 12;
                int size = (int) spacing + 5;
                HolderArr[i][j] = new Holder(new Vector2(i * (screenWidth / 19) + gridOffset.x,j * (screenHeight / 11) + gridOffset.y),1,j);
                _gameEntities.add(HolderArr[i][j]);
            }
        }

        for (int j = 0; j < 5; j++){

            float spacing = screenHeight / 12;
            _gameEntities.add(new Minecart(j,HolderArr[0][j].getPosition().add(new Vector2(-spacing,0))));
        }
        player =new PlayerEntity();
        player.setLayer(1);
        player.setSize(new Vector2(170,170));
        _gameEntities.add(player);
        //_gameEntities.add(new Llama(HolderArr[1][1].getPosition(),1));
        _gameEntities.add(new House(new Vector2(0 + (int)screenWidth / 5,(int)screenHeight/2),0));
        _gameEntities.add(new Pause());
        text_FPS = new Text(R.color.teal_200,75, Paint.Align.RIGHT);
        _gameEntities.add(text_FPS);
        text_eggCount = new Text(R.color.teal_200,75,Paint.Align.LEFT);
        _gameEntities.add(text_eggCount);
        won=false;
        lost=false;
        //_gameEntities.add(new PhysicsEntity(1));
        for(int i  = 0; i< 3;i++){
            //_gameEntities.add(new Zombie(new Vector2(screenWidth,HolderArr[8][0].getPosition().y) ,0));
        }
        AudioClass.getInstance().PlaySFX(context, R.raw.mobsspawn);
    }

    @Override
    public void onUpdate(float dt) {
        super.onUpdate(dt);
        Log.d("ARRSIZ", "After Array size: " + _gameEntities.size() + " " + _gameEntityCache.size());
        if(canReset){
            onEnter();
            canReset = false;
        }
        //Log.d("SCENESIZE", "SIZE OF ARRAY: " + _gameEntities.size());
        if (won || lost) return;

        text_FPS.setText("FPS: " + (int)_fps,new Vector2(screenWidth - 100,0 + 100));
        text_eggCount.setText("EGG: "  + Egg ,new Vector2(100,100));

        _totalEnemyTimer += dt;


        if (_totalEnemyTimer > _enemySpawnTimer + new Random().nextInt(5) - 2){
            _totalEnemyTimer = 0;

            int random  = new Random().nextInt(2);
            int layer = new Random().nextInt(5);
            if (random == 0){
                //_gameEntityCache.add(new Skeleton(new Vector2(screenWidth,HolderArr[8][layer].getPosition().y) ,layer));
                AudioClass.getInstance().PlaySFX(context, R.raw.skeletonsound);
            }
            else if (random == 1){
                //_gameEntityCache.add(new Zombie(new Vector2(screenWidth,HolderArr[8][layer].getPosition().y) ,layer));
                AudioClass.getInstance().PlaySFX(context, R.raw.zombiesound);
            }

        }
    }


    public void ChangeCursorSpriteIndex(int index){
        ChangeCursorSpritei = index;
    }
    @Override
    public void addVariable(String addVaraible, int i) {
        super.addVariable(addVaraible, i);
        if (addVaraible.equals("Egg")){
            this.Egg += i;
        }
        else {
            if (material.containsKey(addVaraible)){
                //CONTAINS
                material.put(addVaraible,material.get(addVaraible) + i);
            }else{
                //NO CONTAINS
                material.put(addVaraible,i);
            }
        }

    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);

    }

    public void wonLost(boolean wonLost){
        if (wonLost){
            won = true;
        }else{
            lost = true;

        }
    }
}
