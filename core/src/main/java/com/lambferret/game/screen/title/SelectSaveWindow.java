package com.lambferret.game.screen.title;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.WindowDialog;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectSaveWindow extends LoadAndSaveWindow {
    private static final Logger logger = LogManager.getLogger(SelectSaveWindow.class.getName());
    public static final String NEW_GAME_EXE = "NEW-GAME.exe";

    public SelectSaveWindow(Stage stage) {
        super(stage, NEW_GAME_EXE);
        this.stage = stage;
    }

    @Override
    protected ClickListener addLoadListener(int index, boolean isExist) {
        return Input.click(() -> {
                if (!isExist) {
                    showEmptyDialog(index);
                } else {
                    showExistDialog(index);
                }
            }
        );
    }

    private void showExistDialog(int index) {
        Dialog dialog = new WindowDialog(NEW_GAME_EXE, WindowDialog.WarnLevel.ERROR, text.getOverrideDeleteSaveConfirm()) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    SaveLoader.deleteSave(index);
                    SaveLoader.makeNewSave(index);
                    SaveLoader.load(index);
                    SnowFight.setPlayer();
                    ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
                }
            }
        };
        dialog.show(stage);
    }

    private void showEmptyDialog(int index) {
        Dialog dialog = new WindowDialog(NEW_GAME_EXE, WindowDialog.WarnLevel.ERROR, text.getOverrideSaveConfirm()) {
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
