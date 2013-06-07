/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class Sound {

    boolean isPause = false;
    boolean isLoop = false;
    int pausePosition = 0;
    Clip clip;

    public Sound(String fileName, boolean isLoop) {
        this.isPause = false;
        this.isLoop = isLoop;

        AudioInputStream audioIn = null;

        try {
            audioIn = AudioSystem.getAudioInputStream(ResourceManager.class.getResource(fileName));
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } catch (Exception ex) {
            System.out.println("Can not open sound: " + fileName);
        } finally {
            try {
                audioIn.close();
            } catch (IOException ex) {
                System.out.println("Can not close sound: " + fileName);
            }
        }
    }

    public void close() {
        clip.close();
    }

    public void play() {
        if (isLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        clip.start();
        clip.setMicrosecondPosition(0);
    }

    public void stop() {
        clip.stop();
    }

    public void pause() {
        pausePosition = clip.getFramePosition();
        clip.stop();

        isPause = true;
    }

    public void resume() {
        if (isPause) {
            if (isLoop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();
            clip.setFramePosition(pausePosition);

            isPause = false;
        }
    }

    public void setVolume(int gainAmount) {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(gainAmount);
    }
}
