/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import myjogl.Global;

/**
 *
 * @author Jundat
 */
public class SkyBox {

    private float m_size;
    //texture
    private Texture ttUp;
    private Texture ttDown;
    private Texture ttFront;
    private Texture ttBack;
    private Texture ttLeft;
    private Texture ttRight;

    public SkyBox() {
    }

    public void Initialize(float size) {
        this.m_size = size;
    }

    public void LoadTextures(Texture up, Texture down, Texture front, Texture back, Texture left, Texture right) {
        ttUp = up;
        ttDown = down;
        ttFront = front;
        ttBack = back;
        ttLeft = left;
        ttRight = right;
    }

    public void LoadTextures(String up, String down, String front, String back, String left, String right) {
        this.ttUp = ResourceManager.getInst().getTexture(up, false, GL.GL_CLAMP_TO_EDGE);
        this.ttDown = ResourceManager.getInst().getTexture(down, false, GL.GL_CLAMP_TO_EDGE);
        this.ttFront = ResourceManager.getInst().getTexture(front, false, GL.GL_CLAMP_TO_EDGE);
        this.ttBack = ResourceManager.getInst().getTexture(back, false, GL.GL_CLAMP_TO_EDGE);
        this.ttLeft = ResourceManager.getInst().getTexture(left, false, GL.GL_CLAMP_TO_EDGE);
        this.ttRight = ResourceManager.getInst().getTexture(right, false, GL.GL_CLAMP_TO_EDGE);
    }

    public void Render(float cameraX, float cameraY, float cameraZ) {
        GL gl = Global.drawable.getGL();

        gl.glPushMatrix();

        // Move the skybox so that it's centered on the camera.
        gl.glTranslatef(cameraX, cameraY, cameraZ);

        gl.glPushAttrib(GL.GL_FOG_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_LIGHTING_BIT);
        gl.glDisable(GL.GL_DEPTH_TEST);

        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);

//		Top
        ttUp.enable();
        ttUp.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, m_size, -m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, m_size);
        gl.glEnd();
        ttUp.disable();
//		Bottom
        ttDown.enable();
        ttDown.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, -m_size, -m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, m_size);
        gl.glEnd();
        ttDown.disable();
//		Font
        ttFront.enable();
        ttFront.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, -m_size);
        gl.glEnd();
        ttFront.disable();
//		Back
        ttBack.enable();
        ttBack.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, m_size);
        gl.glEnd();
        ttBack.disable();
//		Left
        ttLeft.enable();
        ttLeft.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-m_size, -m_size, m_size);
        gl.glEnd();
        ttLeft.disable();
        
//		Right
        ttRight.enable();
        ttRight.bind();
        gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, m_size);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, m_size);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(m_size, -m_size, -m_size);
        gl.glEnd();
        ttRight.disable();

        gl.glPopAttrib();
        gl.glEndList();
        gl.glPopMatrix();
    }

    public void Release() {
        ttUp.dispose();
        ttDown.dispose();
        ttFront.dispose();
        ttBack.dispose();
        ttLeft.dispose();
        ttRight.dispose();
    }

    public enum POSITION {
        SKY_UP,
        SKY_DOWN,
        SKY_FRONT,
        SKY_BACK,
        SKY_LEFT,
        SKY_RIGHT
    };
};
