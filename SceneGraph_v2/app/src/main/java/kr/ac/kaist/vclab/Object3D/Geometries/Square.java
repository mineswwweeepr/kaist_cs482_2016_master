package kr.ac.kaist.vclab.Object3D.Geometries;

import kr.ac.kaist.vclab.Object3D.Geometry;

/**
 * Created by sjjeon on 16. 9. 21.
 */

public class Square extends Geometry {
    public float color[] = { 1f, 1f, 1f };
    public Square() {
        setVertices(GeometrySet.squareVertices);
        setNormals(GeometrySet.squareNormals);
        setBuffer();
    }

}