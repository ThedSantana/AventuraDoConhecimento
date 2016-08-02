package br.com.lealweb.aventuradoconhecimento.jogomemoria;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundManager {

	private static AudioManager mAudioManager;
	private static SoundPool mSoundPool;
	private static int SOUND_ID_FLIP_CARD;
	private static int SOUND_ID_CORRECT;
	private static int SOUND_ID_GAME_DONE;
	private static int SOUND_ID_INCORRECT;

	public static synchronized void initSounds(Context context) {
		if (mAudioManager != null)
			return;

		mSoundPool = createSoundPool();
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		SOUND_ID_FLIP_CARD = mSoundPool.load(context, R.raw.flip_card, 1);
		SOUND_ID_INCORRECT = mSoundPool.load(context, R.raw.incorrect, 1);
		SOUND_ID_CORRECT = mSoundPool.load(context, R.raw.correct, 1);
		SOUND_ID_GAME_DONE = mSoundPool.load(context, R.raw.game_done, 1);
	}

	public static void playClick() {
		SoundManager.playSoundOnce(SoundManager.SOUND_ID_FLIP_CARD);
	}

	public static void playCorrect() {
		SoundManager.playSoundOnce(SoundManager.SOUND_ID_CORRECT);
	}

	public static void playGameDone() {
		SoundManager.playSoundOnce(SoundManager.SOUND_ID_GAME_DONE);
	}

	public static void playIncorrect() {
		SoundManager.playSoundOnce(SoundManager.SOUND_ID_INCORRECT);
	}

	private static int playSoundOnce(int soundId) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		return mSoundPool.play(soundId, streamVolume, streamVolume, 1, 0, 1f);
	}

	private static SoundPool createSoundPool() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return createNewSoundPool();
		} else {
			return createOldSoundPool();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private static SoundPool createNewSoundPool(){
		AudioAttributes attributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
				.build();
		return new SoundPool.Builder()
				.setAudioAttributes(attributes)
				.build();
	}

	@SuppressWarnings("deprecation")
	private static SoundPool createOldSoundPool(){
		return new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
	}
}
