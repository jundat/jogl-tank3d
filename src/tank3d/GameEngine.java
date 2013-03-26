package tank3d;

import gameview.GameView;
import gameview.IntroView;
import java.util.ArrayList;
import java.util.Iterator;
import javax.media.opengl.GLAutoDrawable;
import tank3d.Tank3D;

/**
 * GameEngine class provides a basic tank3d engine.
 */
public class GameEngine {

    public Tank3D tank3d;
    //public Vector views;
    public ArrayList views;
    public boolean paused;
    public static int volume = 3; // 1, 2, 3, 4, 5
    private long localTime;

    public GameEngine(Tank3D tank3d) {
        this.tank3d = tank3d;
        this.paused = false;
        this.views = new ArrayList();
        this.localTime = System.currentTimeMillis();
        // more...
    }

    public void init() {
        this.attach(new IntroView(this));
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
            this.views.add(view);
        }
    }

    public void detach(GameView view) {
        this.views.remove(view);
        view.unload();
    }

    public void display(GLAutoDrawable drawable) {
        Iterator it = views.iterator();
        while(it.hasNext()){
            GameView view = (GameView) it.next();
            view.display(drawable);
        }
    }

    public void update(long time) {
        Iterator it = views.iterator();
        while(it.hasNext()){
            GameView view = (GameView) it.next();
            view.update(time);
        }
    }

    /**
     * Just send event to the first GameView It mean the top GameView will solve
     * this event, other views do not solve this event
     *
     * @param x : Pointer.x
     * @param y : Pointer.y
     */
    public void pointerPressed(int x, int y) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.get(views.size() - 1);
            view.pointerPressed(x, y);
        }
    }

    public void pointerDragged(int x, int y) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.get(views.size() - 1);
            view.pointerDragged(x, y);
        }
    }

    public void pointerReleased(int x, int y) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.get(views.size() - 1);
            view.pointerReleased(x, y);
        }
    }

    public void keyPressed(int keyCode) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.get(views.size() - 1);
            view.keyPressed(keyCode);
        }
    }

    public void keyReleased(int keyCode) {
        if (!this.views.isEmpty()) {
            GameView view = (GameView) this.views.get(views.size() - 1);
            view.keyReleased(keyCode);
        }
    }

    public void run(GLAutoDrawable drawable) {
        long currentTime = System.currentTimeMillis();
        //long timeAnimation = 1000 / 10;//tank3d.FPS;
        //---------------------------------------------------------
        //if (currentTime - localTime >= timeAnimation) 
        {
            this.update(currentTime - localTime);
            this.display(drawable);
            
            //--------------------
//            if(currentTime > localTime)
//                System.out.println("FPS: " + (1000 / (currentTime - localTime)));
            
            localTime = currentTime;
        }
//        else 
//        {
//            try {
//                Thread.sleep(timeAnimation - (currentTime - localTime));
//            } catch (InterruptedException ex) {
//                Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
}
