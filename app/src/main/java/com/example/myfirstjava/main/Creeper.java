package com.example.myfirstjava.main;

import android.content.Context;
import android.graphics.Canvas;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class Creeper extends EnemyEntity {

    Context context;
    public Creeper(Vector2 pos, int layer){

        super(100,1,0.375f);
        context = GameActivity.instance;
        isEnemy=true;
        setLayer(layer);
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.slimewalk,4,8,5,8*4,12);
        attack.setAnimationHolder(R.drawable.slimeattack,4,4,4,7,12);
        idle.setAnimationHolder(R.drawable.zombieidlenew,1,19,0,18,12);
        SetSprite(walk);
        alive();
        int size = 100;
        setSize(new Vector2(size,size));

    }

    public void ON(Vector2 pos, int layer){
        super.ON(150,1,0.375f/2);
        context = GameActivity.instance;
        isEnemy=true;
        setLayer(layer);
        setPosition(pos);
        walk.setAnimationHolder(R.drawable.slimewalk,4,4,2,5,12);
        attack.setAnimationHolder(R.drawable.slimeattack,4,4,4,7,12);
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
                ge.SetHealth(ge.getHealth() - 100);
                gameScene._gameEntityCache.add(AreaDamagePool.acquire(3,this._position,75 * UseItem(gameScene),false));
                destroy();
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
    public void onRender(Canvas canvas) {
        super.onRender(canvas);
    }


}
