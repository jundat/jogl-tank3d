/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.swing.JOptionPane;
import myjogl.Global;

/**
 *
 * @author TIEUNUN
 */
public class Md2 {

    private float scale;
    private float interpol;             // time counter
    private int currentFrame;
    private int nextFrame;              // next frame # in animation
    /**
     * current frame's vertices (array of vertex)
     */
    private vec3_t[] currentVList;             // current frame vertices 
    /**
     * next frame's vertices (array of vertex)
     */
    private vec3_t[] nextVList;         // next frame vertices
    /**
     * list c�c ??nh ?� ???c t�nh to�n l?i c�c ??nh n�y m?i ???c d�ng ?? v?
     */
    private vec3_t[] finalVList;          // list interpolated vertices
    public byte[] buffer;              // Md2 data
    public int num_frames;             // number of frames
    public int num_xyz;                // number of vertices in 1 frame
    public int num_glcmds;             // number of opengl commands
    public md2_header_t m_header;      // file header
    public triangle_t[] m_triangles;   // array of triagle
    public frame_t[] m_frames;         // array of frame
    public vec3_t[] m_vertices;        // vertex array, all vertices in all frame
    public int[] m_glcmds;             // opengl command array
    public texCoord_t[] m_texCoord;    // texture coord array
    Texture m_texture;

    public Md2() {
        interpol = 0.0f;
        currentFrame = 0;
        nextFrame = 1;
        scale = 1.0f;
    }

    private void ReadFile(String filename) {
        InputStream inputStr = getClass().getResourceAsStream(filename);

        try {
            buffer = new byte[inputStr.available()];
            inputStr.read(buffer, 0, buffer.length);
        } catch (IOException ex) {
            System.out.println("md2.Readfile.buffer.available(): can not read!");
            JOptionPane.showMessageDialog(null, "Can not readFile: " + filename);
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStr.close();
            } catch (IOException ex) {
                Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Can not readFile: " + filename);
            }
        }
    }

    public Boolean LoadModel(String filename) {
        ReadFile(filename);

        //read header from buffer array
        InputStream bis = new ByteArrayInputStream(buffer);
        LittleEndianDataInputStream din = new LittleEndianDataInputStream(new DataInputStream(bis));

        try {
            m_header = new md2_header_t();
            m_header.LoadHeader(din);
            din.close();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Can not load model: " + filename);
        }

        num_frames = m_header.num_frames;
        num_xyz = m_header.num_xyz;
        num_glcmds = m_header.num_glcmds;

        //number of all vertices = num_frame * num_xyz (in 1 frame)
        m_vertices = new vec3_t[num_frames * num_xyz];

        ///////////////////////////////////////////////////

        try {
            din.reset();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        //load st coord array
        m_texCoord = new texCoord_t[m_header.num_st];

        try {
            din.skip(m_header.ofs_st);

            for (int i = 0; i < m_header.num_st; i++) {
                m_texCoord[i] = new texCoord_t();

                m_texCoord[i].s = din.readUnsignedShort();
                m_texCoord[i].t = din.readUnsignedShort();
            }
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Can not load model: " + filename);
        }

        ///////////////////////////////////////////////////

        // Load triangle array initialization with index vertex and index texture coord
        try {
            din.reset();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        m_triangles = new triangle_t[m_header.num_tris];

        try {
            din.skip(m_header.ofs_tris);

            for (int i = 0; i < m_header.num_tris; i++) {
                m_triangles[i] = new triangle_t();

                m_triangles[i].index_xyz[0] = din.readUnsignedShort();
                m_triangles[i].index_xyz[1] = din.readUnsignedShort();
                m_triangles[i].index_xyz[2] = din.readUnsignedShort();
                m_triangles[i].index_st[0] = din.readUnsignedShort();
                m_triangles[i].index_st[1] = din.readUnsignedShort();
                m_triangles[i].index_st[2] = din.readUnsignedShort();
            }
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        // vertex array initialization
        try {
            din.reset();
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        m_frames = new frame_t[m_header.num_frames];
        try {
            din.skip(m_header.ofs_frames);

            for (int i = 0; i < m_header.num_frames; i++) {
                m_frames[i] = new frame_t();

                m_frames[i].scale[0] = din.readFloat();
                m_frames[i].scale[1] = din.readFloat();
                m_frames[i].scale[2] = din.readFloat();

                m_frames[i].translate[0] = din.readFloat();
                m_frames[i].translate[1] = din.readFloat();
                m_frames[i].translate[2] = din.readFloat();

                //name of frame
                byte[] name = new byte[16];
                din.readFully(name);

                m_frames[i].name = new String(name);

                m_frames[i].verts = new vertex_t[num_xyz];
                for (int j = 0; j < num_xyz; j++) {
                    m_frames[i].verts[j] = new vertex_t();

                    m_frames[i].verts[j].v[0] = din.readUnsignedByte();
                    m_frames[i].verts[j].v[1] = din.readUnsignedByte();
                    m_frames[i].verts[j].v[2] = din.readUnsignedByte();
                    m_frames[i].verts[j].normalIndex = din.readUnsignedByte();

                    //calculate m_vertieces
                    m_vertices[i * num_xyz + j] = new vec3_t();

                    m_vertices[i * num_xyz + j].v[0] = m_frames[i].scale[0] * m_frames[i].verts[j].v[0] + m_frames[i].translate[0];
                    m_vertices[i * num_xyz + j].v[1] = m_frames[i].scale[1] * m_frames[i].verts[j].v[1] + m_frames[i].translate[1];
                    m_vertices[i * num_xyz + j].v[2] = m_frames[i].scale[2] * m_frames[i].verts[j].v[2] + m_frames[i].translate[2];
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Md2.class.getName()).log(Level.SEVERE, null, ex);
        }

        currentVList = new vec3_t[m_header.num_xyz]; // current frame vertices 
        nextVList = new vec3_t[m_header.num_xyz]; // next frame vertices
        finalVList = new vec3_t[m_header.num_xyz]; // list interpolated vertices

        for (int i = 0; i < m_header.num_xyz; i++) {
            currentVList[i] = new vec3_t();
            nextVList[i] = new vec3_t();
            finalVList[i] = new vec3_t();
        }

        if (num_frames > 1) {
            Interpolate();
        }

        return true;
    }

    public void LoadSkin(String filename) {
        try {
            m_texture = TextureLoader.Load(filename, false, GL.GL_REPEAT);// TextureIO.newTexture(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "C�c h�m c?n ??n GLAutoDrawable th� ph?i ???c g?i trong thread ch�nh!");
        }
    }

    public void LoadSkin(Texture skin) {
        m_texture = skin;
    }

    //renders a single key frame
    public void DrawModel(GL gl, int keyframe) {
        gl.glEnable(GL.GL_TEXTURE);
        m_texture.enable();
        m_texture.bind();
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < m_header.num_tris; i++) {
            for (int j = 0; j < 3; j++) {
                float x = (m_vertices[m_triangles[i].index_xyz[j] + m_header.num_xyz * keyframe].v[0]) * scale;
                float y = (m_vertices[m_triangles[i].index_xyz[j] + m_header.num_xyz * keyframe].v[1]) * scale;
                float z = (m_vertices[m_triangles[i].index_xyz[j] + m_header.num_xyz * keyframe].v[2]) * scale;

                float s = (float) (m_texCoord[m_triangles[i].index_st[j]].s) / m_header.skinwidth;
                float t = (float) (m_texCoord[m_triangles[i].index_st[j]].t) / m_header.skinheight;

                gl.glTexCoord2f(s, t);
                gl.glVertex3f(x, y, z);
            }
        }
        gl.glEnd();
        m_texture.disable();
    }

    //Draw animation from start frame too end frame
    public int DrawAnimate(GL gl, int startFrame, int endFrame, float percent) {
        if ((startFrame > currentFrame)) {
            currentFrame = startFrame;
        }

        if ((startFrame < 0) || (endFrame < 0)) {
            return -1;
        }

        if ((startFrame >= num_frames) || (endFrame >= num_frames)) {
            return -1;
        }

        if (interpol >= 1.0) {
            interpol = 0.0f;
            currentFrame++;
            //this.change();
            if (currentFrame >= endFrame) {
                currentFrame = startFrame;
            }

            nextFrame = currentFrame + 1;
            if (nextFrame >= endFrame) {
                nextFrame = startFrame;
            }
        }
        Interpolate();
        //System.out.println(currentFrame);
        gl.glEnable(GL.GL_TEXTURE);
        m_texture.enable();
        m_texture.bind();
        gl.glBegin(GL.GL_TRIANGLES);
        for (int i = 0; i < m_header.num_tris; i++) {
            for (int j = 0; j < 3; j++) {
                float x = finalVList[m_triangles[i].index_xyz[j]].v[0];
                float y = finalVList[m_triangles[i].index_xyz[j]].v[1];
                float z = finalVList[m_triangles[i].index_xyz[j]].v[2];

                float s = (float) (m_texCoord[m_triangles[i].index_st[j]].s) / m_header.skinwidth;
                float t = (float) (m_texCoord[m_triangles[i].index_st[j]].t) / m_header.skinheight;

                gl.glTexCoord2f(s, t);
                gl.glVertex3f(x, y, z);
            }
        }
        gl.glEnd();
        interpol += percent;
        return 0;
    }

    public void SetScale(float scale) {
        this.scale = scale;
    }
    // calculate interpolated vertices from current frame vertices and next frame vertices,scale,interpol

    public void Interpolate() {
        for (int i = 0; i < m_header.num_xyz; i++) {
            currentVList[i].v[0] = this.m_vertices[i + m_header.num_xyz * currentFrame].v[0];
            currentVList[i].v[1] = this.m_vertices[i + m_header.num_xyz * currentFrame].v[1];
            currentVList[i].v[2] = this.m_vertices[i + m_header.num_xyz * currentFrame].v[2];

            nextVList[i].v[0] = this.m_vertices[i + m_header.num_xyz * nextFrame].v[0];
            nextVList[i].v[1] = this.m_vertices[i + m_header.num_xyz * nextFrame].v[1];
            nextVList[i].v[2] = this.m_vertices[i + m_header.num_xyz * nextFrame].v[2];

            finalVList[i].v[0] = (currentVList[i].v[0] + interpol * (nextVList[i].v[0] - currentVList[i].v[0])) * scale;
            finalVList[i].v[1] = (currentVList[i].v[1] + interpol * (nextVList[i].v[1] - currentVList[i].v[1])) * scale;
            finalVList[i].v[2] = (currentVList[i].v[2] + interpol * (nextVList[i].v[2] - currentVList[i].v[2])) * scale;
        }
    }
}
