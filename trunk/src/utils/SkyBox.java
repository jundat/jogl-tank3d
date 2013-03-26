/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/**
 *
 * @author Jundat
 */

public class SkyBox
{
    private float     m_size;
    
    //texture
    private Texture ttUp;
    private Texture ttDown;
    private Texture ttLeft;
    private Texture ttRight;
    private Texture ttFront;
    private Texture ttBack;
    
    public SkyBox()
    {
        
    }

    public void Initialize(float size)
    {
        this.m_size = size;
    }
    
    //imgFormat is "png" or "tga" or somethings else
    //
    public boolean LoadTextures(GLAutoDrawable drawable, String up, String down, String front, String back, String left, String right, String imgFormat)
    {
        this.ttUp = TextureLoader.Load(drawable, up, true, GL.GL_CLAMP_TO_EDGE);
        this.ttDown = TextureLoader.Load(drawable, down, true, GL.GL_CLAMP_TO_EDGE);
        this.ttFront = TextureLoader.Load(drawable, front, true, GL.GL_CLAMP_TO_EDGE);
        this.ttBack = TextureLoader.Load(drawable, back, true, GL.GL_CLAMP_TO_EDGE);
        this.ttLeft = TextureLoader.Load(drawable, left, true, GL.GL_CLAMP_TO_EDGE);
        this.ttRight = TextureLoader.Load(drawable, right, true, GL.GL_CLAMP_TO_EDGE);

	return true;
    }
    
    public void Render(GLAutoDrawable drawable, float cameraX, float cameraY, float cameraZ)
    {
        GL gl = drawable.getGL();

        gl.glPushMatrix();

	// Move the skybox so that it's centered on the camera.
	gl.glTranslatef(cameraX, cameraY, cameraZ);

	gl.glPushAttrib(GL.GL_FOG_BIT | GL.GL_DEPTH_BUFFER_BIT | GL.GL_LIGHTING_BIT);
	gl.glDisable(GL.GL_DEPTH_TEST);

	gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);

	// Up
        ttUp.enable();
        ttUp.bind();
	gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-m_size, m_size, -m_size);
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(m_size, m_size, -m_size);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(m_size, m_size, m_size);
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-m_size, m_size, m_size);
	gl.glEnd();
        ttUp.disable();

	// Down
        ttDown.enable();
	ttDown.bind();
	gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(m_size, -m_size, -m_size); //
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-m_size, -m_size, -m_size);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-m_size, -m_size, m_size);
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(m_size, -m_size, m_size);
	gl.glEnd();
        ttDown.disable();

	// Left
        ttLeft.enable();
	ttLeft.bind();
	gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(m_size, -m_size, -m_size);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(m_size, m_size, -m_size);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-m_size, m_size, -m_size);
	gl.glEnd();
        ttLeft.disable();

	// Right
        ttRight.enable();
	ttRight.bind();
	gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(m_size, -m_size, m_size);
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-m_size, -m_size, m_size);
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-m_size, m_size, m_size);
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(m_size, m_size, m_size);
	gl.glEnd();
        ttRight.disable();

	// Back
        ttBack.enable();
	ttBack.bind();
	gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(m_size, -m_size, m_size);
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(m_size, m_size, m_size);
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(m_size, m_size, -m_size);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(m_size, -m_size, -m_size);
	gl.glEnd();
        ttBack.disable();

	// Front
        ttFront.enable();
	ttFront.bind();
	gl.glBegin(GL.GL_QUADS);
                gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-m_size, -m_size, -m_size);
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-m_size, m_size, -m_size); 
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-m_size, m_size, m_size);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-m_size, -m_size, m_size);
	gl.glEnd();
        ttFront.disable();

	gl.glPopAttrib();
	gl.glEndList();
	gl.glPopMatrix();
    }
    
    public void Release()
    {
        ttUp.dispose();
        ttDown.dispose();
        ttFront.dispose();
        ttBack.dispose();
        ttLeft.dispose();
        ttRight.dispose();
    }

    public enum POSITION{
            SKY_UP,
            SKY_DOWN,
            SKY_FRONT,
            SKY_BACK,
            SKY_LEFT,
            SKY_RIGHT
    };
};

