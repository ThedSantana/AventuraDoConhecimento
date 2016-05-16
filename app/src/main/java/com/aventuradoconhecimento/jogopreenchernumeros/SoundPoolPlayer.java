package com.aventuradoconhecimento.jogopreenchernumeros;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundPoolPlayer {

    private SoundPool soundPool = null;
    private HashMap mSounds = new HashMap();
    private int selectedSound = 0;
    private int streamId;
    private boolean loaded;


    public SoundPoolPlayer(Context context) {
        AudioAttributes audioAttrib = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttrib)
                    .build();
        } else {
            this.soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        mSounds.put(R.raw.dice_shake, soundPool.load(context, R.raw.dice_shake, 1));
        mSounds.put(R.raw.dice_throw, soundPool.load(context, R.raw.dice_throw, 1));
    }

    public void setSoundResource(int soundResource) {
        selectedSound = (int) mSounds.get(soundResource);
    }

    public void play() {
        if (loaded) {
            int loop = selectedSound == (int) mSounds.get(R.raw.dice_shake) ? -1 : 0;

            streamId = soundPool.play(selectedSound, 0.99f, 0.99f, 0, loop, 1);
        }
    }

    public void play(int soundResource) {
        setSoundResource(soundResource);
        play();
    }
    
    public void stop() {
        soundPool.stop(streamId);
    }

    public void release() {
        soundPool.release();
        soundPool = null;
    }
}