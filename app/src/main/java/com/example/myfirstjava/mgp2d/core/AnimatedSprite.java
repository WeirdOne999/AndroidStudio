package com.example.myfirstjava.mgp2d.core;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class AnimatedSprite {
    private int _col;
    private int _row;
    private final int  _width;
    private final int _height;
    private int _currentFrame = 0;
    private int _startFrame;
    private int _endFrame;
    private final float _timePerFrame;
    public float _timeAccumulated = 0f;
    public Bitmap _bmp;
    private boolean _isLooping = true;

    private final Rect _src;
    private final Rect _dst;

    public Vector2 offset = new Vector2(0 ,0);

    public void set_timeAccumulated(float time){
        _timeAccumulated = time;
    }

    protected AnimatedSprite(Bitmap bitmap, int row, int col, int fps){
        _bmp = bitmap;
        _col = col;
        _row = row;
        _width = _bmp.getWidth() / _col;
        _height = _bmp.getHeight() / row;
        _timePerFrame = 1f/fps;
        //_endFrame  = _col * row;
        _src = new Rect();
        _dst = new Rect();
    }

    public AnimatedSprite(Bitmap bitmap, int row, int col, int fps, int startFrame, int endFrame){
        this(bitmap,row,col,fps);
        _currentFrame = startFrame;
        _startFrame = startFrame;
        _endFrame = endFrame;
    }

    public void setCurrentToZero(){_currentFrame = 0;}

    public AnimatedSprite(Bitmap bitmap, int row, int col, int fps, int startFrame, int endFrame, boolean current){
        this(bitmap,row,col,fps);
        if (!current)_currentFrame = startFrame;
        _startFrame = startFrame;
        _endFrame = endFrame;
    }

    public void setNew(int start, int end){
        _startFrame = start;
        _currentFrame = start;
        _endFrame = end;
    }

    public void setNewBitmap(Bitmap bitmap,int row, int col, int start, int end){
        _bmp = bitmap;
        _col = col;
        _row = row;
        _startFrame = start;
        _currentFrame = start;
        _endFrame = end;
    }

    public void setLopping(boolean shouldLoop) {_isLooping = shouldLoop;}

    public boolean hasFinished(){
        if (_isLooping) return false;
        return _currentFrame == _endFrame;
    }

    public void update(float dt){
        if (hasFinished()) return;
        _timeAccumulated += dt;
        if (_timeAccumulated > _timePerFrame){
            _currentFrame++;
            if (_currentFrame > _endFrame && _isLooping)
                _currentFrame = _startFrame;
            _timeAccumulated = 0f;
        }
    }

    public void render(Canvas canvas, int x, int y, Paint paint){
        int frameX = _currentFrame % _col;
        int frameY = _currentFrame / _row;
        int srcX = frameX * _width;
        int srcY = frameY * _height;

        x -= (int) (0.5f * _width);
        y -= (int) (0.5f * _height);

        _src.left = srcX;
        _src.top = srcY;
        _src.right = srcX + _width;
        _src.bottom = srcY + _height;

        _dst.left = x + (int)offset.x;
        _dst.top = y - (_height/4) + (int)offset.y;
        _dst.right = x + _width + (int)offset.x;
        _dst.bottom = y + (_height/4) * 3 + (int)offset.y;

        canvas.drawBitmap(_bmp,_src,_dst,paint);
    }
}
