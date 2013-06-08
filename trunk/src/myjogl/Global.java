/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl;

import com.sun.opengl.util.texture.Texture;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Component;
import javax.media.opengl.GLAutoDrawable;
import sun.audio.*;    //import the sun.audio package
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.ClockStoppedException;
import javax.media.Control;
import javax.media.Controller;
import javax.media.ControllerListener;
import javax.media.GainControl;
import javax.media.IncompatibleSourceException;
import javax.media.IncompatibleTimeBaseException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.Time;
import javax.media.TimeBase;
import javax.media.protocol.DataSource;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import myjogl.utils.ResourceManager;

/**
 *
 * @author Jundat
 */
public class Global {

    public static boolean isFullScreen = false;
    public static int FPS = 60;
    public static int wndWidth = 1366;
    public static int wndHeight = 768;
    public static GLAutoDrawable drawable = null;

    public static void Print(HashMap hm) {
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println("[" + pairs.getKey().toString() + ", " + pairs.getValue().toString() + "]");
        }
    }
}
