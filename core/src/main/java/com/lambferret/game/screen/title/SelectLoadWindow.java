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

public class SelectLoadWindow extends LoadAndSaveWindow {
    private static final Logger logger = LogManager.getLogger(SelectLoadWindow.class.getName());
    public static final String LOAD_GAME_EXE = "LOAD-GAME.exe";

    public SelectLoadWindow(Stage stage) {
        super(stage, LOAD_GAME_EXE); //text.getLoadData()
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
                    showEmptyDialog(index);
                }
            }
        };
    }

    private void showEmptyDialog(int index) {
        Dialog dialog = new Dialog(LOAD_GAME_EXE, skin) {
            {
                text(text.getOverrideLoadConfirm());
                button(text.getYes(), true);
                button(text.getNo(), false);
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
