/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.util.Vector;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import myjogl.utils.BoundSphere;
import myjogl.utils.GLModel;
import myjogl.utils.MathUtil;
import myjogl.utils.MatrixUtil;
import myjogl.utils.ModelLoaderOBJ;
import myjogl.utils.Vector3;
import sun.security.x509.AttributeNameEnumeration;

/**
 *
 * @author bu0i,tieunun,jundat
 */
public class GameObject {
    public Vector3 m_position;
    
    protected Vector3 m_velocity;
    protected float m_v;
    
    //Chau vo ghi chu thich may cai bien nay nha, doc dek hieu ~~
    protected GLModel m_model;
    protected Vector3 m_direct;
    protected Vector3 m_modelDirect;
    protected float m_scale;
    protected Vector3 m_rotate;
    
    public Vector BoundSphereObject; // Luu Bound cho tung object cu the
    public Vector BoundSphereTranslated; // DS Bound da transfom
    
    public BoundSphere BigBoundTranform; // Bound Big da tranform
    
    protected float[] m_matrixTransform;
    
    public GameObject(Vector3 pos, Vector3 direct, float vel) {
        m_position = new Vector3(pos);
        
        m_direct = new Vector3(direct);
        m_v = vel;
        m_velocity = new Vector3(0, 0, 0);
    }
    
    protected void LoadModel(GLAutoDrawable drawable, String _fileModel, String _fileMtl, String _fileSkin) {
        BoundSphereObject = new Vector();
        BoundSphereTranslated = new Vector();
        m_model = ModelLoaderOBJ.LoadModel(_fileModel, _fileMtl, _fileSkin, drawable);
    }
    
    public void Init(GLAutoDrawable drawable, String _fileModel, String _fileMtl, String _fileSkin) {
        LoadModel(drawable, _fileModel,_fileMtl, _fileSkin);
    }
    
    public void Update(float gameTime) {
        float dirC = (float)Math.sqrt(m_direct.x * m_direct.x + m_direct.z * m_direct.z);
        m_velocity.x = m_v / dirC * m_direct.x;
        m_velocity.z = m_v / dirC * m_direct.z;
        
        m_position.x += m_velocity.x;
        m_position.y += m_velocity.y;
        m_position.z += m_velocity.z;
        // Update Big Bound
        m_matrixTransform = GetMatrixTransform(); // Luu lai de dung sau
        Vector3 mid = new Vector3((m_model.rightpoint + m_model.leftpoint)/2,
                (m_model.toppoint + m_model.bottompoint)/2,
                (m_model.nearpoint + m_model.farpoint)/2);
        Vector3 pos = MatrixUtil.MatrixMulti4(m_matrixTransform, mid.ToArrayFloat());
        
        BigBoundTranform = new BoundSphere(pos, m_model.BigBound.R * m_scale);
        
        
    }
    
    public void Draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glPushMatrix();
        gl.glTranslatef(m_position.x, m_position.y, m_position.z);
        double angle = MathUtil.Angle2Vector(m_direct, m_modelDirect); // Tìm góc quay
        if (m_direct.x >=0)
            angle =-angle;
        gl.glRotatef((float)Math.toDegrees(angle), 0, 0, 1);
        gl.glScalef(m_scale, m_scale, m_scale);
        m_model.opengldraw(drawable);
        gl.glPopMatrix();
    }
    
    protected float[] GetMatrixTransform() {
        float[] matrix = new float[16];
        MatrixUtil.MatrixIdentity(matrix); // Init
        MatrixUtil.MatrixTranslate(matrix, m_position.x, m_position.y, m_position.z); // Translate
        double angle = MathUtil.Angle2Vector(m_direct, m_modelDirect); // Tìm góc quay
        if (m_direct.x >=0)
            angle =-angle;
        //MatrixUtil.MatrixRoate(matrix, (float)Math.toDegrees(angle), 0, 0, 1); // Rotate
        MatrixUtil.MatrixScale(matrix, m_scale);
        return matrix;
    }
    
    //
    // For test
    //
    public void DrawBoundSphere(GL gl) {
//        gl.glPushMatrix();
//        gl.glTranslatef(m_position.x, m_position.y, m_position.z);
//        double angle = MathUtil.Angle2Vector(m_direct, m_modelDirect); // Tìm góc quay
//        if (m_direct.x >=0)
//            angle =-angle;
//        gl.glRotatef((float)Math.toDegrees(angle), 0, 0, 1);
//        gl.glScalef(m_scale.x, m_scale.y, m_scale.z);
//        
//        for (int i = 0; i < BoundSphereObject.size(); i++)
//            ((BoundSphere)BoundSphereObject.elementAt(i)).DrawSphere(gl);
//        gl.glPopMatrix();
        UpdateBoundSphereTranform();
        for (int i = 0; i < BoundSphereObject.size(); i++)
            ((BoundSphere)BoundSphereTranslated.elementAt(i)).DrawSphere(gl);
    }
    
    public boolean IsCollisedWith(GameObject other) {
        // Kiem tra sphere lon
        if (!BigBoundTranform.IsCollideWith(other.BigBoundTranform))
            return false;
        
        // Update tranformed sphere
        UpdateBoundSphereTranform();
        other.UpdateBoundSphereTranform();
        
        // Check collision
        int size = BoundSphereTranslated.size();
        Vector otherBound = other.GetBoundTranform();
        int size2 = otherBound.size();
        for (int i = 0; i < size; i++)
            for (int j = 0 ; j < size2; j++) {
                BoundSphere a = (BoundSphere)BoundSphereTranslated.elementAt(i);
                BoundSphere b = (BoundSphere)otherBound.elementAt(j);
                if (a.IsCollideWith(b))
                    return true;
            }
        
        return false;
    }
    
    public void UpdateBoundSphereTranform() {
        BoundSphereTranslated.removeAllElements();
        for (int i = 0; i < BoundSphereObject.size(); i++) {
            BoundSphere bound = (BoundSphere)BoundSphereObject.elementAt(i);
            float[] pos = new float[4];
            MatrixUtil.MatrixMulti4(m_matrixTransform, bound.Position.ToArrayFloat(), pos);
            
            BoundSphereTranslated.add(new BoundSphere(new Vector3(pos[0], pos[1], pos[2]), bound.R * m_scale));
        }
    }
    
    public Vector GetBoundTranform() {
        return this.BoundSphereTranslated;
    }
    
        public void SetVelocity(Vector3 v) {
        m_velocity = v;
    }
}
