package kr.ac.kaist.vclab.Scenegraph.Node;

import java.util.Vector;

import kr.ac.kaist.vclab.Object3D.RigTForm;
import kr.ac.kaist.vclab.Scenegraph.Visitor.SgNodeVisitor;

public class SgTransformNode extends SgNode {
    private Vector<SgNode> children = new Vector<>();

    @Override
    public boolean accept(SgNodeVisitor visitor){
        if (!visitor.visit(this))
            return false;
        for (int i = 0, n = children.size(); i < n; ++i) {
            if (!children.get(i).accept(visitor))
                return false;
        }
        return visitor.postVisit(this);
    }

    public RigTForm getRbt(){
        return null;
    }
    public void setRbt(RigTForm _rbt){
    }

    public void addChild(SgNode child){
        children.add(child);
    }

    public void removeChild(SgNode child){
        children.remove(child);
    }

    public int getNumChild(){
        return children.size();
    }

    public SgNode getChild(int i){
        return children.get(i);
    }
}
