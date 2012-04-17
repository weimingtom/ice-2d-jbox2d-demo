package com.jason.scenes;

import android.graphics.Color;
import ice.engine.Scene;
import ice.model.vertex.Rect;
import ice.model.vertex.VboRect;
import ice.node.Overlay;
import ice.node.widget.Mesh;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * User: jason
 * Date: 12-4-16
 * Time: 下午12:28
 */
public class MainScene extends Scene {

    public void showGround(float groundWidth, float groundHeight, float posX, float posY) {
        Rect groundRect = new VboRect(groundWidth, groundHeight);
        Mesh<Rect> groundMesh = new Mesh<Rect>(groundRect);
        groundMesh.setPos(posX, posY);

        addChild(groundMesh);
    }

    public Overlay showBoxBody(Body body, float scaleRate) {

        PolygonShape box = (PolygonShape) body.m_fixtureList.getShape();

        Vec2 vertex = box.getVertex(0);
        Vec2 pos = body.getPosition();

        Rect groundRect = new VboRect(
                Math.abs(vertex.x) * 2 / scaleRate,
                Math.abs(vertex.y) * 2 / scaleRate
        );
        Mesh<Rect> boxOverlay = new Mesh<Rect>(groundRect);
        boxOverlay.setPos(pos.x / scaleRate, pos.y / scaleRate);

        boxOverlay.setColor(randomColor());

        addChild(boxOverlay);

        return boxOverlay;
    }

    private int randomColor() {
        return Color.argb(
                (int) ((Math.random() + 0.5) * 255),
                (int) ((Math.random() + 0.5) * 255),
                (int) ((Math.random() + 0.5) * 255),
                (int) ((Math.random() + 0.5) * 255)
        );
    }

}
