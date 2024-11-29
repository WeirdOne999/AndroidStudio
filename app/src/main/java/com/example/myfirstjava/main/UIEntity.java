package com.example.myfirstjava.main;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;


//INTERFACE CLASS FOR MAINMENU FROM GAME SURFACE
public class UIEntity {
    private GameSurface gameSurface;

    public UIEntity(Context context, FrameLayout container, int backgroundId) {
        gameSurface = new GameSurface(context,container, backgroundId);
        container.addView(gameSurface); // Add the GameSurface to the provided container
    }

    public void setSize(int width, int height){
        gameSurface.setSize(width,height);
    }




    public void resume() {
        if (gameSurface != null) {
            gameSurface.resume();
        }
    }

    public void pause() {
        if (gameSurface != null) {
            gameSurface.pause();
        }
    }
}
