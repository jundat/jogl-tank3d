package myjogl.tank3d;

import com.sun.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import myjogl.GameEngine;
import myjogl.Global;
import myjogl.gameview.IntroView;
import myjogl.utils.FullscreenSetting;
import myjogl.utils.ResourceManager;

/**
 * Tank3D.java <BR> author: Brian Paul (converted to Java by Ron Cemer and Sven
 * Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Tank3D implements GLEventListener {

    public String name = "Tank 3D";
    public Frame frame;
    public GLCanvas canvas;
    public FPSAnimator animator;
    public FullscreenSetting fullscreen;

    public Tank3D() {
        frame = new Frame(this.name);

        //---------------------
        //Th? t? c?a các setting fullscreen ? ?ây r?t quan tr?ng, n?u thay ??i s? khác
        if (Global.isFullScreen) {
            fullscreen = new FullscreenSetting();
            Global.wndWidth = fullscreen.getWidth();
            Global.wndHeight = fullscreen.getHeight();
            frame.setSize(Global.wndWidth, Global.wndHeight);
            fullscreen.init(frame);
            frame.setFocusable(true);
            frame.setLayout(new BorderLayout());
        }
        //---------------------

        if (!Global.isFullScreen) {
            frame.setSize(Global.wndWidth, Global.wndHeight);
        }

        canvas = new GLCanvas();
        canvas.setAutoSwapBufferMode(false);
        
        GameEngine engine = GameEngine.getInst();
        engine.init(this);
        
        canvas.addGLEventListener(this);
        canvas.addKeyListener(engine);
        canvas.addMouseListener(engine);
        canvas.addMouseMotionListener(engine);
        canvas.addMouseWheelListener(engine);
        
        frame.add(canvas);
        animator = new FPSAnimator(canvas, 60, false);
        frame.addWindowListener(new Tank3DWindowAdapter(this));

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (Global.isFullScreen) {
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
        
        frame.requestFocus();
        frame.requestFocusInWindow();
        canvas.requestFocus();
        canvas.requestFocusInWindow();

        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        Global.drawable = drawable;

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        //gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
        //mycode
        this.loadResource(drawable);
        
        GameEngine engine = GameEngine.getInst();
        engine.init(this);
        
        engine.attach(new IntroView());
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        Global.drawable = drawable;
        
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        Global.wndWidth = width;
        Global.wndHeight = height;

        height = height <= 0 ? 1 : height;
        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        if (Global.isFullScreen) {
            this.frame.setExtendedState(this.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
    }

    public void display(GLAutoDrawable drawable) {
        Global.drawable = drawable;
        
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        GameEngine.getInst().run();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
        canvas.swapBuffers();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void loadResource(GLAutoDrawable drawable) {
        ResourceManager.getInst().LoadOutGame();
        ResourceManager.getInst().LoadInGame();
    }

    public void unloadResource() {
        
    }
    
    public void exit(){
        this.unloadResource();
        
        if (Global.isFullScreen) {
            fullscreen.exit();
        }
        
        this.animator.stop();

        System.exit(0);
    }       
}
