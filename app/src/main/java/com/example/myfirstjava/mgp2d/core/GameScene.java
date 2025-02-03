package com.example.myfirstjava.mgp2d.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.main.AreaDamage;
import com.example.myfirstjava.main.AreaDamagePool;
import com.example.myfirstjava.main.Arrow;
import com.example.myfirstjava.main.ArrowFactory;
import com.example.myfirstjava.main.ArrowPool;
import com.example.myfirstjava.main.BackgroundEntity;
import com.example.myfirstjava.main.Chicken;
import com.example.myfirstjava.main.ChickenPool;
import com.example.myfirstjava.main.Egg;
import com.example.myfirstjava.main.EggPool;
import com.example.myfirstjava.main.Holder;
import com.example.myfirstjava.main.House;
import com.example.myfirstjava.main.IronGolem;
import com.example.myfirstjava.main.IronGolemPool;
import com.example.myfirstjava.main.Llama;
import com.example.myfirstjava.main.LlamaPool;
import com.example.myfirstjava.main.LlamaSpit;
import com.example.myfirstjava.main.LlamaSpitPool;
import com.example.myfirstjava.main.Minecart;
import com.example.myfirstjava.main.PlayerEntity;
import com.example.myfirstjava.main.Sheep;
import com.example.myfirstjava.main.SheepPool;
import com.example.myfirstjava.main.Skeleton;
import com.example.myfirstjava.main.SkeletonPool;
import com.example.myfirstjava.main.Zombie;
import com.example.myfirstjava.main.ZombiePool;

import java.util.HashMap;
import java.util.Vector;

public abstract class GameScene {
    // region Static props for managing multiple scenes

    private static GameScene _current = null;

    private static GameScene _next = null;

    public static GameScene getCurrent() { return _current; }

    public static GameScene getNext() { return _next; }

    private static final HashMap<Class<?>, GameScene> map = new HashMap<>();
    public Holder[][] HolderArr = new Holder[9][5];

    private int _frameCount;
    private long _lastTime;
    public float _fps;

    public float lastAccel = 0;
    public float currentAccel = 0;

    public static void enter(Class<?> gameSceneClass) {
        if (!map.containsKey(gameSceneClass)) {
            try {
                map.put(gameSceneClass, (GameScene) gameSceneClass.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        _next = map.get(gameSceneClass);
    }

    public static void enter(GameScene gameScene) {
        if (_current != null) _current.onExit();
        _current = gameScene;
        if (_current == null) return;
        _current.onEnter();
    }

    public static void exitCurrent() {
        if (_current == null) return;
        _current.onExit();
        _current = null;
    }

    // endregion

    // region Props for handling internal behaviour of game scene

    private boolean _isCreated = false;
    public void onCreate() { _isCreated = true; }
    public void onEnter() { if (!_isCreated) onCreate(); }
    public void onUpdate(float dt)
    {
        _frameCount++;
        long currentTime = System.currentTimeMillis();
        if(currentTime - _lastTime > 1000) {
            _fps = (_frameCount * 1000.f) / (currentTime - _lastTime);
            _lastTime = currentTime;
            _frameCount = 0;
        }
        //Log.d("TAPPOS","START");
        for (int i = _gameEntities.size() - 1;i >= 0; i--){
            if (_gameEntities.get(i).canDestroy()){
                if(_gameEntities.get(i) instanceof Zombie){
                    ZombiePool.release((Zombie)_gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof Skeleton){
                    SkeletonPool.release((Skeleton)_gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof Arrow){
                    ArrowPool.release((Arrow) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof Egg){
                    EggPool.release((Egg) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof Sheep){
                    SheepPool.release((Sheep) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof Chicken){
                    ChickenPool.release((Chicken) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof Llama){
                    LlamaPool.release((Llama) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof IronGolem){
                    IronGolemPool.release((IronGolem) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof LlamaSpit){
                    LlamaSpitPool.release((LlamaSpit) _gameEntities.get(i));
                }
                else if(_gameEntities.get(i) instanceof AreaDamage){
                    AreaDamagePool.release((AreaDamage) _gameEntities.get(i));
                }
                _gameEntities.remove(i);
            }
        }
        // TODO UNCOMMENT THIS.UNCOMMENTEDALR
        for (GameEntity i : _gameEntities){
            if (i == null ) continue;
            i.onUpdate(dt,this);
            Log.d("ITEM", "" + i);
        }

        //Log.d("TAPPOS","END");
        for (int i = _gameEntityCache.size() - 1;i >= 0; i--){
            _gameEntities.add(_gameEntityCache.get(i));
            _gameEntityCache.remove(i);
        }
        Log.d("TEST","FPS: " + _fps + " GE: " + _gameEntities.size() + " GEC: " + _gameEntityCache.size());

        if( GameActivity.instance.areSensorsWorking()){
            currentAccel = (GameActivity.instance.getSensorEvent().values[1]) - lastAccel;
            Log.d("ACCEL", String.valueOf(currentAccel));
            lastAccel = GameActivity.instance.getSensorEvent().values[1];
        }
    }
    public void onRender(Canvas canvas){
        canvas.drawColor(Color.parseColor("#b2d4ff"));
        for (GameEntity i : _gameEntities){
             i.onRender(canvas);
        }
    }

    public void addVariable(String addVaraible, int i){

    }
    public void onExit() {}

    public Vector<GameEntity> _gameEntities = new Vector<>();
    public Vector<GameEntity> _gameEntityCache = new Vector<>();

    //endregion
}
