package com.example.myfirstjava.main;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

import java.util.Vector;

public class MainGameScene extends GameScene {

    public static MainGameScene instance = null;

    public GameEntity player;

    public int screenWidth;
    public int screenHeight;

    private Vector2 gridOffset = new Vector2(750,350);
    Holder[][] HolderArr = new Holder[9][5];
    @Override
    public void onCreate() {    
        super.onCreate();
        screenWidth = GameActivity.instance.getResources().getDisplayMetrics().widthPixels;
        screenHeight = GameActivity.instance.getResources().getDisplayMetrics().heightPixels;

        gridOffset = new Vector2(screenWidth / 2.9f,screenHeight / 3.2f);

        _gameEntities.add(new BackgroundEntity(R.drawable.netherback,0));

        //Create Grid Holders
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 5; j++){

                float spacing = 100;
                int size = (int) spacing + 5;
                HolderArr[i][j] = new Holder(new Vector2(i * (screenWidth / 19) + gridOffset.x,j * (screenHeight / 11) + gridOffset.y),size,j);
                _gameEntities.add(HolderArr[i][j]);
            }
        }

        player =new PlayerEntity();

        player.setLayer(1);
        player.setSize(new Vector2(170,170));
        _gameEntities.add(player);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 5; j++){
                _gameEntities.add(new Chicken(HolderArr[i][j].getPosition(),j));
            }
        }
        _gameEntities.add(new Pause());
        //_gameEntities.add(new PhysicsEntity(1));
    }

    @Override
    public void onUpdate(float dt) {
        //Log.d("SCENESIZE", "SIZE OF ARRAY: " + _gameEntities.size());
        super.onUpdate(dt);






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
    public void onRender(Canvas canvas) {
        super.onRender(canvas);


    }
}
