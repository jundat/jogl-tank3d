package tank3d;

import utils.FullscreenSetting;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 * Tank3D.java <BR> author: Brian Paul (converted to Java by Ron Cemer and Sven
 * Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Tank3D implements GLEventListener {

    public final int FPS = 60;
    public final boolean IS_FULL_SCREEN = false;
    public String name = "Tank 3D";
    public int wndWidth = 800;
    public int wndHeight = 600;
    public GLAutoDrawable drawable;
    public Frame frame;
    public GLCanvas canvas;
    public FPSAnimator animator;
    public FullscreenSetting fullscreen;
    public GameEngine engine;

    public Tank3D() {
        frame = new Frame(this.name);

        //---------------------
        //Th? t? c?a các setting fullscreen ? ?ây r?t quan tr?ng, n?u thay ??i s? khác
        if (IS_FULL_SCREEN) {
            fullscreen = new FullscreenSetting();
            int width = fullscreen.getWidth();
            int height = fullscreen.getHeight();
            frame.setSize(width, height);
            fullscreen.init(frame);
            frame.setFocusable(true);
            frame.setLayout(new BorderLayout());
        }
        //---------------------

        if (!IS_FULL_SCREEN) {
            frame.setSize(wndWidth, wndHeight);
        }

        canvas = new GLCanvas();
        canvas.setAutoSwapBufferMode(false);
        canvas.addGLEventListener(this);
        frame.add(canvas);
        animator = new FPSAnimator(canvas, 60, false);
        frame.addWindowListener(new Tank3DWindowAdapter(this));

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        if (IS_FULL_SCREEN) {
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
        
        frame.requestFocus();
        frame.requestFocusInWindow();
        canvas.requestFocus();
        canvas.requestFocusInWindow();

        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        this.drawable = drawable;

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        
        
        //mycode
        this.loadResource(drawable);
        
        this.engine = new GameEngine(this);
        this.engine.init();
        
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        this.wndWidth = width;
        this.wndHeight = height;

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        if (IS_FULL_SCREEN) {
            this.frame.setExtendedState(this.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        this.engine.run(drawable);

        // Flush all drawing operations to the graphics card
        gl.glFlush();
        canvas.swapBuffers();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void loadResource(GLAutoDrawable drawable) {
        
    }

    public void unloadResource() {
        
    }
}
