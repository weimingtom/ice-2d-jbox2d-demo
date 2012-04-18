package com.jason.scene_providers;

import android.util.Log;
import android.view.MotionEvent;
import com.jason.scenes.MainScene;
import ice.engine.Scene;
import ice.node.Overlay;
import ice.node.TouchEventListener;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

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

    private Body groundBody;

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
