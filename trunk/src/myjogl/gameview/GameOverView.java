/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.utils.Renderer;
import myjogl.utils.ResourceManager;
import myjogl.utils.Writer;

/**
 *
 * @author Jundat
 */
public class GameOverView implements GameView {

    Point pBg = new Point(230, 132);
    Point pGame = new Point(230 + 291, 132 + 252);
    Point pOver = new Point(230 + 300, 132 + 165);
    Rectangle rectMenu = new Rectangle(230 + 44, 132 + 5, 202, 54);
    Rectangle rectRetry = new Rectangle(230 + 316, 132 + 5, 202, 54);
    MainGameView mainGameView;
    Texture ttBg;

    public GameOverView(MainGameView mainGameView) {
        this.mainGameView = mainGameView;
        mainGameView.isPause = true;
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
        if (rectMenu.contains(e.getX(), e.getY())) { //menu
            GameEngine.getInst().attach(new MenuView());
            GameEngine.getInst().detach(mainGameView);
            GameEngine.getInst().detach(this);
        } else if(rectRetry.contains(e.getX(), e.getY())) {
            mainGameView.isPause = false;
            mainGameView.loadLevel(Global.level);
            GameEngine.getInst().detach(this);
        }
    }

    public void load() {
        ttBg = ResourceManager.getInst().getTexture("data/common/bg_dialog.png");
        //
        GameEngine.getInst().saveHighscore();
    }

    public void unload() {
        ResourceManager.getInst().deleteTexture("data/common/bg_dialog.png");
    }

    public void update(long elapsedTime) {
    }

    public void display() {
        Renderer.Render(ttBg, pBg.x, pBg.y);
        GameEngine.writer.Render("GAME", pGame.x, pGame.y, 0.9f, 0.9f);
        GameEngine.writer.Render("OVER", pOver.x, pOver.y, 0.9f, 0.9f);
        GameEngine.writer.Render("MENU", rectMenu.x + 18, rectMenu.y + 5, 0.85f, 0.85f);
        GameEngine.writer.Render("RETRY", rectRetry.x + 6, rectRetry.y + 5, 0.85f, 0.85f);
    }
}
