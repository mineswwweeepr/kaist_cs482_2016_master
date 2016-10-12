package kr.ac.kaist.vclab.Scenegraph;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.Vector;

import kr.ac.kaist.vclab.Object3D.RigTForm;
import kr.ac.kaist.vclab.Scenegraph.Node.SgShapeNode;
import kr.ac.kaist.vclab.Scenegraph.Node.SgTransformNode;
import kr.ac.kaist.vclab.Scenegraph.Visitor.SgNodeVisitor;
import kr.ac.kaist.vclab.util.MatOperator;
import kr.ac.kaist.vclab.util.ShaderState;

/**
 * Created by PCPC on 2016-10-05.
 */
public class Drawer implements SgNodeVisitor {

    protected Vector<RigTForm> rbtStack;
    public ShaderState curSS;

    public Drawer(RigTForm initialRbt, ShaderState curSS){
        this.curSS = curSS;
        rbtStack = new Vector<>();
        rbtStack.add(initialRbt);
    }

    @Override
    public boolean visit(SgTransformNode node){
        rbtStack.add(rbtStack.lastElement().multiply(node.getRbt()));
        int i = rbtStack.size();
        return true;
    }

    @Override
    public boolean postVisit(SgTransformNode node){
        rbtStack.remove(rbtStack.size() - 1);
        return true;
    }

    @Override
    public boolean visit(SgShapeNode node){

        float[] MVM = new float [16];
        float[] nM = new float[16];

        float temp[] = new float[16];
        Matrix.setIdentityM(temp, 0);

        Matrix.multiplyMM(MVM, 0, rbtStack.lastElement().rigTFormToMatrix(), 0, node.getAffineM(), 0);
        //Matrix.multiplyMM(MVM, 0, node.getAffineM(), 0, rbtStack.lastElement().rigTFormToMatrix(), 0);
        MatOperator.normalMatrix(nM, 0, MVM, 0);

        GLES20.glUniformMatrix4fv(curSS.mModelViewMatrixHandle, 1, false, MVM, 0);
        GLES20.glUniformMatrix4fv(curSS.mNormalMatrixHandle, 1, false, nM, 0);

        node.draw(curSS);
        return true;
    }

    @Override
    public boolean postVisit(SgShapeNode node) {
        return true;
    }

    ShaderState getCurSS() {
        return curSS;
    }

}
