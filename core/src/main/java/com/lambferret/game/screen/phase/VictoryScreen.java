package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;

public class VictoryScreen extends ResultWindow {

    protected Table makeConfirmGroup() {
        Table table = new Table();
        CustomButton button = GlobalUtil.simpleButton("confirm", "go back to Military");
        button.addListener(Input.click(this::confirm));
        table.add(button);
        return table;
    }

}
