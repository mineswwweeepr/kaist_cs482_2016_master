package kr.ac.kaist.vclab.Object3D;

import android.opengl.Matrix;

import kr.ac.kaist.vclab.util.Quaternion;

public class RigTForm extends Object {
    float translateM[];
    Quaternion rotateQ;

    public RigTForm(){
        translateM = new float[]{0,0,0};
        rotateQ = new Quaternion();
    }
    public RigTForm(RigTForm obj){
        float[] temp = obj.getTranslation();
        Quaternion tempQ = obj.getRotation();
        translateM = new float[]{temp[0], temp[1], temp[2]};
        rotateQ = new Quaternion(tempQ);
        temp = null;
        tempQ = null;
    }

    public RigTForm(float[] t, Quaternion r){
        translateM = new float[]{t[0], t[1], t[2]};
        rotateQ = new Quaternion(r);
    }
    public RigTForm(float[] t){
        translateM = new float[]{t[0], t[1], t[2]};
        rotateQ = new Quaternion();
    }
    public RigTForm(Quaternion r){
        translateM = new float[]{0,0,0};
        rotateQ = new Quaternion(r);
    }


    public float[] getTranslation(){
        return translateM;
    }
    public void setTranslation(float[] t){
        translateM[0] = t[0];
        translateM[1] = t[1];
        translateM[2] = t[2];
    }

    public Quaternion getRotation(){
        return rotateQ;
    }

    public float[] multiply(float a[]){
        float[] result= new float[4];
        result[0] = translateM[0] * a[3];
        result[1] = translateM[1] * a[3];
        result[2] = translateM[2] * a[3];
        result[3] = 0;
        float temp[] = rotateQ.multiply(a);
        result[0] += temp[0];
        result[1] += temp[1];
        result[2] += temp[2];
        result[3] += temp[3];
        return result;
    }

    public RigTForm multiply(RigTForm rtf){
        RigTForm result;
        float temp[] = {translateM[0], translateM[1], translateM[2]};
        float temp2[] = {rtf.translateM[0], rtf.translateM[1], rtf.translateM[2], 0};
        float mult[] = rotateQ.multiply(temp2);
        temp[0] += mult[0];
        temp[1] += mult[1];
        temp[2] += mult[2];
        result = new RigTForm(temp, rotateQ.multiply(rtf.getRotation()));
        return result;
    }

    public static RigTForm inv(RigTForm tForm){
        Quaternion invRot = Quaternion.inv(tForm.getRotation());
        float[] temp = {-tForm.translateM[0],-tForm.translateM[1],-tForm.translateM[2], 1};
        return new RigTForm(invRot.multiply(temp), invRot );
    }
    public static RigTForm transFact(RigTForm tForm){
        return new RigTForm(tForm.getTranslation());
    }
    public static RigTForm linFact(RigTForm tForm){
        return new RigTForm(tForm.getRotation());
    }

    public float[] rigTFormToMatrix() {

        float[] tm = new float[16];
        Matrix.setIdentityM(tm,0);
        Matrix.translateM(tm, 0, translateM[0], translateM[1], translateM[2]);
        float[] rm = Quaternion.quatToMat(rotateQ);

        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, tm, 0, rm,0);

        return result;
    }
}
