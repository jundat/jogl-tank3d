/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl;

import javax.media.opengl.GLAutoDrawable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
