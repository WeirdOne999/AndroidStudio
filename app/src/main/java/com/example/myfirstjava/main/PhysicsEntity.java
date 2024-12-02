package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class PhysicsEntity extends GameEntity {



    private float force = 2;

    private float Gravity = 9.81f;
    private float velocityY = 0;
    private float posY;
    private float posX;

    private Vector2 velocity = new Vector2(0, 0);
    private Vector2 acceleration = new Vector2(0, 0);
    private float mass = 1.0f;
    private float dragCoefficient = 0.95f;
    public PhysicsEntity(){
        Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        _sprite = Bitmap.createScaledBitmap(bmp,200,200,true);
        setPosition(new Vector2(10,500));


    }

    public PhysicsEntity(int layer){
        setLayer(layer);
        //Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), R.drawable.pause);
        //_sprite = Bitmap.createScaledBitmap(bmp,200,200,true);
        //setPosition(new Vector2(10,500));
    }

    public void SetGravity(float dt){
        velocityY -= Gravity * dt;
        posY -= velocityY * dt;
        Vector2 finalpos = new Vector2(getPosition().x, getPosition().y + posY);
        setPosition(finalpos);

    }

    public void addImpulse(Vector2 impulse) {
        velocity.x += impulse.x / mass;  // Instantaneously update velocity
        velocity.y += impulse.y / mass;
    }

    // Simulate drag for deceleration
    private void applyDrag() {
        velocity.x *= dragCoefficient;  // Reduce velocity proportionally
        velocity.y *= dragCoefficient;
    }

    public void SetPositions(float dt){
        // Update velocity based on acceleration
        velocity.x += acceleration.x * dt;
        velocity.y += acceleration.y * dt;

        // Apply drag to slow down
        applyDrag();

        // Update position based on velocity
        _position.x += velocity.x * dt;
        _position.y += velocity.y * dt;
        setPosition(_position);
        // Reset acceleration after each frame
        acceleration.x = 0;
        acceleration.y = 0;
    }
    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt,gamescene);
        Log.d("CHIKCENTEST","PHYSISCS");
    }



}
