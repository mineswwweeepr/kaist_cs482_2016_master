package kr.ac.kaist.vclab.Object3D;

import android.opengl.GLES20;
import android.opengl.Matrix;

import kr.ac.kaist.vclab.util.Constants;
import kr.ac.kaist.vclab.Object3D.Geometries.GeometrySet;
import kr.ac.kaist.vclab.util.MatOperator;
import kr.ac.kaist.vclab.util.Quaternion;
import kr.ac.kaist.vclab.util.ShaderState;

/**
 * Created by PCPC on 2016-10-09.
 */
public class Arcball extends Geometry{

    private int viewWidth, viewHeight;
    float color[] = {0.2f, 0.709803922f, 0.898039216f};

    public final float arcBallRadius = 0.4f;
    private float arcballScale = 1f;


    private float[] mArcBallModelViewMatrix;

    private float[] arcBallRotation;
    private float[] mProjMatrix;

    public Arcball(){
        super();
        setVertices(GeometrySet.sphereVertices);
        setNormals(GeometrySet.sphereNormal);
        setBuffer();
        mProjMatrix = new float[16];
        mArcBallModelViewMatrix = new float[16];

        arcBallRotation = new float[16];
    }

    public void setViewInfo(int width, int height, float[] projMat){
        viewWidth = width;
        viewHeight = height;
        System.arraycopy(projMat, 0,mProjMatrix, 0, 16);
    }

    public void setScale(float scale){
        arcballScale = scale;
    }

    public RigTForm move(float[] p0, float[] p1, RigTForm arcballRbt, RigTForm eyeInverse) {

        float[] arcballCenter = new float[4];
        System.arraycopy(arcballRbt.getTranslation(), 0,arcballCenter, 0, 3);
        arcballCenter[3] = 1;
        float[] temp = eyeInverse.multiply(arcballCenter);
        float[] arcballCenter_ec = new float[4];
        System.arraycopy(temp, 0, arcballCenter_ec, 0, 3);
        arcballCenter_ec[3] = 1;

        if (arcballCenter_ec[2] > -Constants.CS175_EPS)
            return new RigTForm(new float[]{0,0,0});

        float[] sphereCenter = getSphereCenterScreen(arcballCenter_ec);
        float radius = getSphereRadius();
        float[] v0 = getArcballDireciton(new float[] {p0[0] - sphereCenter[0], p0[1] - sphereCenter[1]}, radius);
        float[] v1 = getArcballDireciton(new float[] {p1[0] - sphereCenter[0], p1[1] - sphereCenter[1]}, radius);
        RigTForm result = new RigTForm(new Quaternion(0.0f, v1[0], v1[1], v1[2]).multiply(new Quaternion(0.0f, -v0[0], -v0[1], -v0[2])));
        return result;
    }

    public float[] getArcballDireciton(float[] p, float r) {
        float n2 = p[0] * p[0] + p[1] * p[1];

        if (n2 >= r * r) {
            return MatOperator.normalize(new float[]{p[0], -p[1], 0});
        } else {
            return MatOperator.normalize(new float[]{p[0], -p[1], (float) Math.sqrt(r * r - n2)});
        }
    }
    public float[] getSphereCenterScreen(float[] arcballCenter_ec) {
        float[] temp = new float[4];
        Matrix.multiplyMV(temp, 0, mProjMatrix, 0, arcballCenter_ec, 0);
        float[] clipCoord = new float[]{temp[0]/temp[3], temp[1]/temp[3], temp[2]/temp[3]};
        return new float[]{clipCoord[0] * viewWidth / 2.0f + ((float)viewWidth- 1)/2.0f,
                clipCoord[1] * viewHeight / 2.0f + ((float)viewHeight - 1)/2.0f};
    }
    public float getSphereRadius() {
        return arcBallRadius * viewHeight / 2;
    }

    public void draw(ShaderState curSS, RigTForm arcballEye){

        float[] scaleM = new float[16];
        Matrix.setIdentityM(scaleM, 0);
        Matrix.scaleM(scaleM, 0, arcballScale, arcballScale, arcballScale);

        Matrix.multiplyMM(mArcBallModelViewMatrix, 0, arcballEye.rigTFormToMatrix(), 0, scaleM, 0);
        ShaderState.sendModelViewNormalMatrix(curSS, mArcBallModelViewMatrix);

        float[] nM = new float[16];
        MatOperator.normalMatrix(nM, 0, mArcBallModelViewMatrix, 0);
        GLES20.glUniformMatrix4fv(curSS.mNormalMatrixHandle, 1, false, nM, 0);

        GLES20.glUniform3fv(curSS.mColorHandle, 1, color, 0);
        draw(curSS, GLES20.GL_LINES);
    }
}
