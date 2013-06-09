package com.jason.graphics;

import ice.engine.App;
import ice.engine.GameView;
import ice.engine.GlRenderer;
import ice.graphic.projection.PerspectiveProjection;

import static android.opengl.GLES10.*;
import static javax.microedition.khronos.opengles.GL11.GL_CULL_FACE;
import static javax.microedition.khronos.opengles.GL11.GL_LIGHTING;
import static javax.microedition.khronos.opengles.GL11.GL_LINE_SMOOTH;
import static javax.microedition.khronos.opengles.GL11.GL_NICEST;
import static javax.microedition.khronos.opengles.GL11.GL_PERSPECTIVE_CORRECTION_HINT;

/**
 * User: jason
 * Date: 12-4-17
 * Time: 上午10:25
 */
public class Box2DView extends GameView {

    public Box2DView(App app) {
        super(app);

        setDebugFlags(DEBUG_CHECK_GL_ERROR);
    }

    @Override
    protected GlRenderer onCreateGlRenderer() {
        return new Box2DRenderer();
    }


    private class Box2DRenderer extends GlRenderer {

        public Box2DRenderer() {
            super(
                    new PerspectiveProjection(45)
            );
        }

        @Override
        protected void onInit() {
            glEnable(GL_CULL_FACE);

            glDisable(GL_LIGHTING);

            glEnable(GL_LINE_SMOOTH);

            glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

            //Rect.setSharedMode(GL_LINE_STRIP);

//            gl.glEnable(GL_BLEND);
//            gl.glBlendFunc(GL_ONE, GL_ONE);
        }

    }

}
