package classes.Asset.Audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
    private static final float DEFAULT_VOLUME = -15;
    private Clip stream;

    public Audio(String filePath) {
        try {
            stream = AudioSystem.getClip();
            stream.open(AudioSystem.getAudioInputStream(Audio.class.getResourceAsStream("../../../assets/sounds/" + filePath)));
            ((FloatControl) stream.getControl(FloatControl.Type.MASTER_GAIN)).setValue(DEFAULT_VOLUME);
        } catch (Exception e) {
            System.err.println("[AUDIO LOADER] Error at opening audio file" + filePath + " with Error Message:\n" + e.getMessage());
            stream = null;
        }
    }

    public Audio play() {
        if (stream == null) return this;
        stream.stop();
        stream.setMicrosecondPosition(0);
        stream.start();
        return this;
    }

    public Audio stop() {
        if (stream == null) return this;
        stream.stop();
        return this;
    }

    public Audio setVolume(float decibels) {
        if (stream == null) return this;
        ((FloatControl) stream.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibels);
        return this;
    }

    public void close() {
        stream.close();
    }
}