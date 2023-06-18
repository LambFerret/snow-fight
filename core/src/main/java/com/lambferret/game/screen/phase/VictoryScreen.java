package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VictoryScreen extends Window implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(VictoryScreen.class.getName());
    TextButton textButton;
    Stage stage;
    Table info;
    CustomButton confirmButton;

    public VictoryScreen() {
        super("VictoryScreen", GlobalSettings.skin);
        textButton = new TextButton("VictoryScreen", GlobalSettings.skin);
        textButton.setPosition(300, 300);
        stage = ActionPhaseScreen.stage;
        this.setPosition(100, 100);
        this.setSize(800, 500);
        info = makeInfo();
        confirmButton = makeConfirmButton();


        this.add(info);
        this.add(confirmButton);
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

    private CustomButton makeConfirmButton() {
        CustomButton button = GlobalUtil.simpleButton("confirm", "go back to Military");
        button.setPosition(600, 400);
        button.setSize(400, 400);
        button.addListener(Input.click(this::confirm));
        return button;
    }

    private void confirm() {
        PhaseScreen.end();
        SnowFight.player.setDay(SnowFight.player.getDay() + 1);
        ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
        executePhase();
    }

    private Table makeInfo() {
        Table table = new Table();
        table.setSkin(GlobalSettings.skin);
        table.add("You have been defeated!").row();
        table.add(label1()).width(100).height(50).row();
        table.add(label2()).width(100).height(50).row();
        table.add(label3()).width(100).height(50).row();
        table.add(label4()).width(100).height(50).row();
        return table;
    }

    private Label label1() {
        Label label = new Label("Victory!", GlobalSettings.skin);
        return label;
    }

    private Label label2() {
        Label label = new Label("Money earned : ", GlobalSettings.skin);
        return label;
    }

    private Label label3() {
        Label label = new Label("Affinity get upper : ", GlobalSettings.skin);
        return label;
    }

    private Label label4() {
        Label label = new Label("Affinity get middle : ", GlobalSettings.skin);
        return label;
    }

    private Label label5() {
        Label label = new Label("Affinity get down : ", GlobalSettings.skin);
        return label;
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
