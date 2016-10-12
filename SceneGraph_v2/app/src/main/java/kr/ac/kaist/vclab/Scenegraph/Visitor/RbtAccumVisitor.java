package kr.ac.kaist.vclab.Scenegraph.Visitor;

import java.util.Vector;

import kr.ac.kaist.vclab.Scenegraph.Node.SgShapeNode;
import kr.ac.kaist.vclab.Scenegraph.Node.SgTransformNode;
import kr.ac.kaist.vclab.Object3D.RigTForm;

public class RbtAccumVisitor implements SgNodeVisitor {
    protected Vector<RigTForm> rbtStack;
    SgTransformNode target;
    boolean found;

    public RbtAccumVisitor(SgTransformNode target){
        this.target = target;
        found = false;
        rbtStack = new Vector<>();
    }

    final RigTForm getAccumulatedRbt(int offsetFromStackTop){
        if(!found){
            throw new RuntimeException("RbtAccumVisitor target never reached");
        }
        return rbtStack.get(rbtStack.size()-1-offsetFromStackTop);
    }
    @Override
    public boolean visit(SgTransformNode node){
        if (rbtStack.isEmpty())
            rbtStack.add(node.getRbt());
        else {
            RigTForm last = rbtStack.lastElement();
            RigTForm nodeRbt = node.getRbt();
            RigTForm mult = last.multiply(nodeRbt);

            rbtStack.add(mult);

        }
        if(target != null) {
            if (target.equals(node)) {
                found = true;
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean visit(SgShapeNode node){
        return false;
    }

    @Override
    public boolean postVisit(SgTransformNode node){
        rbtStack.remove(rbtStack.size() - 1);
        return true;
    }
    @Override
    public boolean postVisit(SgShapeNode node){
        return false;
    }

    public static RigTForm getPathAccumRbt(
            SgTransformNode source,
            SgTransformNode destination,
            int offsetFromDestination) {

        RbtAccumVisitor accum = new RbtAccumVisitor(destination);
        source.accept(accum);
        return accum.getAccumulatedRbt(offsetFromDestination);
    }

    public static RigTForm getPathAccumRbt(
            SgTransformNode source,
            SgTransformNode destination) {

        RbtAccumVisitor accum = new RbtAccumVisitor(destination);
        source.accept(accum);
        return accum.getAccumulatedRbt(0);
    }
}

