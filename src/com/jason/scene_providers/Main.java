package com.jason.scene_providers;

import android.view.MotionEvent;
import com.jason.scenes.MainScene;
import ice.engine.Scene;
import ice.node.Overlay;
import ice.node.TouchEventListener;

import java.util.Random;

/**
 * User: jason
 * Date: 12-4-16
 * Time: 下午12:27
 */
public class Main extends Test {

    private static final String TAG = Main.class.getSimpleName();

    @Override
    protected Scene onCreateScene() {
        return new MainScene();
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        scene.addEventListener(new TouchHandler());
    }

    @Override
    protected void onResume() {
        super.onResume();

        initGround();
    }


    private class TouchHandler extends TouchEventListener {
        private Random random = new Random();

        @Override
        public boolean onEvent(Overlay overlay, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP)
                addRandomBox(event.getX(), event.getY());

            return false;
        }

        private void addRandomBox(float x, float y) {
            float width = random.nextFloat() * maxWorldWidth / 4 + 0.05f * maxWorldWidth;
            float height = random.nextFloat() * maxWorldHeight / 4 + 0.05f * maxWorldHeight;

            createBox(x * scaleRate, y * scaleRate, width, height);
        }


    }
}
