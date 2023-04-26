package com.lambferret.game.screen.title;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.SnowFight;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.ScreenConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectLoadWindow extends LoadAndSaveWindow {
    private static final Logger logger = LogManager.getLogger(SelectLoadWindow.class.getName());

    public SelectLoadWindow(Stage stage) {
        super(stage, "load");
        this.stage = stage;
    }

    @Override
    protected ClickListener addLoadListener(int index, boolean isExist) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isExist) {
                    SaveLoader.load(index);
                    SnowFight.setPlayer();
                    ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
                } else {
                    logger.info("cannot select empty slot");
                }
            }
        };
    }
}
