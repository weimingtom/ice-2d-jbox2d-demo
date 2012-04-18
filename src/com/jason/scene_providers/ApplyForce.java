package com.jason.scene_providers;

import android.graphics.PointF;
import android.view.MotionEvent;
import com.jason.scenes.ApplyForceScene;
import ice.engine.Scene;
import ice.node.Overlay;
import ice.node.TouchEventListener;
import ice.node.widget.Grid;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午4:48
 */
public class ApplyForce extends Test {

    @Override
    protected Scene onCreateScene() {
        return new ApplyForceScene();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initGround();

        forceBox = createBox(
                0.2f * maxWorldWidth,
                0.6f * maxWorldHeight,
                1,
                1
        );

        Grid grid = (Grid) forceBox.getUserData();
        grid.addEventListener(new ApplyFocreListener());

    }

    private Body forceBox;

    private class ApplyFocreListener extends TouchEventListener {

        private ApplyFocreListener() {
            lastPoint = new PointF();
        }

        @Override
        public boolean onEvent(Overlay overlay, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                lastPoint.set(x, y);
            }

            Vec2 pos = forceBox.getPosition();
            Vec2 boxCenter = new Vec2(pos);
            Vec2 force = new Vec2((x * scaleRate - pos.x) * 10, (y * scaleRate - pos.y) * 10);

            forceBox.applyForce(force, boxCenter);

            return true;
        }

        private PointF lastPoint;

    }
}
