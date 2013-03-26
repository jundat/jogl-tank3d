/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Jundat
 */
public class Renderer {
    
    public static void Render(GLAutoDrawable drawable, int wndWidth, int wndHeight, Texture tt, int x, int y)
    {
        int w = tt.getWidth();
        int h = tt.getHeight();
        Renderer.Render(drawable, wndWidth, wndHeight, tt, x, y, w, h);
    }
    
    public static void Render(GLAutoDrawable drawable, int wndWidth, int wndHeight, Texture tt, int x, int y, int w, int h)
    {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        gl.glMatrixMode(GL.GL_PROJECTION);

        gl.glPushMatrix();
            gl.glLoadIdentity();

            gl.glViewport(0, 0, wndWidth, wndHeight);

            glu.gluOrtho2D(0.0, wndWidth, 0.0, wndHeight);

            gl.glMatrixMode(GL.GL_MODELVIEW);

            gl.glPushMatrix();
                gl.glLoadIdentity();

                gl.glEnable(GL.GL_BLEND);
                gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
                gl.glEnable(GL.GL_TEXTURE_2D);
                tt.enable();
                tt.bind();

                gl.glBegin(GL.GL_QUADS);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(0, 0, 0);

                    gl.glTexCoord2f(1, 0);
                    gl.glVertex3f(w, 0, 0);

                    gl.glTexCoord2f(1, 1);
                    gl.glVertex3f(w, h, 0);

                    gl.glTexCoord2f(0, 1);
                    gl.glVertex3f(0, h, 0);
                gl.glEnd();

                tt.disable();
                gl.glDisable(GL.GL_TEXTURE_2D);
                gl.glDisable(GL.GL_BLEND);
            gl.glPopMatrix();

            gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();

        gl.glMatrixMode(GL.GL_MODELVIEW);
    }
}
