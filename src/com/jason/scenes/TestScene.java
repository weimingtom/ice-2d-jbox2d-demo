package com.jason.scenes;

import android.graphics.Color;
import ice.engine.Scene;
import ice.node.widget.RectOverlay;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午5:49
 */
public class TestScene extends Scene {

    public RectOverlay showBoxBody(Body body, float scaleRate) {

        PolygonShape box = (PolygonShape) body.m_fixtureList.getShape();

        Vec2 vertex = box.getVertex(0);
        Vec2 pos = body.getPosition();

        RectOverlay boxOverlay = new RectOverlay(
                Math.abs(vertex.x) * 2 / scaleRate,
                Math.abs(vertex.y) * 2 / scaleRate
        );

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
