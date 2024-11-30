package com.example.myfirstjava.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.service.notification.ZenPolicy;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.util.Random;
import java.util.Vector;

public class MainGameScene extends GameScene {

    public static MainGameScene instance = null;

    public GameEntity player;

    public int screenWidth;
    public int screenHeight;

    private Vector2 gridOffset = new Vector2(750,350);
    Holder[][] HolderArr = new Holder[9][5];

    private float _enemySpawnTimer = 5;
    private float _totalEnemyTimer = 0;

    public int Egg;
    Text text_FPS;
    Text text_eggCount;
    @Override
    public void onCreate() {    
        super.onCreate();
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

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
                HolderArr[i][j] = new Holder(new Vector2(i * (screenWidth / 19) + gridOffset.x,j * (screenHeight / 11) + gridOffset.y),size,j);
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
        for (int i = 0; i < 1; i++){
            for (int j = 0; j < 5; j++){
                _gameEntities.add(new Chicken(HolderArr[i][j].getPosition(),j));
            }
        }
        _gameEntities.add(new Llama(HolderArr[1][1].getPosition(),1));

        _gameEntities.add(new Pause());
        text_FPS = new Text(R.color.teal_200,75, Paint.Align.RIGHT);
        _gameEntities.add(text_FPS);
        text_eggCount = new Text(R.color.teal_200,75,Paint.Align.LEFT);
        _gameEntities.add(text_eggCount);
        //_gameEntities.add(new PhysicsEntity(1));
    }



    @Override
    public void onUpdate(float dt) {
        //Log.d("SCENESIZE", "SIZE OF ARRAY: " + _gameEntities.size());
        super.onUpdate(dt);
        text_FPS.setText("FPS: " + (int)_fps,new Vector2(screenWidth - 100,screenHeight - 100));
        text_eggCount.setText("EGG: " + Egg,new Vector2(100,100));

        _totalEnemyTimer += dt;

        if (_totalEnemyTimer > _enemySpawnTimer + new Random().nextInt(5) - 2){
            _totalEnemyTimer = 0;

            int random  = new Random().nextInt(2);
            int layer = new Random().nextInt(5);
            if (random == 0){
                _gameEntityCache.add(new Skeleton(new Vector2(screenWidth,HolderArr[8][layer].getPosition().y) ,layer));

            }
            else if (random == 1){
                _gameEntityCache.add(new Zombie(new Vector2(screenWidth,HolderArr[8][layer].getPosition().y) ,layer));

            }
        }








        /*
        if (motionEvent == null) return;
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !BackDialog.isShowing()){
            BackDialog backDialog = new BackDialog();
            backDialog.show(GameActivity.instance.getSupportFragmentManager(),"Back dialog");
        }
         */
        /*
        for (GameEntity i: _gameEntities){
            if (i == player) continue;
            if (player.isColliding(i)){
                i.destroy();
            }
        }
        */


    }

    @Override
    public void addVariable(String addVaraible, int i) {
        super.addVariable(addVaraible, i);
        if (addVaraible.equals("Egg")){
            this.Egg += i;
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);

    }
}
