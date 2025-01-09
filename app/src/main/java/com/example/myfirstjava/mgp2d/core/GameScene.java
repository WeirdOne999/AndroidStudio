package com.example.myfirstjava.mgp2d.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.main.Holder;

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
                _gameEntities.remove(i);
            }
        }

        for (GameEntity i : _gameEntities){
            if (i == null ) continue;
            i.onUpdate(dt,this);
        }
        //Log.d("TAPPOS","END");
        for (int i = _gameEntityCache.size() - 1;i >= 0; i--){
            _gameEntities.add(_gameEntityCache.get(i));
            _gameEntityCache.remove(i);
        }
        Log.d("TEST","FPS: " + _fps + " GE: " + _gameEntities.size() + " GEC: " + _gameEntityCache.size());

        if( GameActivity.instance.areSensorsWorking()){
            Log.d("ACCEL", String.valueOf(GameActivity.instance.getSensorEvent().values[1] * 10));
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
