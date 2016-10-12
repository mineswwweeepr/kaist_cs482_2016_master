package kr.ac.kaist.vclab.Object3D.Geometries;

import kr.ac.kaist.vclab.Object3D.Geometry;

/**
 * Created by sjjeon on 16. 9. 20.
 */

public class Sphere extends Geometry {

        float color[] = {0.2f, 0.709803922f, 0.898039216f};

        public Sphere() {
                setVertices(GeometrySet.sphereVertices);
                setNormals(GeometrySet.sphereNormal);
                setBuffer();
        }

}