package com.example.myfirstjava.main;

import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;

public class EggClass {

    private Drawable backgroundImage;
    private long hatchingDuration; // in milliseconds
    private long creationTime; // timestamp when the egg was created

    private long startHatchingDuration;
    private String Name;

    private boolean isHatched = false;
    private boolean hasPlayedSFX = false; // New flag
    public void reset() {
        this.hatchingDuration = startHatchingDuration;
        this.isHatched = false;
    }

    public EggClass(Drawable backgroundImage, long hatchingDuration, String name) {
        this.backgroundImage = backgroundImage;
        this.hatchingDuration = hatchingDuration;
        this.creationTime = System.currentTimeMillis();
        this.startHatchingDuration = hatchingDuration;
        this.Name = name;
    }

    public boolean hasPlayedSFX() {
        return hasPlayedSFX;
    }

    public void setPlayedSFX(boolean played) {
        this.hasPlayedSFX = played;
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
