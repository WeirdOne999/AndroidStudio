package com.example.myfirstjava.mgp2d.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class GameEntity {
    protected Bitmap _sprite;
    protected Vector2 _position = new Vector2(0, 0);
    public Vector2 getPosition() { return _position.copy(); }
    public void setPosition(Vector2 position) { _position = position; }

    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }

    private boolean _isDone = false;
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void setSprite(Bitmap bmp) {_sprite = bmp;}

    public void onUpdate(float dt, GameScene gamescene) {}


    public void onRender(Canvas canvas) {
        canvas.drawBitmap(_sprite, _position.x - (float) _sprite.getWidth() / 2, _position.y - (float)
                _sprite.getHeight() / 2 , null);
    };

}
