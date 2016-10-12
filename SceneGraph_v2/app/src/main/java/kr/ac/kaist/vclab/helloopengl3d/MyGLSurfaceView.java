package kr.ac.kaist.vclab.helloopengl3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.CountDownTimer;
import android.view.MotionEvent;

/**
 * Created by sjjeon on 16. 9. 20.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    private float mCurrentX = 0;
    private float mCurrentY = 0;

    private float mPreviousX;
    private float mPreviousY;

    private float[] temp1 = new float[16];
    private float[] temp2 = new float[16];


    public int mode;

    public int fingerCount = 0;
    public boolean inputEnable;
    public CountDownTimer suddenChangePreventer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        inputEnable = true;
        suddenChangePreventer = new CountDownTimer(100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mRenderer.touchDown(mPreviousX,mPreviousY);
                inputEnable = true;
            }
        };
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        if(fingerCount != e.getPointerCount()){
            inputEnable = false;
            suddenChangePreventer.start();
        }
        if(fingerCount < e.getPointerCount()){
            fingerCount = e.getPointerCount();
        }

        float x = e.getX(0);
        float y = e.getY(0);

        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if(inputEnable) {
                    switch (fingerCount) {
                        case 1:
                            // Rotate camera
                            //mRenderer.motion(x, y, MyGLRenderer.MOVEMENT_ROTATION);
                            break;
                        case 2:
                            // translate (XY) camera
                            mRenderer.motion(x, y, MyGLRenderer.MOVEMENT_TRANSLATION_XY);
                            break;
                        case 3:
                            // translate (Depth) camera
                            mRenderer.motion(x, y, MyGLRenderer.MOVEMENT_TRANSLATION_DEPTH);
                            break;
                        case 5:
                            inputEnable = false;
                            suddenChangePreventer.start();
                            mRenderer.initScene();
                            break;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                mRenderer.touchDown(-1, -1);
                fingerCount = 0;
                break;
        }

        mPreviousX = x;
        mPreviousY = y;

        requestRender();
        return true;
    }


}
