package com.example.myfirstjava.main;

import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.Vector2;

public class CollisionEntity extends GameEntity {
    private int _layer = 0;
    public void setLayer(int layer) {_layer = layer;}

    public int getLayer(){return _layer;}

    protected Vector2 _size = new Vector2(0, 0);
    public Vector2 getSize() { return _size.copy(); }
    public void setSize(Vector2 size) { _size = size; }


    public boolean isColliding(CollisionEntity collider) {
        float Left = getPosition().x - getSize().x / 2;
        float Right = getPosition().x + getSize().x / 2;
        float Top = getPosition().y - (getSize().y / 2);
        float Btm = getPosition().y + getSize().y / 2;

        float cLeft = collider.getPosition().x - collider.getSize().x / 2;
        float cRight = collider.getPosition().x + collider.getSize().x / 2;
        float cTop = collider.getPosition().y - collider.getSize().y / 2;
        float cBtm = collider.getPosition().y + collider.getSize().y / 2;

        return Left < cRight && cLeft < Right && Top < cBtm && cTop < cBtm && getLayer() == collider.getLayer();
    }

}
