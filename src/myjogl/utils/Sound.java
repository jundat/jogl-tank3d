/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Jundat
 */
public class Sound {

    String fileName;
    boolean isPause = false;
    boolean isLoop = false;
    int pausePosition = 0;
    public Clip clip;

    public Sound(String fileName, boolean isLoop) {
        this.fileName = fileName;
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

            System.out.println("+ Sound: " + fileName);
        } catch (Exception ex) {
            System.out.println("Can not open sound: " + fileName);
        } finally {
            try {
                audioIn.close();
                audioIn = null;
            } catch (IOException ex) {
                System.out.println("Can not close sound: " + fileName);
            }
        }
    }

    /**
     * release data
     */
    public void dispose() {
        clip.close();
        clip = null;
        System.out.println("- Sound: " + fileName);
    }

    public void play() {
        if (isLoop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        clip.setMicrosecondPosition(0);
        clip.start();
        System.out.println("Play sound: " + fileName);
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

            clip.setFramePosition(pausePosition);
            clip.start();

            isPause = false;
        }
    }

    public void setVolume(int gainAmount) {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(gainAmount);
    }
    
    
}