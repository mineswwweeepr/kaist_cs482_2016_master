package kr.ac.kaist.vclab.Object3D.hierarchicalObject;

import kr.ac.kaist.vclab.Object3D.Geometry;

/**
 * Created by PCPC on 2016-10-05.
 */
public class ShapeDesc {
    int parentJointId;
    float x, y, z, sx, sy, sz;
    Geometry geometry;

    public ShapeDesc(int p, float x, float y, float z, float sx, float sy, float sz, Geometry geo){
        this.parentJointId = p;
        this.x= x;
        this.y = y;
        this.z = z;
        this.sx = sx;
        this.sy = sy;
        this.sz = sz;
        geometry = geo;
    }


}
