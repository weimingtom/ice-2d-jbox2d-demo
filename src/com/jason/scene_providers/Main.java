package com.jason.scene_providers;

import android.util.Log;
import android.view.MotionEvent;
import com.jason.scenes.MainScene;
import ice.engine.EngineContext;
import ice.engine.Scene;
import ice.engine.SceneProvider;
import ice.node.Overlay;
import ice.node.TouchEventListener;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: jason
 * Date: 12-4-16
 * Time: 下午12:27
 */
public class Main extends SceneProvider {

    private static final String TAG = Main.class.getSimpleName();
    public static final float MAX_WORLD_SIZE = 10.0f;

    @Override
    protected Scene onCreateScene() {
        return new MainScene();
    }

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

        scene.addEventListener(new TouchHandler());
    }

    @Override
    protected void onResume() {
        super.onResume();

        initWorld();

        initGround();

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

    private void initGround() {
        float posX = maxWorldWidth / 2;
        float posY = maxWorldHeight * 0.1f;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(posX, posY);

        groundBody = world.createBody(groundBodyDef);

        float groundWidth = maxWorldWidth;
        float groundHeight = 1f;

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(groundWidth / 2, groundHeight / 2);

        groundBody.createFixture(groundBox, 0);

        ((MainScene) scene).showGround(
                groundWidth / scaleRate,
                groundHeight / scaleRate,
                posX / scaleRate,
                posY / scaleRate
        );
    }

    private float maxWorldWidth;
    private float maxWorldHeight;

    private float scaleRate;
    private float aspect;

    private World world;
    private Body groundBody;
    private List<Body> bodies;

    private boolean stop;

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

    private class TouchHandler extends TouchEventListener {
        private Random random = new Random();

        @Override
        public boolean onEvent(Overlay overlay, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP)
                addRandomBox(event.getX(), event.getY());

            return false;
        }

        private void addRandomBox(float x, float y) {
            Body body = createRandomBox(x, y);

            if (body != null) {
                bodies.add(body);

                Overlay overlay = ((MainScene) scene).showBoxBody(body, scaleRate);

                body.setUserData(overlay);
            }
        }

        private Body createRandomBox(float x, float y) {
            float width = random.nextFloat() * maxWorldWidth / 4;
            float height = random.nextFloat() * maxWorldHeight / 4;

            BodyDef boxBodyDef = new BodyDef();
            boxBodyDef.type = BodyType.DYNAMIC;
            boxBodyDef.position.set(x * scaleRate, y * scaleRate);

            Body boxBody = world.createBody(boxBodyDef);

            if (boxBody == null) {
                Log.e(TAG, "Create body error ! ");
                return null;
            }

            PolygonShape boxShape = new PolygonShape();
            boxShape.setAsBox(width / 2, height / 2);

            FixtureDef boxFixtureDef = new FixtureDef();
            boxFixtureDef.shape = boxShape;
            boxFixtureDef.density = 1;
            boxFixtureDef.friction = 0.8f;
            boxFixtureDef.restitution = 0.2f;

            boxBody.createFixture(boxFixtureDef);

            return boxBody;
        }

    }
}
