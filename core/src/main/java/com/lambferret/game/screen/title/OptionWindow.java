package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.lambferret.game.screen.title.LoadAndSaveWindow.*;

public class OptionWindow extends Window {
    private static final Logger logger = LogManager.getLogger(OptionWindow.class.getName());

    Stage stage;
    private final Table table;
    private final CustomButton confirmButton;
    private final CustomButton cancelButton;
    boolean isTitle;

    public OptionWindow(Stage stage, boolean isTitle) {
        super("option", GlobalSettings.skin);
        this.stage = stage;
        this.isTitle = isTitle;
        stage.addActor(this);
        table = new Table();
        this.add(table);
        confirmButton = GlobalUtil.simpleButton("confirm", "confirm");
        cancelButton = GlobalUtil.simpleButton("cancel", "cancel");
        stage.addActor(confirmButton);
        stage.addActor(cancelButton);
        create();
    }

    public void create() {
        this.setBackground(new TextureRegionDrawable(AssetFinder.getTexture("hideButto2n")));
        this.setSize(SAVE_WINDOW_WIDTH, SAVE_WINDOW_HEIGHT);
        this.setPosition(SAVE_WINDOW_X, SAVE_WINDOW_Y);

        makeButton();
        makeOptionTable();
    }

    private void makeButton() {
        confirmButton.setSize(200, 100);
        confirmButton.setPosition(100, 100);
        confirmButton.setColor(Color.GREEN);
        cancelButton.setSize(200, 100);
        cancelButton.setPosition(300, 100);
        cancelButton.setColor(Color.RED);
        cancelButton.setDebug(true, true);

        confirmButton.addListener(Input.click(() -> {
                GlobalSettings.saveConfigJson();
                setVisible(false);
                makeOptionTable();
            })
        );
        cancelButton.addListener(Input.click(() -> {
                GlobalSettings.init();
                setVisible(false);
                makeOptionTable();
            })
        );
    }

    private void makeOptionTable() {
        table.clear();
        table.setSize(300, 500);
        table.setPosition(50, 50);
        table.setDebug(true, true);

        makeScreenOption();

        makeVolumeSlider(Volume.MASTER);
        makeVolumeSlider(Volume.BGM);
        makeVolumeSlider(Volume.EFFECT);
    }

    private void makeScreenOption() {
        CheckBox box = new CheckBox("fullscreen", GlobalSettings.skin);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GlobalSettings.font;
        box.setChecked(GlobalSettings.isFullscreen);
        Dialog dialog = new Dialog("", GlobalSettings.skin) {
            {
                text("restart to apply change");
                button("ok", true);
            }
        };
        box.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GlobalSettings.isFullscreen = ((CheckBox) actor).isChecked();
                dialog.show(stage);
            }
        });

        table.add(newLabel("full screen", labelStyle));
        table.add(box).row();
    }

    private void makeVolumeSlider(Volume volume) {
        Slider.SliderStyle style = new Slider.SliderStyle();
        style.knob = new TextureRegionDrawable(AssetFinder.getTexture("hideButton"));
        style.background = new TextureRegionDrawable(AssetFinder.getTexture("yellow"));
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GlobalSettings.font;
        Slider slider = new Slider(0, 100, 1, false, style);
        switch (volume) {

            case MASTER -> {
                slider.setValue(GlobalSettings.masterVolume);
                slider.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GlobalSettings.setMasterVolume((int) ((Slider) actor).getValue());
                    }
                });
            }
            case BGM -> {
                slider.setValue(GlobalSettings.bgmVolume);
                slider.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GlobalSettings.setBgmVolume((int) ((Slider) actor).getValue());
                    }
                });
            }
            case EFFECT -> {
                slider.setValue(GlobalSettings.effectVolume);
                slider.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GlobalSettings.setEffectVolume((int) ((Slider) actor).getValue());
                    }
                });
            }
        }
        table.add(newLabel(volume.toString(), labelStyle));
        table.add(slider).row();
    }

    @Override
    public void setVisible(boolean visible) {
        confirmButton.setVisible(visible);
        cancelButton.setVisible(visible);
        super.setVisible(visible);
    }

    enum Volume {
        MASTER,
        BGM,
        EFFECT
    }

}
