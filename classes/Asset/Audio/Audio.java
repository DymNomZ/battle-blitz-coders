package classes.Asset.Audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import static src.Utils.showError;

public class Audio {
    private Clip stream = null;
    private float volume = -15;

    public Audio play() {
        if (stream == null) return this;
        stream.stop();
        stream.setMicrosecondPosition(0);
        stream.start();
        return this;
    }

    public Audio load(String filePath) {
        if (stream != null) {
            stream.close();
        }

        Clip stream;

        try {
            stream = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            showError("[AUDIO] Player cannot be initialized: \n" + e.getMessage());
            return this;
        }

        try {
            stream.open(AudioSystem.getAudioInputStream(Audio.class.getResourceAsStream("../../../assets/sounds/" + filePath)));
            ((FloatControl) stream.getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);
        } catch (Exception e) {
            showError("[AUDIO LOADER] Error at opening audio file" + filePath + " with Error Message:\n" + e.getMessage());
            return this;
        }

        if (this.stream != null) {
            this.stream.close();
        }
        this.stream = stream;
        return this;
    }

    public Audio stop() {
        if (stream == null) return this;
        stream.stop();
        return this;
    }

    public Audio setVolume(float decibels) {
        if (stream == null) return this;
        volume = decibels;
        ((FloatControl) stream.getControl(FloatControl.Type.MASTER_GAIN)).setValue(decibels);
        return this;
    }

    public void close() {
        stream.close();
    }
}