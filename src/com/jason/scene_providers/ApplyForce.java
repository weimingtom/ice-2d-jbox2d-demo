package com.jason.scene_providers;

import com.jason.scenes.ApplyForceScene;
import ice.engine.Scene;

/**
 * User: jason
 * Date: 12-4-18
 * Time: 下午4:48
 */
public class ApplyForce extends Test {

    @Override
    protected Scene onCreateScene() {
        return new ApplyForceScene();
    }


}
