package com.lambferret.game.screen.title;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.SnowFight;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.ScreenConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectSaveWindow extends LoadAndSaveWindow {
    private static final Logger logger = LogManager.getLogger(SelectSaveWindow.class.getName());

    public SelectSaveWindow(Stage stage) {
        super(stage, "save");
        this.stage = stage;
    }

    @Override
    protected ClickListener addLoadListener(int index, boolean isExist) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isExist) {
                    showDialog(index);
                } else {
                    logger.info("cannot select empty slot");
                }
            }

        };
    }

    private void showDialog(int index) {
        Dialog dialog = new Dialog("Confirmation", skin) {
            {
                text("Are you sure?");
                button("Yes", true);
                button("No", false);
            }

            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    SaveLoader.makeNewSave(index);
                    SaveLoader.load(index);
                    SnowFight.setPlayer();
                    ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
                }
            }
        };
        dialog.show(stage);
    }

}
