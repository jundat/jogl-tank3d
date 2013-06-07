/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import myjogl.utils.ResourceManager;

/**
 * Loading ch?y tr??c 
 * @author Jundat
 */
public class LoadingView  implements GameView {
    
    public void LoadingView() {
        Thread t;
        t = new Thread(new Runnable(){
            public void run() {
                //ResourceManager.getInst().LoadOutGame();
                //ResourceManager.getInst().LoadInGame();
            }
        });
        
        t.start();
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void pointerPressed(MouseEvent e) {
    }

    public void pointerMoved(MouseEvent e) {
    }

    public void pointerReleased(MouseEvent e) {
    }

    public void load() {
    }

    public void unload() {
    }

    public void update(long elapsedTime) {
    }

    public void display() {
    }
    
}
