package com.example.myfirstjava.main;

import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;

public class EggClass {

    private Drawable backgroundImage;
    private long hatchingDuration; // in milliseconds
    private long creationTime; // timestamp when the egg was created

    private String Name;
    public EggClass(Drawable backgroundImage, long hatchingDuration, String name) {
        this.backgroundImage = backgroundImage;
        this.hatchingDuration = hatchingDuration;
        this.creationTime = System.currentTimeMillis();
        this.Name = name;
    }
    public String getName(){

        return Name;
    }

    public Drawable getBackgroundImage() {
        return backgroundImage;
    }

    public long getHatchingDuration() {
        return hatchingDuration;
    }

    public long GetCreationTime(){
        return creationTime;
    }

    public boolean isHatched() {
        return System.currentTimeMillis() - creationTime >= hatchingDuration;
    }

    public String getRemainingTime() {
        long remainingTime = hatchingDuration - (System.currentTimeMillis() - creationTime);
        long seconds = remainingTime / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
