package com.example.myfirstjava.mgp2d.core;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Vector;

public abstract class GameScene {

    // region Static props for managing multiple scenes

    private static GameScene _current = null;

    private static GameScene _next = null;

    public static GameScene getCurrent() { return _current; }

    public static GameScene getNext() { return _next; }

    private static final HashMap<Class<?>, GameScene> map = new HashMap<>();

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
        for (GameEntity i : _gameEntities){
            i.onUpdate(dt,this);
        }
        /*
        for (int i = _gameEntities.size() - 1; i >= 0 ; i--){
            if (_gameEntities.get(i).canDestroy())
                _gameEntities.remove(i);
        }

         */
    }
    public abstract void onRender(Canvas canvas);
    public void onExit() {}

    public Vector<GameEntity> _gameEntities = new Vector<>();

    //endregion
}
