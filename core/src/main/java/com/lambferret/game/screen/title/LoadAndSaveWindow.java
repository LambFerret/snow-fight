package com.lambferret.game.screen.title;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.component.UnderlineLabel;
import com.lambferret.game.component.WindowDialog;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.FontConfig;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class LoadAndSaveWindow extends Window {
    private static final Logger logger = LogManager.getLogger(LoadAndSaveWindow.class.getName());

    protected static final TitleMenuText text;
    public static final int SAVE_WINDOW_WIDTH = 800;
    public static final int SAVE_WINDOW_HEIGHT = 400;
    public static final int SAVE_WINDOW_X = (GlobalSettings.currWidth - SAVE_WINDOW_WIDTH) / 2;
    public static final int SAVE_WINDOW_Y = (GlobalSettings.currHeight - SAVE_WINDOW_HEIGHT) / 2;

    protected Skin skin;
    protected Stage stage;

    public LoadAndSaveWindow(Stage stage, String stageName) {
        super(stageName, GlobalSettings.skin);
        this.stage = stage;
        this.skin = GlobalSettings.skin;

        this.setSize(SAVE_WINDOW_WIDTH, SAVE_WINDOW_HEIGHT);
        this.setPosition(SAVE_WINDOW_X, SAVE_WINDOW_Y);

        create();
        stage.addActor(this);
    }

    public void create() {
        this.clearChildren();
        this.add(makeTable());
    }

    protected Table makeTable() {
        Table table = new Table();
        for (int i = 0; i < 3; i++) {
            if (SaveLoader.isSaveExist(i)) {
                table.add(existCell(i));
            } else {
                table.add(emptyCell(i));
            }
            table.row();
        }
        return table;
    }

    private Table existCell(int index) {
        Table table = new Table();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontConfig.uiFont;

        SaveLoader.SaveFileInfo info = SaveLoader.info(index);
        Date date = new Date(info.getLastModified());
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String strLabel = "SLOT : " + index + "\n"
            + "last played time : " + dateString + "\n"
            + "day : " + info.getDay();
        UnderlineLabel label = new UnderlineLabel(strLabel, style.font); // TODO : look better
        TextButton button = new TextButton("", style);
        button.setLabel(label);
        button.addListener(Input.hover(
            () -> label.setUnderline(true), () -> label.setUnderline(false)
        ));
        button.addListener(addLoadListener(index, true));
        Image trashButton = new Image(AssetFinder.getTexture("trashbin"));
        trashButton.addListener(Input.click(() -> {
            showExistDialog(index);
        }));
        table.add(button).size(SAVE_WINDOW_WIDTH * 2 / 3F, SAVE_WINDOW_HEIGHT / 3F);
        table.add(trashButton).size(50, 50);
        return table;
    }

    private Table emptyCell(int index) {
        Table table = new Table();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = FontConfig.uiFont;
        TextButton button = new TextButton(text.getNewGame(), style);
        button.addListener(addLoadListener(index, false));
        table.add(button).size(SAVE_WINDOW_WIDTH, SAVE_WINDOW_HEIGHT / 3F);
        return table;
    }

    abstract protected ClickListener addLoadListener(int index, boolean isExist);

    private void showExistDialog(int index) {
        Dialog dialog = new WindowDialog("", WindowDialog.WarnLevel.ERROR, text.getDeleteConfirm()) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    SaveLoader.deleteSave(index);
                    create();
                }
            }
        };
        dialog.show(stage);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) logger.info(" SYSTEM : " + getClass().getSimpleName().replaceAll("(?<=.)([A-Z])", " $1") + " Screen ");
    }

    static {
        text = LocalizeConfig.uiText.getTitleMenuText();
    }

}
