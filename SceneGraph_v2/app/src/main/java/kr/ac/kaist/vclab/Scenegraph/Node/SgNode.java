package kr.ac.kaist.vclab.Scenegraph.Node;

import kr.ac.kaist.vclab.Scenegraph.Visitor.SgNodeVisitor;

public class SgNode extends Object{
    public boolean accept(SgNodeVisitor visitor){
        boolean result = true;
        return result;
    }

}
