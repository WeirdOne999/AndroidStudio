package com.example.myfirstjava.mgp2d.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myfirstjava.main.CollisionEntity;

public abstract class GameEntity {
    protected Bitmap _sprite;
    protected AnimatedSprite  _animatedSprite;
    protected Vector2 _position = new Vector2(0, 0);
    public Vector2 getPosition() { return _position.copy(); }
    public void setPosition(Vector2 position) { _position = position; }

    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }

    private boolean _isDone = false;
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void setSprite(Bitmap bmp) {_sprite = bmp;}

    private Rect _srcRect;
    private Rect _dstRect;
    public void setAnimatedSprite(Bitmap bmp,int row,int col, int fps) {
        _animatedSprite = new AnimatedSprite(bmp,row,col,fps);
    }

    public void onUpdate(float dt, GameScene gamescene) {
        if (_animatedSprite != null) _animatedSprite.update(dt);
    }


    public void onRender(Canvas canvas) {
        if (_sprite != null){
            /*
            _dstRect.left = (int)_position.x - _sprite.getWidth() / 7 / 2;
            _dstRect.top = (int)_position.y - _sprite.getHeight() / 2;
            _dstRect.right = (int) _position.x + _sprite.getWidth() / 7/2;
            _dstRect.bottom = (int) _position.y + _sprite.getHeight() / 2;

            canvas.drawBitmap(_sprite,_srcRect,_dstRect,null);
            */
            canvas.drawBitmap(_sprite, _position.x - (float) _sprite.getWidth() / 2, _position.y - (float)
                    _sprite.getHeight() / 2 , null);
        }
        if (_animatedSprite != null){
            _animatedSprite.render(canvas,(int) _position.x,(int)_position.y,null);
        }
    };


    //Collision Stuff
    private int _layer = 0;
    public void setLayer(int layer) {_layer = layer;}

    public int getLayer(){return _layer;}

    protected Vector2 _size = new Vector2(0, 0);
    public Vector2 getSize() { return _size.copy(); }
    public void setSize(Vector2 size) { _size = size; }


    public boolean isColliding(GameEntity collider) {
        float Left = getPosition().x - getSize().x / 2;
        float Right = getPosition().x + getSize().x / 2;
        float Top = getPosition().y - (getSize().y / 2);
        float Btm = getPosition().y + getSize().y / 2;

        float cLeft = collider.getPosition().x - collider.getSize().x / 2;
        float cRight = collider.getPosition().x + collider.getSize().x / 2;
        float cTop = collider.getPosition().y - collider.getSize().y / 2;
        float cBtm = collider.getPosition().y + collider.getSize().y / 2;

        return Left < cRight && cLeft < Right && Top < cBtm && cTop < Btm && getLayer() == collider.getLayer();
    }



}
