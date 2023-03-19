package com.lambferret.game.screen.ui;

import com.lambferret.game.setting.GlobalSettings;

public interface AbstractOverlay {
    float OVERLAY_WIDTH = GlobalSettings.currWidth / 3.0F;
    float OVERLAY_HEIGHT = 200.0F;
    float BAR_HEIGHT = 50.0F;

    void create();

    void setVisible(boolean visible);
}
