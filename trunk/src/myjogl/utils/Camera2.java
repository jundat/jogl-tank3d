/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.utils;

import GameObjects.GameObject;

/**
 *
 * @author bu0i
 */
public class Camera2 {
    public double x, y, z, r;
    public double lookAtX, lookAtY, lookAtZ;
    public double alpha, beta;
    
    public Camera2(double _lookAtX, double _lookAtY, double _lookAtZ, double _alpha, double _beta, double _R) {
        lookAtX = _lookAtX;
        lookAtY = _lookAtY;
        lookAtZ = _lookAtZ;
        r = _R;
        alpha = _alpha;
        beta = _beta;
    }
    
    public void Update(GameObject tank) {
        lookAtX = tank.m_position.x;
        lookAtZ = tank.m_position.z;
        lookAtY = tank.m_position.y + 3.5f;
        //beta += 0.01f;
        if (alpha <= 0)
            alpha = 0.000001f;
        else if (alpha >= Math.PI)
            alpha = Math.PI - 0.000001f;
        
        x = r * Math.sin(alpha) * Math.sin(beta) + lookAtX;
        y = r * Math.cos(alpha) + lookAtY;
        z = r * Math.sin(alpha) * Math.cos(beta) + lookAtZ;
    }
    
    public void CameraLeftRight(float delta) {
        float tile = delta / 40;
        beta += tile;
    }
    
    /**
     * Angle rotate in y axis
     * @return 
     */
    public float GetAngleY() {
        float dx = (float)(x - lookAtX);
        float dz = (float)(z - lookAtZ);
        //float angle = (float) Math.atan(dz / dx);
        //angle = 180 * angle / (float)Math.PI;
        float angle = (float) (Math.atan2(dz, dx));
        angle = (float)Math.toDegrees(angle);
            
        int angle2 = (int) angle;
        angle2 %= 360;
//        if (dx < 0) { // Phai bo cai nay moi chay dung - bu0i
//            angle2 = (int) (angle - 180);
//        }

        return 90-angle2;
//        Vector3f dir = new Vector3f((float)(x - lookAtX), (float)(y - lookAtY), (float)(z - lookAtZ));
//        System.out.println((float)Math.toDegrees(MathUtil.Angle2Vector(dir, new Vector3f(0, 0, 1))));
//        return 180 - (float)Math.toDegrees(MathUtil.Angle2Vector(dir, new Vector3f(0, 0, 1)));
    }
    
    /**
     * Angle rotate in X axis
     * @return 
     */
    public float GetAngleX() {
        float dy = (float)(y - lookAtY);
        float dz = (float)(z - lookAtZ);
        float angle = (float) Math.atan(dy / dz);
        //angle = 180 * angle / (float)Math.PI;
        angle = (float)Math.toDegrees(angle);
        
        int angle2 = (int) angle;
        angle2 %= 360;
        if (dz < 0) {
            angle2 = (int) (angle - 180);
        }

        return 90-angle2;
    }
    
    /**
     * Angle rotate in Z axis
     * @return 
     */
    public float GetAngleZ() {
        float dy = (float)(y - lookAtY);
        float dx = (float)(x - lookAtX);
        float angle = (float) Math.atan(dx / dy);
        //angle = 180 * angle / (float)Math.PI;
        angle = (float)Math.toDegrees(angle);
        
        int angle2 = (int) angle;
        angle2 %= 360;
        if (dy < 0) {
            angle2 = (int) (angle - 180);
        }

        return 90-angle2;
    }
}
