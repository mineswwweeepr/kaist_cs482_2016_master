package kr.ac.kaist.vclab.Scenegraph.Visitor;

import kr.ac.kaist.vclab.Scenegraph.Node.SgShapeNode;
import kr.ac.kaist.vclab.Scenegraph.Node.SgTransformNode;

public interface SgNodeVisitor {
    public boolean visit(SgTransformNode node);
    public boolean visit(SgShapeNode node);
    public boolean postVisit(SgTransformNode node);
    public boolean postVisit(SgShapeNode node);
}
