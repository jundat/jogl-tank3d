package myjogl;

import myjogl.gameview.GameView;
import myjogl.gameview.MenuView;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.media.opengl.GLAutoDrawable;
import myjogl.gameview.IntroView;
import myjogl.tank3d.Tank3D;
import myjogl.utils.ResourceManager;

/**
 * GameEngine class provides a basic tank3d engine.
 */
public class GameEngine implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public boolean paused;
    public int volume = 3; // 1, 2, 3, 4, 5
    
    public Tank3D tank3d;
    public Vector views;
    public long localTime = System.currentTimeMillis();
    
    GameView oldView = null; //detaching view
    boolean hasOldView = false;
    
    GameView newView = null; //attaching view
    boolean hasNewView = false;
    
    private static GameEngine instance = null;
    
    private GameEngine(){}
    
    public static GameEngine getInst(){
        if(instance == null){
            instance = new GameEngine();
        }
        
        return instance;
    }
    
    public void init(Tank3D tank3d){
        this.tank3d = tank3d;
        this.paused = false;
        this.views = new Vector();
        
        tank3d.canvas.addKeyListener(KeyboardState.getState());
        
        //----
        attach(new MenuView());
    }

    public void resume() {
        if (paused) {
            this.paused = false;
        }
    }

    public void pause() {
        if (!paused) {
            this.paused = true;
        }
    }

    public void attach(GameView view) {
        if (!this.views.contains(view)) {
            newView = view;
            hasNewView = true;
        }
    }

    public void detach(GameView view) {
        if (this.views.contains(view)) {
            oldView = view;
            hasOldView = true;
        }
    }

    public void display() {
        Enumeration enumeration = this.views.elements();
        while (enumeration.hasMoreElements()) {
            GameView view = (GameView) enumeration.nextElement();
            view.display();
        }
    }

    public void update(long time) {
        Enumeration enumeration = this.views.elements();
        while (enumeration.hasMoreElements()) {
            GameView view = (GameView) enumeration.nextElement();
            view.update(time);
        }
    }

    public void run(GLAutoDrawable drawable) {
        
        //always detach first
        if(hasOldView) {
            this.views.remove(oldView);
            oldView.unload();
            hasOldView = false;
            
            System.gc();
            Runtime.getRuntime().gc();
        }
        
        ResourceManager.getInst().PreUnload();
        
        if(hasNewView) {
            newView.load();
            this.views.add(newView);
            hasNewView = false;
        }
        
        ResourceManager.getInst().PreLoad();
        
        long currentTime = System.currentTimeMillis();
        {
            this.update(currentTime - localTime);
            this.display();
            
            //System.out.println("FPS: " + (float)1000 / (currentTime - localTime));
            tank3d.frame.setTitle("FPS: " + (float)1000 / (currentTime - localTime));

            localTime = currentTime;
        }
    }
    
    public void exit(){
        this.tank3d.exit();
    }

    /////////////////////// ENVENT HANDLER /////////////////////////////////////
    public void keyTyped(KeyEvent e) {
    }

    //
    public void keyPressed(KeyEvent e) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.lastElement();
            view.keyPressed(e);
        }
    }

    //
    public void keyReleased(KeyEvent e) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.lastElement();
            view.keyReleased(e);
        }
    }

    //
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Translate to OpenGL coordinate
     * @param e 
     */
    public void mousePressed(MouseEvent e) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.lastElement();
            int dy = Global.wndHeight - e.getY();
            int dyy = dy - e.getY();
            e.translatePoint(0, dyy);
            view.pointerPressed(e);
        }
    }

    /**
     * Translate to OpenGL coordinate
     * @param e 
     */
    public void mouseReleased(MouseEvent e) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.lastElement();
            int dy = Global.wndHeight - e.getY();
            int dyy = dy - e.getY();
            e.translatePoint(0, dyy);
            view.pointerReleased(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Translate to OpenGL coordinate
     * @param e 
     */
    public void mouseMoved(MouseEvent e) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.lastElement();
            int dy = Global.wndHeight - e.getY();
            int dyy = dy - e.getY();
            e.translatePoint(0, dyy);
            view.pointerMoved(e);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
