package com.lambferret.game.component;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.lambferret.game.setting.GlobalSettings;

public class CustomDialog extends Dialog {
    private final int ID;

    public CustomDialog(String title, int ID) {
        super(title, GlobalSettings.skin);
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

}
