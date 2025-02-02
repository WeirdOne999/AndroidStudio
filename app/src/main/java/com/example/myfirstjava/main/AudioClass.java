package com.example.myfirstjava.main;

import android.content.Context;
import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.List;

public class AudioClass {
    private MediaPlayer _backgroundMusic;
    private final List<MediaPlayer> _sfxPlayers = new ArrayList<>();
    public static AudioClass instance;

    private AudioClass() {}

    public static AudioClass getInstance() {
        if (instance == null) {
            instance = new AudioClass();
        }
        return instance;
    }


    public void PlayBackgroundMusic(Context context, int audioResId, boolean looping) {
        if (_backgroundMusic != null) {
            _backgroundMusic.release();
        }
        _backgroundMusic = MediaPlayer.create(context, audioResId);
        if (_backgroundMusic == null) return;
        _backgroundMusic.setLooping(looping);
        _backgroundMusic.start();
    }


    public void PlaySFX(Context context, int audioResId) {
        MediaPlayer sfx = MediaPlayer.create(context, audioResId);
        if (sfx == null) return;
        _sfxPlayers.add(sfx);
        sfx.setOnCompletionListener(mp -> {
            mp.release();
            _sfxPlayers.remove(mp);
        });
        sfx.start();
    }


    public void StopBackgroundMusic() {
        if (_backgroundMusic != null) {
            _backgroundMusic.stop();
            _backgroundMusic.release();
            _backgroundMusic = null;
        }
    }


    public void StopAllSFX() {
        for (MediaPlayer sfx : _sfxPlayers) {
            if (sfx.isPlaying()) {
                sfx.stop();
            }
            sfx.release();
        }
        _sfxPlayers.clear();
    }
}
