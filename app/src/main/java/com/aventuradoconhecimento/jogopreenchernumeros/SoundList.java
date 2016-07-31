package com.aventuradoconhecimento.jogopreenchernumeros;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

public class SoundList {

    private android.media.SoundPool soundPool = null;
    private HashMap mSounds = new HashMap();
    private int selectedSound = 0;
    private int streamId;
    private boolean loaded;
    private int loop;


    public SoundList(Context context) {
        AudioAttributes audioAttrib = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new android.media.SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttrib)
                    .build();
        } else {
            this.soundPool = createSoundPool();
        }

        soundPool.setOnLoadCompleteListener(new android.media.SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(android.media.SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        mSounds.put(R.raw.dice_shake, soundPool.load(context, R.raw.dice_shake, 1));
        mSounds.put(R.raw.dice_throw, soundPool.load(context, R.raw.dice_throw, 2));
        mSounds.put(R.raw.elephant, soundPool.load(context, R.raw.elephant, 3));
        mSounds.put(R.raw.error, soundPool.load(context, R.raw.error, 3));
    }

    public void setSoundResource(int soundResource, boolean loop) {
        selectedSound = (int) mSounds.get(soundResource);
        this.loop = loop ? -1 : 0;
    }

    public void setSoundResource(int soundResource) {
        setSoundResource(soundResource, false);
    }

    public void play() {
        if (loaded) {
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

    private SoundPool createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return createNewSoundPool();
        } else {
            return createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private SoundPool createOldSoundPool(){
        return new SoundPool(5,AudioManager.STREAM_MUSIC,0);
    }
}