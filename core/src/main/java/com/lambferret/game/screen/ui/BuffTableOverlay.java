package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BuffTableOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(BuffTableOverlay.class.getName());
    private static final GroundText text;
    private final Stage stage;

    private static final float BUFF_TABLE_WIDTH = 300;
    private static final float BUFF_TABLE_HEIGHT = 150;
    private static final float BUFF_TABLE_X = 100;
    private static final float BUFF_TABLE_Y = 100;
    private static final float BUFF_TABLE_EACH_WIDTH = 25;
    private static final float BUFF_TABLE_EACH_HEIGHT = 50;
    private final Container<CustomButton> infoContainer = new Container<>();

    Player player;

    public BuffTableOverlay(Stage stage) {
        this.stage = stage;
        this.setSize(BUFF_TABLE_WIDTH, BUFF_TABLE_HEIGHT);
        this.setPosition(BUFF_TABLE_X, BUFF_TABLE_Y);
        infoContainer.setVisible(false);

        infoContainer.setSize(100, 100);
        infoContainer.setPosition((GlobalSettings.currWidth + infoContainer.getWidth()) / 2, 200);
        pack();

        this.setDebug(true, true);

        stage.addActor(infoContainer);
        stage.addActor(this);
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        makeTable();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        if (type == Item.Type.BUFF) {
            makeTable();
            stage.addActor(this);
        }
    }

    private CustomButton makeIcon(Buff buff) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();

        style.up = GlobalUtil.getNinePatchDrawableFromTexture("itemUI_description", 5);
        style.font = GlobalSettings.font;

        return new CustomButton(buff.getDescription(), style);

    }

    private void makeTable() {
        clear();
        for (Buff buff : PhaseScreen.buffList) {
            Image buffIcon = new Image(buff.getTexture());

            buffIcon.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    if (pointer == -1) {
                        infoContainer.setVisible(true);
                        infoContainer.setActor(makeIcon(buff));
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    infoContainer.setVisible(false);
                }
            });
            add(buffIcon).size(BUFF_TABLE_EACH_WIDTH, BUFF_TABLE_EACH_HEIGHT);
        }
    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }

}
