package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.screen.title.SelectLoadWindow;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;

public class DefeatScreen extends ResultWindow {

    @Override
    protected Table makeConfirmGroup() {
        Table table = new Table();
        table.setSkin(GlobalSettings.skin);
        table.add("You have been defeated!").row();
        table.add(button1()).height(50).uniformX().row();
        table.add(button2()).height(50).uniformX().row();
        table.add(button3()).height(50).uniformX().row();
        table.add(button4()).height(50).uniformX().row();
        return table;
    }

    private CustomButton button1() {
        // 불이익 감수
        var button = GlobalUtil.simpleButton("info", "Accept Disadvantage and Continue");
        button.addListener(Input.click(() -> {
                SnowFight.player.setDownAffinityBy(-30);
                SnowFight.player.setUpperAffinityBy(-30);
                SnowFight.player.setMoneyBy(-500);
                confirm();
            }
        ));
        return button;
    }

    private CustomButton button2() {
        // 리트라이
        var button = GlobalUtil.simpleButton("Retry", "Retry");
        button.addListener(Input.click(() -> {
                // warning
                ScreenConfig.screenChanger(ScreenConfig.AddedScreen.PHASE_SCREEN);
                executePhase();
            }
        ));
        return button;
    }

    private CustomButton button3() {
        // 로드
        var button = GlobalUtil.simpleButton("LOAD", "LOAD");
        button.addListener(Input.click(() -> {
                new SelectLoadWindow(Overlay.uiSpriteBatch);
                executePhase();
            }
        ));
        return button;
    }

    private CustomButton button4() {
        // 메인메뉴
        var button = GlobalUtil.simpleButton("info", "Return to Main Menu");
        button.addListener(Input.click(() -> {
                executePhase();
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.TITLE_SCREEN;
            }
        ));
        return button;
    }

}
