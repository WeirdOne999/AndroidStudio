package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Enderman extends EnemyEntity {

    Context context;
    public int invul = 2;
    public Enderman(Vector2 pos, int layer){

        super(250,3,0.375f);
        context = GameActivity.instance;
        isEnemy=true;
        setLayer(layer);
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.endermanwalk,4,4,4,7,12);
        attack.setAnimationHolder(R.drawable.endermanattack ,4,5,5,9,12);
        idle.setAnimationHolder(R.drawable.zombieidlenew,1,19,0,18,12);
        SetSprite(walk);
        alive();
        int size = 100;
        setSize(new Vector2(size,size));

    }

    public void ON(Vector2 pos, int layer){
        super.ON(250,3,0.375f);
        context = GameActivity.instance;
        isEnemy=true;
        setLayer(layer);
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.endermanwalk,4,4,4,7,12);
        attack.setAnimationHolder(R.drawable.endermanattack ,4,5,5,9,12);
        idle.setAnimationHolder(R.drawable.zombieidlenew,1,19,0,18,12);
        SetSprite(walk);
        alive();
        int size = 100;
        setSize(new Vector2(size,size));
    }

    @Override
    public void attack(float dt, GameScene gameScene, LivingEntity ge) {
        super.attack(dt, gameScene, ge);
        currentState = State.ATTACK;
        _animatedSprite.setLopping(false);
        //Log.d("ATTACKCHECK",""+_animatedSprite.hasFinished() + currentState.toString());
        currentspeed = 0;
        if (currentState == State.ATTACK && prevState == State.ATTACK){
            if (_animatedSprite.hasFinished()){
                ge.SetHealth(ge.getHealth() - 10);
                currentState = State.WALK;
            }
        }

    }

    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);

        if(touchedPlant != null){
            //ATTACK
            //Log.d("ZOMBTOUCHPLANT" , "HEALTH:  " + touchedPlant.getHealth());
            attack(dt,gamescene,touchedPlant);
            //AudioClass.getInstance().PlaySFX(context, R.raw.punch);
        }else{
            if (currentState == State.ATTACK){
                currentState = State.WALK;
            }
        }
    }

    @Override
    public void lostHealth(){
        if (invul > 0){
            invul--;
            
        }
    }

    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }


}
