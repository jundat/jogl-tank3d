/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameview;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GLAutoDrawable;
import tank3d.GameEngine;
import utils.Renderer;
import utils.ResourceManager;

/**
 *
 * @author Jundat
 */
public class IntroView implements GameView {

    public GameEngine engine;
    public Texture ttBgIntro;
    int x = 0;
    int y = 300;

    public IntroView(GameEngine engine) {
        this.engine = engine;
        ResourceManager rm = ResourceManager.getInst();
        rm.LoadIntro(engine.tank3d.drawable);

        ttBgIntro = ResourceManager.ttBgIntro;
    }

    public void keyPressed(int keyCode) {
    }

    public void keyReleased(int keyCode) {
    }

    public void pointerPressed(int x, int y) {
    }

    public void pointerDragged(int x, int y) {
    }

    public void pointerReleased(int x, int y) {
    }

    public void unload() {
        ResourceManager.getInst().UnLoadIntro();
    }

    public void update(long elapsedTime) {
        
    }

    public void display(GLAutoDrawable drawable) {
        Renderer.Render(drawable, engine.tank3d.wndWidth, engine.tank3d.wndHeight, ttBgIntro, 0, 0);
    }
}
