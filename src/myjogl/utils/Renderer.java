/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class Renderer {
    
    public static void Render(Texture tt, int x, int y)
    {
        int w = tt.getWidth();
        int h = tt.getHeight();
        Renderer.Render(tt, x, y, w, h);
    }
    
    public static void Render(Texture tt, int x, int y, int w, int h)
    {
        GL gl = Global.drawable.getGL();
        GLU glu = new GLU();

        gl.glMatrixMode(GL.GL_PROJECTION);

        gl.glPushMatrix();
            gl.glLoadIdentity();

            gl.glViewport(0, 0, Global.wndWidth, Global.wndHeight);

            glu.gluOrtho2D(0.0, Global.wndWidth, 0.0, Global.wndHeight);

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
                    gl.glVertex3f(x, y, 0);

                    gl.glTexCoord2f(1, 0);
                    gl.glVertex3f(x + w, y, 0);

                    gl.glTexCoord2f(1, 1);
                    gl.glVertex3f(x + w, y + h, 0);

                    gl.glTexCoord2f(0, 1);
                    gl.glVertex3f(x, y + h, 0);
                gl.glEnd();

                tt.disable();
                gl.glDisable(GL.GL_TEXTURE_2D);
                gl.glDisable(GL.GL_BLEND);
            gl.glPopMatrix();

            gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();

        gl.glMatrixMode(GL.GL_MODELVIEW);
    }
    
    /**
     * 
     * @param drawable
     * @param wndWidth
     * @param wndHeight
     * @param tt
     * @param x
     * @param y
     * @param alpha between 0.0 -> 1.0
     */
    public static void Render(Texture tt, int x, int y, float alpha)
    {
        int w = tt.getWidth();
        int h = tt.getHeight();
        
        GL gl = Global.drawable.getGL();
        GLU glu = new GLU();

        gl.glMatrixMode(GL.GL_PROJECTION);

        gl.glPushMatrix();
            gl.glLoadIdentity();

            gl.glViewport(0, 0, Global.wndWidth, Global.wndHeight);

            glu.gluOrtho2D(0.0, Global.wndWidth, 0.0, Global.wndHeight);

            gl.glMatrixMode(GL.GL_MODELVIEW);

            gl.glPushMatrix();
                gl.glLoadIdentity();

                gl.glEnable(GL.GL_BLEND);
                gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
                //gl.glBlendFuncSeparate(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA, (int)(255 * alpha), );
                gl.glEnable(GL.GL_TEXTURE_2D);
                tt.enable();
                tt.bind();

                gl.glBegin(GL.GL_QUADS);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(x, y, 0);

                    gl.glTexCoord2f(1, 0);
                    gl.glVertex3f(x + w, y, 0);

                    gl.glTexCoord2f(1, 1);
                    gl.glVertex3f(x + w, y + h, 0);

                    gl.glTexCoord2f(0, 1);
                    gl.glVertex3f(x, y + h, 0);
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
