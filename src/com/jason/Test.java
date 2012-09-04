package com.jason;

import com.jason.graphics.Box2DView;
import com.jason.scene_providers.Demo;
import ice.engine.Game;
import ice.engine.GameView;
import ice.engine.SceneProvider;

public class Test extends Game {

    @Override
    protected Class<? extends SceneProvider> getEntry() {
        return Demo.class;
    }

    @Override
    protected GameView buildGameView() {
        return new Box2DView(this);
    }

}
