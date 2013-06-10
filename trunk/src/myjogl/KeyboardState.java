/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl;

import java.awt.event.KeyEvent;

/**
 *
 * @author Jundat
 */
public class KeyboardState {
    
    private boolean[] keys = new boolean[256];
    
    private static KeyboardState state;
    
    private KeyboardState() {
        for (int i = 0; i < 256; i++) {
            keys[i] = false;
        }
    }
    
    public static KeyboardState getState() {
        if(state == null) {
            state = new KeyboardState();
        }
        
        return state;
    }
    
    /**
     * Be called in gameEngine
     * @param e 
     */
    public void Keypress(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
     /**
     * Be called in gameEngine
     * @param e 
     */
    public void KeyRelease(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    /**
     * Be called in object
     * @param key
     * @return 
     */
    public boolean isDown(int key) {
        return keys[key];
    }
    
    public boolean isUp(int key) {
        return ! keys[key];
    }
}
