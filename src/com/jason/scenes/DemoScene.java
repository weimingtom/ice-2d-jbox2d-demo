package com.jason.scenes;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.animation.LinearInterpolator;
import com.jason.R;
import ice.animation.*;
import ice.engine.Scene;
import ice.graphic.gl_status.DepthController;
import ice.graphic.texture.BitmapTexture;
import ice.graphic.texture.ETC1Texture;
import ice.graphic.texture.Texture;
import ice.model.vertex.VertexBufferObject;
import ice.node.Overlay;
import ice.node.widget.Grid;
import ice.node.widget.Mesh;
import ice.node.widget.Simulate3D;
import ice.node.widget.TextOverlay;
import ice.practical.DigitSequence;
import ice.practical.TestParticleSystem;
import ice.res.Res;
import ice.util.ObjLoader;

import static ice.graphic.gl_status.BlendController.BLEND_S_ONE_D_ONE;
import static ice.practical.DigitSequence.STYLE_0_9_POSITIVE_NEGATIVE;

public class DemoScene extends Scene {

    @Override
    protected void onCreate() {

        textOverlayTest();

        simulateTest();

        pokerTest();

        atlasTest();

        meshTest();

        particleSystemTest();

        Grid grid = new Grid(100, 100);
        grid.setTexture(new ETC1Texture(R.raw.hover));
        grid.addGlStatusController(BLEND_S_ONE_D_ONE);
        addChild(hoverEffect = grid);

    }


    private void meshTest() {
        ObjLoader objLoader = new ObjLoader();

        Resources resources = Res.getContext().getResources();

        objLoader.loadObj(
                resources.openRawResource(R.raw.teaport)
        );

        VertexBufferObject vertexBufferObject = new VertexBufferObject(
                objLoader.getVertexNum(),
                objLoader.getAttributes()
        );
        vertexBufferObject.setVertices(objLoader.getVertexData());

        Bitmap bitmap = Res.getBitmap(R.drawable.mask1);
        BitmapTexture texture = new BitmapTexture(
                bitmap,
                Texture.Params.LINEAR_REPEAT
        );

        Mesh<VertexBufferObject> mesh = new Mesh<VertexBufferObject>(
                vertexBufferObject,
                texture
        );

        mesh.setPos(200, 200, 50);
        mesh.setRotate(-90, 1, 0, 0);

        mesh.addGlStatusController(new DepthController(true));
        //mesh.setColors(new float[]{1, 0, 0, 1});

        RotateAnimation rotateAnim = new RotateAnimation(9000, 360);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.setRotateVector(0, 0, 1);
        rotateAnim.setLoopTimes(Animation.FOREVER);

        mesh.startAnimation(rotateAnim);

        addChild(mesh);
    }

    private void simulateTest() {
        Simulate3D simulate3D = new Simulate3D(R.drawable.crown);
        RotateAnimation rotate = new RotateAnimation(3000, 360);
        rotate.setRotateVector(0, 1, 0);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setLoopTimes(Animation.FOREVER);
        simulate3D.startAnimation(rotate);

        simulate3D.setPos(
                getWidth() / 2,
                700
        );

        addChild(simulate3D);
    }

    private void atlasTest() {
        DigitSequence digitSequence = new DigitSequence(
                STYLE_0_9_POSITIVE_NEGATIVE,
                R.drawable.digit,
                50,
                50,
                30
        );

        digitSequence.setPrePrepareLength(10);
        digitSequence.setDigit(0);
        digitSequence.growTo(999, 100000);

        digitSequence.setPos(
                getWidth() / 2,
                getHeight() / 2 - 200
        );

        addChild(digitSequence);
    }

    private void pokerTest() {
        PokerOverlay pokerOverlay = new PokerOverlay();

        pokerOverlay.setPos(
                getWidth() / 2,
                getHeight() / 2
        );

        AnimationGroup group = new AnimationGroup();

        RotateAnimation rotateAnimation = new RotateAnimation(3000, 720);
        rotateAnimation.setRotateVector(1, 1, 1);

        TranslateAnimation translateAnimation = new TranslateAnimation(3000, 200, 200, 200);

        group.add(translateAnimation);
        group.add(rotateAnimation);

        group.setFillAfter(false);
        group.setLoopTimes(Animation.FOREVER);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1000, 1, 0);
        alphaAnimation.setOffset(2000);
        group.add(alphaAnimation);

        pokerOverlay.startAnimation(group);

        pokerOverlay.addGlStatusController(new DepthController(true));

        addChild(pokerOverlay);
    }

    private void particleSystemTest() {
        TestParticleSystem particleSystem = new TestParticleSystem(50, R.drawable.star);
        particleSystem.addGlStatusController(new DepthController(false));

        particleSystem.setPos(700, getHeight() / 2);

        RotateAnimation rotateAnimation = new RotateAnimation(30000, 720);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.setLoopTimes(Animation.FOREVER);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        particleSystem.startAnimation(rotateAnimation);


        addChild(particleSystem);
    }

    private void textOverlayTest() {
        TextOverlay textOverlay = new TextOverlay(200, 40);

        textOverlay.setPos(
                getWidth() / 2,
                650
        );
        textOverlay.setColor(Color.YELLOW);
        textOverlay.setText("Hello Demo !");

        addChild(textOverlay);
    }

    private Overlay hoverEffect;
}
