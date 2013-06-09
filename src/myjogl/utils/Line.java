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
public class Line {
    Vector3 M0;
    Vector3 DirectVector;
    
    public Line(Vector3 m0, Vector3 direct){
        M0 = m0;
        DirectVector = direct;
    }
    
    public double Distance(Vector3 point) {
        double distance;
        
        Vector3 m = MathUtil.SubVector(point, M0);
        
        distance = (Math.abs(MathUtil.ScrossProduct(DirectVector, m))/ MathUtil.AbsVector3(DirectVector));
        return distance;
    }
    
    public boolean IsCollisionWithSphere(BoundSphere sphere) {
        double distance = Distance(sphere.Position);
        if (distance <= sphere.R)
            return true;
        return false;
    }
    
    public Vector3 IsCollisionWithGameObject(GameObject obj) {
        if (IsCollisionWithSphere(obj.BigBoundTranform)) {
            obj.UpdateBoundSphereTranform();
            int size = obj.BoundSphereTranslated.size();
            for (int i = 0; i < size; i++) {
                if (IsCollisionWithSphere((BoundSphere)obj.BoundSphereTranslated.elementAt(i)))
                    return obj.m_position;
            }
        }
        return null;
    }
}
