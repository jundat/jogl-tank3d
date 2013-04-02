/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import java.io.IOException;

/**
 * Contain info in header of md2 file
 * @author TIEUNUN
 */
class md2_header_t {

    int ident;              // magic number. must be equal to "IDP2"
    int version;            // md2 version. must be equal to 8
    int skinwidth;          // width of the texture
    int skinheight;         // height of the texture
    int framesize;          // size of one frame in bytes, (use to seek in file to read frame data)
    
    int num_skins;          // number of textures
    int num_xyz;            // number of vertices (in 1 frame)
    int num_st;             // number of texture coordinates
    int num_tris;           // number of triangles
    int num_glcmds;         // number of opengl commands
    int num_frames;         // total number of frames
    
    int ofs_skins;          // offset to skin names (64 bytes each), used to read the fileName of texture
    int ofs_st;             // offset to s-t texture coordinates
    int ofs_tris;           // offset to triangles
    int ofs_frames;         // offset to frame data (used to seek in file to read frame data)
    int ofs_glcmds;         // offset to opengl commands
    int ofs_end;            // offset to end of file

    public md2_header_t() {
    }

    public boolean LoadHeader(LittleEndianDataInputStream in) throws IOException {
        ident = in.readInt();
        version = in.readInt();
        skinwidth = in.readInt();
        skinheight = in.readInt();
        framesize = in.readInt();
        num_skins = in.readInt();
        num_xyz = in.readInt();
        num_st = in.readInt();
        num_tris = in.readInt();
        num_glcmds = in.readInt();
        num_frames = in.readInt();
        ofs_skins = in.readInt();
        ofs_st = in.readInt();
        ofs_tris = in.readInt();
        ofs_frames = in.readInt();
        ofs_glcmds = in.readInt();
        ofs_end = in.readInt();
        return true;
    }
}

/**
 * Contain 3 value of a point in 3D space, without normal vector
 * Call (v[0], v[1], v[2]) to get (x,y,z)
 * @author Jundat
 */
class vec3_t {

    public float[] v;

    public vec3_t() {
        v = new float[3];
    }
}

/**
 * A vertex in 3D space, contain normal vector
 * @author Jundat
 */
class vertex_t {

    public float[] v;              // compressed vertex (x, y, z) coordinates
    public int normalIndex;        // index to a normal vector for the lighting

    public vertex_t() {
        v = new float[3];
    }
}

/**
 * Contain info of a frame
 * @author Jundat
 */
class frame_t {

    public float[] scale;          // scale values (scale[0], scale[1], scale[2])
    public float[] translate;      // 1 translation vector (translate[0],translate[1], translate[2]
    public String name;             // frame name
    
    /**
     * array vertex of this frame
     */
    vertex_t[] verts;               // first vertex of this frame

    public frame_t() {
        scale = new float[3];
        translate = new float[3];
    }
}

/**
 * Contain a triangle info
 * @author Jundat
 */
class triangle_t {

    /**
     * index_xyz[0] => index to 1st vertex in listVertex
     * index_xyz[1] => index to 2nd vertex
     * index_xyz[1] => index to 3rd vertex
     */
    public int[] index_xyz;         // indexes to triangle's vertices
    
    /**
     * index_st[0] => index to 1st st in listCoorinates
     * index_st[1] => index to 2nd st
     * index_st[1] => index to 3rd st
     */
    public int[] index_st;          // indexes to vertices' texture coorinates

    public triangle_t() {
        index_xyz = new int[3];
        index_st = new int[3];
    }
}

/**
 * Contain a value of 1 textCoordinate
 * @author Jundat
 */
class texCoord_t {

    int s;
    int t;
}