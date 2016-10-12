package kr.ac.kaist.vclab.Object3D.Geometries;

import kr.ac.kaist.vclab.Object3D.Geometry;

/**
 * Created by sjjeon on 16. 9. 20.
 */

public class Cube extends Geometry {
    public float color[] = { 0.2f, 0.709803922f, 0.898039216f };


    public Cube() {
        setVertices(GeometrySet.cubeVertices);
        setNormals(GeometrySet.cubeNormals);
        setBuffer();
    }


}
