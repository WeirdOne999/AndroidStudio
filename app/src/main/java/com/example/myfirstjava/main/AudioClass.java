package com.example.myfirstjava.main;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.myfirstjava.R;

public class AudioClass {
    private MediaPlayer _backgroundmusic;
    public static AudioClass instance;

    private AudioClass() {

    }

    public static AudioClass getInstance() {
        if (instance == null) {
            instance = new AudioClass();
        }
        return instance;
    }

    public void PlayAudio(Context context, int Audio, boolean looping) {
        if (_backgroundmusic != null) {
            _backgroundmusic.release();
        }
        _backgroundmusic = MediaPlayer.create(context, Audio);
        if (_backgroundmusic == null) {
            throw new IllegalStateException("Failed to initialize MediaPlayer");
        }
        _backgroundmusic.setLooping(looping);
        _backgroundmusic.start();
    }

    public void StopAudio() {
        if (_backgroundmusic != null) {
            if (_backgroundmusic.isPlaying()) {
                _backgroundmusic.stop();
            }
            _backgroundmusic.release();
            _backgroundmusic = null;
        }
    }
}
