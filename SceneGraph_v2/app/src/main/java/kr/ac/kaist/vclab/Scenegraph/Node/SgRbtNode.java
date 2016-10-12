package kr.ac.kaist.vclab.Scenegraph.Node;

import kr.ac.kaist.vclab.Object3D.RigTForm;

public class SgRbtNode extends SgTransformNode {
    //SgNode which has a Rigid body transform (RigTForm)

    private RigTForm rbt;

    public SgRbtNode(){
        rbt = new RigTForm();
    }
    public SgRbtNode(RigTForm _rbt){
        rbt = new RigTForm(_rbt);
    }
    @Override
    public RigTForm getRbt(){
        return rbt;
    }

    public void setRbt(RigTForm _rbt){
        rbt = new RigTForm(_rbt);
    }

}
