/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GLAutoDrawable;
import myjogl.GameEngine;
import myjogl.utils.ResourceManager;
import myjogl.utils.Writer;

/**
 * Ch?a dùng ???c!!!
 *
 * @author Jundat
 */
public class LoadingView implements GameView {

    private GameView loadingView;
    private boolean isCompleted = false;
    private float percent = 0.0f;

    public LoadingView(final GameView loadingView) {
        System.out.println("Go to loading view----------------------------------");
        this.loadingView = loadingView;
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
        loadingView.load();
        isCompleted = true;
        
        if (isCompleted) {
            GameEngine.getInst().attach(loadingView);
            GameEngine.getInst().detach(this);
        }
    }

    public void display() {
        Writer.Render("Loading... " + (int) (100 * percent) + " %",
                "Times New Roman", Font.BOLD, 20, 1050, 40, Color.RED);
    }
}
