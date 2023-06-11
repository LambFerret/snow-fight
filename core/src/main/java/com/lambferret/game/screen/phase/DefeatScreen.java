package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.title.SelectLoadWindow;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefeatScreen extends Window implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(DefeatScreen.class.getName());
    TextButton textButton;
    Stage stage;
    Table info;


    public DefeatScreen() {
        super("DefeatScreen", GlobalSettings.skin);
        textButton = new TextButton("DefeatScreen", GlobalSettings.skin);
        textButton.setPosition(300, 300);
        stage = ActionPhaseScreen.stage;
        this.setPosition(100, 100);
        this.setSize(800, 500);
        info = makeInfo();
        this.add(info);
    }

    @Override
    public void onPlayerReady() {

    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void startPhase() {
        Overlay.uiSpriteBatch.addActor(this);
    }

    @Override
    public void executePhase() {
        this.remove();
    }

    private Table makeInfo() {
        Table table = new Table();
        table.setSkin(GlobalSettings.skin);
        table.add("You have been defeated!").row();
        table.add(button1()).width(100).height(50).row();
        table.add(button2()).width(100).height(50).row();
        table.add(button3()).width(100).height(50).row();
        table.add(button4()).width(100).height(50).row();
        return table;
    }

    private CustomButton button1() {
        // 불이익 감수
        var button = GlobalUtil.simpleButton("info", "Accept Disadvantage and Continue");
        button.addListener(Input.click(() -> {
                SnowFight.player.setDownAffinityBy(-30);
                SnowFight.player.setUpperAffinityBy(-30);
                SnowFight.player.setMiddleAffinityBy(-30);
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
                executePhase();
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
                new SelectLoadWindow(stage);
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

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
