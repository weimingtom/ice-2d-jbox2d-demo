package com.jason.scenes;

import com.jason.R;
import ice.graphic.texture.BitmapTexture;
import ice.node.Overlay;
import ice.node.OverlayParent;
import ice.node.widget.BitmapOverlay;
import ice.node.widget.Grid;

public class PokerOverlay extends OverlayParent<Overlay> {

    public PokerOverlay() {

        front = new BitmapOverlay(R.drawable.pai_a2);

        back = new Grid(front.getWidth(), front.getHeight(), false);

        back.setTexture(
                new BitmapTexture(R.drawable.poker_back)
        );

        addChildren(back, front);
    }

    private Grid front;
    private Grid back;
}
