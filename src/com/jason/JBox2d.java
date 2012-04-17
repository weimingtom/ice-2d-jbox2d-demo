package com.jason;

import com.jason.graphics.Box2DView;
import com.jason.scene_providers.Main;
import ice.engine.Game;
import ice.engine.GameView;
import ice.engine.SceneProvider;

public class JBox2d extends Game {

    @Override
    protected Class<? extends SceneProvider> getEntry() {
        return Main.class;
    }

    @Override
    protected GameView buildGameView() {
        return new Box2DView(this);
    }

}
