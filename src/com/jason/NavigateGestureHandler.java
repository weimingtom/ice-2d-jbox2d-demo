package com.jason;

import android.view.MotionEvent;
import com.jason.scene_providers.ApplyForce;
import com.jason.scene_providers.Main;
import ice.engine.App;
import ice.engine.EngineContext;
import ice.node.Overlay;
import ice.node.TouchEventListener;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午4:11
 */
public class NavigateGestureHandler extends TouchEventListener {
    private static final Class[] TESTS = new Class[]{Main.class, ApplyForce.class};

    @Override
    public boolean onEvent(Overlay overlay, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            downPointX = event.getX();
        }
        else if (action == MotionEvent.ACTION_UP) {
            float upPointX = event.getX();

            return tryNavigate(downPointX, upPointX);
        }

        return false;
    }

    private boolean tryNavigate(float downPointX, float upPointX) {

        float xOffset = Math.abs(downPointX - upPointX);

        if (xOffset < 200) return false;

        if (upPointX > downPointX) {
            navigateNext();
        }
        else {
            navigatePrevious();
        }

        return true;
    }


    private void navigateNext() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < TESTS.length; i++) {

                    if (i != currentIndex) {

                        currentIndex = (currentIndex + 1) % TESTS.length;

                        EngineContext context = EngineContext.getInstance();
                        App app = context.getApp();
                        app.intent(TESTS[i]);

                        break;
                    }
                }

            }
        }.start();
    }

    private void navigatePrevious() {

        new Thread() {
            @Override
            public void run() {

                currentIndex--;
                if (currentIndex < 0)
                    currentIndex = 0;

                EngineContext context = EngineContext.getInstance();
                App app = context.getApp();
                app.back();

            }
        }.start();

    }

    private int currentIndex;
    private float downPointX;
}
