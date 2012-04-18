package com.jason.scene_providers;

import com.jason.NavigateGestureHandler;
import ice.engine.EngineContext;
import ice.engine.SceneProvider;
import ice.node.Overlay;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午4:46
 */
public abstract class Test extends SceneProvider {
    public static final float MAX_WORLD_SIZE = 10.0f;

    private static NavigateGestureHandler navigateGestureHandler = new NavigateGestureHandler();

    protected static float maxWorldWidth;
    protected static float maxWorldHeight;

    protected static float scaleRate;
    protected static float aspect;

    @Override
    protected void onCreate() {
        super.onCreate();

        bodies = new CopyOnWriteArrayList<Body>();

        int appWidth = EngineContext.getAppWidth();
        int appHeight = EngineContext.getAppHeight();

        aspect = (float) appWidth / appHeight;
        scaleRate = MAX_WORLD_SIZE / Math.max(appWidth, appHeight);

        if (appWidth > appHeight) {
            maxWorldWidth = MAX_WORLD_SIZE;
            maxWorldHeight = maxWorldWidth / aspect;
        }
        else {
            maxWorldHeight = MAX_WORLD_SIZE;
            maxWorldWidth = maxWorldHeight * aspect;
        }

        getScene().addEventListener(navigateGestureHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initWorld();

        stop = false;

        new SimulateThread().start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stop = true;
    }

    private void initWorld() {
        boolean doSleep = true;
        Vec2 gravity = new Vec2(0, -10f);

        world = new World(gravity, doSleep);
    }

    protected World world;

    private boolean stop;

    protected List<Body> bodies;

    private class SimulateThread extends Thread {
        float step = 1 / 60f;
        int velocityIterations = 10;
        int positionIterations = 5;


        @Override
        public void run() {

            while (!stop) {
                step();

                sleep();
            }

            clearBodies();
        }

        private void clearBodies() {
            for (Body body : bodies) {
                Object userData = body.getUserData();

                if (userData != null && userData instanceof Overlay)
                    getScene().remove((Overlay) userData);

            }
        }

        private void sleep() {
            try {
                Thread.sleep((long) (step * 1000));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void step() {
            world.step(step, velocityIterations, positionIterations);

            for (Body body : bodies) {

                Object userData = body.getUserData();

                if (userData != null && userData instanceof Overlay) {

                    Overlay overlay = (Overlay) userData;

                    Vec2 pos = body.getPosition();
                    float angle = body.getAngle();

                    overlay.setPos(pos.x / scaleRate, pos.y / scaleRate);

                    overlay.setRotate(
                            (float) Math.toDegrees(angle),
                            0,
                            0,
                            1
                    );
                }

            }
        }
    }
}
