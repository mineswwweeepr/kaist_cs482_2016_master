package kr.ac.kaist.vclab.Scenegraph.Node;

import android.opengl.GLES20;
import android.opengl.Matrix;

import kr.ac.kaist.vclab.Object3D.Geometry;
import kr.ac.kaist.vclab.util.ShaderState;

public class SgGeometryShapeNode extends SgShapeNode {
    private  Geometry geo;
    float[] affineM = new float[16];
    float[] color = new float[3];

    public SgGeometryShapeNode(Geometry g, float[] color){
        geo = g;
        System.arraycopy(color, 0,this.color, 0, 3);
        Matrix.setIdentityM(affineM, 0);
    }
    public SgGeometryShapeNode(Geometry g, float[] color, float[] translate, float[] eulerAngles, float[] scales){
        geo = g;
        System.arraycopy(color, 0, this.color, 0, 3);

        float translateM[] = new float[16];
        Matrix.setIdentityM(translateM, 0);
        Matrix.translateM(translateM, 0, translate[0], translate[1], translate[2]);
        float rotateM[] = new float [16];
        Matrix.setIdentityM(rotateM, 0);
        Matrix.setRotateEulerM(rotateM, 0, eulerAngles[0], eulerAngles[1], eulerAngles[2]);

        Matrix.multiplyMM(affineM, 0, translateM, 0, rotateM, 0);
        Matrix.scaleM(affineM, 0, scales[0], scales[1], scales[2]);
    }

    @Override
    public float[] getAffineM(){
        return affineM;
    }

    @Override
    public void draw(ShaderState curSS){
        GLES20.glUniform3fv(curSS.mColorHandle, 1, color, 0);
        geo.draw(curSS);
    }

}
