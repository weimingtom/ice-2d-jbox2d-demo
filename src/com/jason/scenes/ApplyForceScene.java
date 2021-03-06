package com.jason.scenes;

import android.graphics.Color;
import ice.model.vertex.Rect;
import ice.node.widget.TextOverlay;

import static javax.microedition.khronos.opengles.GL10.GL_TRIANGLES;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午4:48
 */
public class ApplyForceScene extends TestScene {

    public ApplyForceScene() {

        TextOverlay textOverlay = new TextOverlay(200, 20);
        textOverlay.setColor(Color.YELLOW);
        textOverlay.setText("ApplyForce");

        Rect vertexData = textOverlay.getVertexData();
        vertexData.setMode(GL_TRIANGLES);

        textOverlay.setPos(1024 / 2, 768 / 2);

        addChild(textOverlay);

    }

}
