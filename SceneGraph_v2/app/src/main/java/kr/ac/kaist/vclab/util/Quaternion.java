package kr.ac.kaist.vclab.util;

import android.opengl.Matrix;

public class Quaternion {

    private float w,x,y,z;


    public Quaternion(){
        w = 1f; // w
        x = 0f; // x
        y = 0f; // y
        z = 0f; // z
    }
    public Quaternion(Quaternion q){
        w = q.w;
        x = q.x;
        y = q.y;
        z = q.z;
    }
    public Quaternion(float w, float vec3[]){
        this.w = w;
        x = vec3[0];
        y = vec3[1];
        z = vec3[2];
    }
    public Quaternion(float w, float x, float y, float z){
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Quaternion q){
        w += q.w;
        x += q.x;
        y += q.y;
        z += q.z;
    }

    public void subtract(Quaternion q){
        w -= q.w;
        x -= q.x;
        y -= q.y;
        z -= q.z;
    }

    public void multiply(float s){
        w *= s;
        x *= s;
        y *= s;
        z *= s;
    }

    public Quaternion multiply(Quaternion q){
//        float[] u = {x,y,z};
//        float[] v = {q.x,q.y,q.z};
//        float[] tempU = {x * q.w, y * q.w, z * q.w};
//        float[] tempV = {q.x * w, q.y * w, q.z * w};
//        float[] cross = new float[3];
//        MatOperator.cross(u,v, cross);
//        float[] dir = {tempU[0] + tempV[0] + cross[0],
//                tempU[1] + tempV[1] + cross[1],
//                tempU[2] + tempV[2] + cross[2]};
//
//        Quaternion result = new Quaternion(w * q.w - MatOperator.dot(u,v), dir);

        float y0 = w * q.w - x * q.x - y * q.y - z * q.z;
        float y1 = w * q.x + x * q.w + y * q.z - z * q.y;
        float y2 = w * q.y - x * q.z + y * q.w + z * q.x;
        float y3 = w * q.z + x * q.y - y * q.x + z * q.w;
        return  new Quaternion(y0, y1, y2, y3);
    }




    //float[4]
    public float[] multiply(float[] a){
        float[] result = {
                a[0]*w - a[1]*x - a[2] * y - a[3] * z,
                a[0]*x + a[1]*w - a[2] * z + a[3] * y,
                a[0]*y + a[1]*z + a[2] * w - a[3] * x,
                a[0]*z - a[1]*y + a[2] * x + a[3] * w};
        return result;


    }

    public static Quaternion makeXRotation(double ang) {
        Quaternion r = new Quaternion();
        final double h = 0.5 * ang * Constants.CS175_PI/180;
        r.x = (float)Math.sin(h);
        r.w = (float)Math.cos(h);
        return r;
    }

    public static Quaternion makeYRotation(double ang) {
        Quaternion r = new Quaternion();
        final double h = 0.5 * ang * Constants.CS175_PI/180;
        r.y = (float)Math.sin(h);
        r.w =(float) Math.cos(h);
        return r;
    }

    public static Quaternion makeZRotation(double ang) {
        Quaternion r = new Quaternion();
        final double h = 0.5 * ang * Constants.CS175_PI/180;
        r.z = (float)Math.sin(h);
        r.w = (float)Math.cos(h);
        return r;
    }

    public static float dot(Quaternion q1, Quaternion q2){
        float s = 0f;
        s += q1.w * q2.w +q1.x * q2.x +q1.y * q2.y  + q1.z * q2.z ;
        return s;
    }

    public static float norm2(Quaternion q1){
        return dot(q1,q1);
    }

    public static Quaternion inv(Quaternion q) {
        final float n = norm2(q);
        assert (n > Constants.CS175_EPS2);
        Quaternion result = new Quaternion(q.w, -q.x, -q.y, -q.z);
        result.multiply(1.0f/n);
        return result;
    }

    public static Quaternion normalize(Quaternion q){
        float temp = (float)Math.sqrt(norm2(q));
        q.multiply( 1.0f/temp );
        return new Quaternion(q);
    }

    public static float[] quatToMat(Quaternion q){
        float[] temp = new float[16];

        final float n = norm2(q);
        if(n < Constants.CS175_EPS2){
            return temp;
        }
        Matrix.setIdentityM(temp,0);
        final float two_over_n = 2/n;
        temp[0] -= (q.y * q.y + q.z * q.z) * two_over_n;
        temp[4] += (q.x * q.y - q.w * q.z) * two_over_n;
        temp[12] += (q.x * q.z + q.y * q.w) * two_over_n;

        temp[1] += (q.x * q.y + q.w * q.z) * two_over_n;
        temp[5] -= (q.x * q.x + q.z * q.z) * two_over_n;
        temp[9] += (q.y * q.z - q.x * q.w) * two_over_n;

        temp[2] += (q.x * q.z - q.y * q.w) * two_over_n;
        temp[6] += (q.y * q.z + q.x * q.w) * two_over_n;
        temp[10] -= (q.x * q.x + q.y * q.y) * two_over_n;
        //assert (isAffine(temp));

        return temp;
    }
}
