package com.jason.scene_providers;

import com.jason.scenes.DemoScene;
import ice.engine.Scene;
import ice.engine.SceneProvider;

public class Demo extends SceneProvider {

    @Override
    protected Scene onCreateScene() {
        return new DemoScene();
    }

}
