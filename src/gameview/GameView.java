package gameview;

import tank3d.GameEngine;
import javax.media.opengl.GLAutoDrawable;

/**
 * GameView class provides an interface for the game objects views.
 */

public interface GameView {
    
    public void keyPressed(int keyCode);
    
    public void keyReleased(int keyCode);

    public void pointerPressed(int x, int y);

    public void pointerDragged(int x, int y);

    public void pointerReleased(int x, int y);
    
    public void unload();

    public void update(long elapsedTime);

    public void display(GLAutoDrawable drawable);
}
