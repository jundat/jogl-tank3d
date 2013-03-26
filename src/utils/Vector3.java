/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Jundat
 */
public class Vector3			// expanded 3D vector struct
{
    public float x, y, z;		// 3D vector coordinates
    
    public Vector3() {
        x = y = z = 0;
    }	// constructor
    
    public Vector3 (float new_x, float new_y, float new_z){
        x = new_x; y = new_y; z = new_z;
    }
    
    // overload + operator so that we easier can add vectors
    public Vector3 Add(Vector3 vVector) {
        return new Vector3(vVector.x+x, vVector.y+y, vVector.z+z);
    }

    // overload - operator that we easier can subtract vectors
    public Vector3 Sub(Vector3 vVector) {
        return new Vector3(x-vVector.x, y-vVector.y, z-vVector.z);
    }

    // overload * operator that we easier can multiply by scalars
    public Vector3 Multi(float number){
        return new Vector3(x*number, y*number, z*number);
    }

    // overload / operator that we easier can divide by a scalar
    public Vector3 Div(float number){
        return new Vector3(x/number, y/number, z/number);
    }

    public float[] ToArray3()
    {
            float[] res;
            res = new float[3];
            res[0] = x;
            res[1] = y;
            res[2] = z;

            return res;
    }

    public float[] ToArray4(float newValue)
    {
            float[] res;
            res = new float[4];
            res[0] = x;
            res[1] = y;
            res[2] = z;
            res[3] = newValue;

            return res;
    }
}