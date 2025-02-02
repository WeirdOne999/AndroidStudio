package com.example.myfirstjava.mgp2d.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.myfirstjava.main.CollisionEntity;

import android.util.Log;
import android.view.MotionEvent;

public abstract class GameEntity {

    public Bitmap overSprite;
    public Bitmap _sprite;
    protected AnimatedSprite  _animatedSprite;
    protected Vector2 _position = new Vector2(0, 0);
    public Vector2 getPosition() { return _position.copy(); }
    public void setPosition(Vector2 position) { _position = position; }

    protected int _ordinal = 0;
    public int getOrdinal() { return _ordinal; }

    private boolean _isDone = false;
    public void alive() { _isDone = false;}
    public void destroy() { _isDone = true; }
    public boolean canDestroy() { return _isDone; }

    public void setSprite(Bitmap bmp) {_sprite = bmp;}

    private Rect _srcRect;
    private Rect _dstRect;
    public void setAnimatedSprite(Bitmap bmp,int row,int col, int fps,int start,int end) {
        _animatedSprite = new AnimatedSprite(bmp,row,col,fps,start,end);
        _sprite = bmp;
    }

    public boolean canTap = false;

    public void onUpdate(float dt, GameScene gamescene) {
        //Log.d("CHIKCENTEST","GAMEENTITY");
        if (_animatedSprite != null) _animatedSprite.update(dt);

        if (_sprite != null && canTap) {
            MotionEvent motionEvent = GameActivity.instance.getMotionEvent();
            if (motionEvent == null) return;
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                float tapPositionX = motionEvent.getX();
                float tapPositionY = motionEvent.getY();

                float spriteX = getPosition().x; // X position of the sprite
                float spriteY = getPosition().y; // Y position of the sprite
                float spriteWidth = _sprite.getWidth(); // Width of the sprite
                float spriteHeight = _sprite.getHeight(); // Height of the sprite
                //Log.d("TAPPOS", "Tap Position: " + tapPositionX + " " + tapPositionY + " SPR: " + spriteX + " " + spriteY + " SPRI_SIZE:" + spriteWidth +  " " + spriteHeight);
                if (tapPositionX >= spriteX- spriteWidth / 2 && tapPositionX <= (spriteX + spriteWidth / 2) &&
                        tapPositionY >= spriteY- spriteHeight/ 2 && tapPositionY <= (spriteY + spriteHeight/ 2)) {
                    onTap();
                }
            }
        }





// Check if the tap is within the sprite's bounds

    }

    public float getScreenWidth() {return GameActivity.instance.getResources().getDisplayMetrics().widthPixels;}
    public float getScreenHeight() {return GameActivity.instance.getResources().getDisplayMetrics().heightPixels;}

    public void onTap(){
    }


    public void onRender(Canvas canvas) {
        if (_sprite != null && _animatedSprite == null){
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
    private int _layer = -1;
    public void setLayer(int layer) {_layer = layer;}

    public int getLayer(){return _layer;}

    protected Vector2 _size = new Vector2(0, 0);
    public Vector2 getSize() { return _size.copy(); }
    public void setSize(Vector2 size) { _size = size; }


    public boolean isColliding(GameEntity collider) {
        if(getLayer() != collider.getLayer()) return false;
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

    public boolean cheaperIsColliding(GameEntity collider){
        if(getLayer() != collider.getLayer()) return false;
        float Left = getPosition().x - getSize().x / 2;
        float Right = getPosition().x + getSize().x / 2;

        float cLeft = collider.getPosition().x - collider.getSize().x / 2;
        float cRight = collider.getPosition().x + collider.getSize().x / 2;
        return Left < cRight && cLeft < Right;
    }

    public boolean touching(GameEntity collider) {
        float Left = getPosition().x - getSize().x / 2;
        float Right = getPosition().x + getSize().x / 2;
        float Top = getPosition().y - (getSize().y / 2);
        float Btm = getPosition().y + getSize().y / 2;

        float cLeft = collider.getPosition().x - collider.getSize().x / 2;
        float cRight = collider.getPosition().x + collider.getSize().x / 2;
        float cTop = collider.getPosition().y - collider.getSize().y / 2;
        float cBtm = collider.getPosition().y + collider.getSize().y / 2;

        return Left < cRight && cLeft < Right && Top < cBtm && cTop < Btm;
    }



}
