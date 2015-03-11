package capture;

import javax.sound.sampled.AudioInputStream;

public interface VoiceClipListener {

	/**
	 * 捕捉到的音频�?
	 * 
	 * @param ais
	 */
	public void captureClip(AudioInputStream clipAIS);
}
