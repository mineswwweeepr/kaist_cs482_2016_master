package kr.ac.kaist.vclab.Scenegraph.Node;

import kr.ac.kaist.vclab.Scenegraph.Visitor.SgNodeVisitor;
import kr.ac.kaist.vclab.util.ShaderState;

public class SgShapeNode extends SgNode {
    @Override
    public boolean accept(SgNodeVisitor visitor){
        if (!visitor.visit(this))
            return false;
        return visitor.postVisit(this);
    }

    public float[] getAffineM(){
        return null;
    }

    public void draw(ShaderState curSS){

    }

}
