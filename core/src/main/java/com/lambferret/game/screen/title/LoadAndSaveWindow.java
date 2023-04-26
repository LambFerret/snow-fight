package com.lambferret.game.screen.title;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;

public abstract class LoadAndSaveWindow extends Window {
    public static final int SAVE_WINDOW_WIDTH = 800;
    public static final int SAVE_WINDOW_HEIGHT = 400;
    public static final int SAVE_WINDOW_X = (GlobalSettings.currWidth - SAVE_WINDOW_WIDTH) / 2;
    public static final int SAVE_WINDOW_Y = (GlobalSettings.currHeight - SAVE_WINDOW_HEIGHT) / 2;

    protected Skin skin;
    protected Stage stage;
    Table table = new Table();

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
        this.clear();
        this.table.clear();
        makeTable();
        this.add(table);
    }

    protected void makeTable() {
        for (int i = 0; i < 3; i++) {
            table.add(makeButton(i)).pad(10);
            table.row();
        }
    }

    protected ImageTextButtonStyle getButtonStyle() {
        ImageTextButtonStyle style = new ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("save window button"));
        style.font = GlobalSettings.font;
        return style;
    }

    protected CustomButton makeButton(int index) {
        boolean isExist = SaveLoader.isSaveExist(index);
        String label = (isExist ? "load " : "new ") + index;
        CustomButton button = new CustomButton(label, getButtonStyle());
        button.addListener(addLoadListener(index, isExist));
        return button;
    }

    abstract protected ClickListener addLoadListener(int index, boolean isExist);

}
