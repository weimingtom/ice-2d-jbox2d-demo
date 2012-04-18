package com.jason.scenes;

import android.graphics.Color;
import ice.engine.Scene;
import ice.model.vertex.VboRect;
import ice.node.widget.Mesh;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午4:48
 */
public class ApplyForceScene extends Scene {

    @Override
    protected void onCreate() {
        super.onCreate();

        Mesh<VboRect> mesh = new Mesh<VboRect>(new VboRect(100, 100));
        mesh.setColor(Color.RED);
        mesh.setPos(getWidth() / 2, getHeight() / 2);
        addChild(mesh);
    }
}
