package kr.ac.kaist.vclab.Scenegraph.Node;

import kr.ac.kaist.vclab.Object3D.RigTForm;

public class SgRootNode extends SgTransformNode {

    public RigTForm getRbt() {
        return new RigTForm();
    }

}
