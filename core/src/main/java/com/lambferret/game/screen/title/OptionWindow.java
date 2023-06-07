package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.setting.FontConfig;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.Setting;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class OptionWindow extends Window {
    private static final Logger logger = LogManager.getLogger(OptionWindow.class.getName());
    static Texture background = AssetFinder.getTexture("optionBackground");
    public static final int OPTION_WINDOW_WIDTH = GlobalSettings.currWidth;
    public static final int OPTION_WINDOW_HEIGHT = GlobalSettings.currHeight;
    public static final int OPTION_WINDOW_INIT_X = 0;
    public static final int OPTION_WINDOW_INIT_Y = -OPTION_WINDOW_HEIGHT;
    public static final int OPTION_EACH_WIDTH = OPTION_WINDOW_WIDTH / 4;
    public static final int OPTION_EACH_HEIGHT = OPTION_WINDOW_HEIGHT / 13;
    private static final TitleMenuText text = LocalizeConfig.uiText.getTitleMenuText();
    Stage stage;
    private final Table table;
    Dialog dialog;
    boolean isTitle;
    BitmapFont font;
    int currentLanguageIndex; // TODO need debug

    public OptionWindow(Stage stage, boolean isTitle) {
        super(text.getOption(), GlobalSettings.skin);
        this.stage = stage;
        this.isTitle = isTitle;
        stage.addActor(this);
        this.setBackground(new TextureRegionDrawable(background));
        font = FontConfig.optionFont;
        table = new Table() {
            @Override
            public <T extends Actor> Cell<T> add(T actor) {
                return super.add(actor).height(OPTION_EACH_HEIGHT).width(OPTION_EACH_WIDTH);
            }

            public <T extends Actor> Cell<T> add(T actor, int width) {
                return super.add(actor).height(OPTION_EACH_HEIGHT).width(width);
            }
        };
        this.add(table);
        dialog = new Dialog("", GlobalSettings.skin) {
            {
                text(text.getRestartToChange());
                button(text.getOk(), true);
            }
        };
        create();
    }

    public void create() {
        this.setSize(OPTION_WINDOW_WIDTH, OPTION_WINDOW_HEIGHT);
        this.setPosition(OPTION_WINDOW_INIT_X, OPTION_WINDOW_INIT_Y);

        makeOptionTable();
    }

    private void makeOptionTable() {
        table.clearChildren();
        table.pad(OPTION_EACH_HEIGHT, 10, OPTION_EACH_HEIGHT, 10);
        table.setSize(OPTION_WINDOW_WIDTH, OPTION_WINDOW_HEIGHT);

        makeScreenOption();
        makeLanguageSettings();

        makeVolumeSlider(Volume.MASTER);
        makeVolumeSlider(Volume.BGM);
        makeVolumeSlider(Volume.EFFECT);

        makeButton();
    }

    private void makeScreenOption() {
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();
        style.font = font;
        CheckBox box = new CheckBox(text.getFullscreen(), style);
        box.setChecked(GlobalSettings.isFullscreen);
        box.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GlobalSettings.isFullscreen = ((CheckBox) actor).isChecked();
                dialog.show(stage);
            }
        });
        table.add(newLabel(text.getFullscreen()));
        table.add(box).row();
    }

    private void makeLanguageSettings() {
        currentLanguageIndex = GlobalSettings.language.ordinal(); // start with the first language

        var languages = Arrays.stream(Setting.Language.values()).map(Setting.Language::getLocale).toArray(String[]::new);
        Table languageTable = new Table();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;

        TextButton leftArrow = new TextButton("<", style);
        TextButton rightArrow = new TextButton(">", style);
        Label languageLabel = newLabel(languages[currentLanguageIndex]);

        leftArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLanguageIndex--;
                if (currentLanguageIndex < 0) currentLanguageIndex = languages.length - 1;
                languageLabel.setText(languages[currentLanguageIndex]);
                GlobalSettings.language = Setting.Language.fromLocale(languages[currentLanguageIndex]);
            }
        });

        rightArrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLanguageIndex++;
                if (currentLanguageIndex >= languages.length) currentLanguageIndex = 0;
                languageLabel.setText(languages[currentLanguageIndex]);
                GlobalSettings.language = Setting.Language.fromLocale(languages[currentLanguageIndex]);
            }
        });

        languageTable.add(leftArrow);
        languageTable.add(languageLabel).expandX().fillX().center();
        languageTable.add(rightArrow);

        table.add(newLabel(text.getLanguage()));
        table.add(languageTable).row();
    }

    private void makeVolumeSlider(Volume volume) {
        Table volumeTable = new Table();
        Slider.SliderStyle style = new Slider.SliderStyle();
        style.knob = new TextureRegionDrawable(AssetFinder.getTexture("soundKnob"));
        style.knobBefore = new TextureRegionDrawable(AssetFinder.getTexture("soundKnobBefore"));
        style.knobAfter = new TextureRegionDrawable(AssetFinder.getTexture("soundKnobAfter"));
        style.background = new TextureRegionDrawable(AssetFinder.transparentTexture());
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        TextButton button = new TextButton("", buttonStyle);

        Slider slider = new Slider(0, 100, 1, false, style);
        switch (volume) {
            case MASTER -> {
                button.setText(String.valueOf(GlobalSettings.masterVolume));
                slider.setValue(GlobalSettings.masterVolume);
                slider.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GlobalSettings.setMasterVolume((int) ((Slider) actor).getValue());
                    }
                });
                table.add(newLabel(text.getMaster()));
            }
            case BGM -> {
                button.setText(String.valueOf(GlobalSettings.bgmVolume));
                slider.setValue(GlobalSettings.bgmVolume);
                slider.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GlobalSettings.setBgmVolume((int) ((Slider) actor).getValue());
                    }
                });
                table.add(newLabel(text.getBgm()));
            }
            case EFFECT -> {
                button.setText(String.valueOf(GlobalSettings.effectVolume));
                slider.setValue(GlobalSettings.effectVolume);
                slider.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        GlobalSettings.setEffectVolume((int) ((Slider) actor).getValue());
                    }
                });
                table.add(newLabel(text.getEffect()));

            }
        }
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                button.setText(String.valueOf((int) ((Slider) actor).getValue()));
            }
        });
        volumeTable.add(slider).center().expandX();
        volumeTable.add(button).padRight(10).width(30);
        table.add(volumeTable).row();
    }

    private void makeButton() {
        CustomButton confirmButton = button();
        CustomButton cancelButton = button();
        confirmButton.setText(text.getConfirm());
        cancelButton.setText(text.getCancel());

        confirmButton.addListener(Input.click(() -> {
                GlobalSettings.saveConfigJson();
                makeOptionTable();
                close();
            })
        );
        cancelButton.addListener(Input.click(() -> {
                GlobalSettings.init();
                makeOptionTable();
                close();
            })
        );


        Table buttonTable = new Table();
        buttonTable.add(cancelButton).expandX();
        buttonTable.add(confirmButton).expandX();
        table.add();
        table.row();
        table.add();
        table.add(buttonTable);
    }

    private CustomButton button() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = font;
        style.up = new TextureRegionDrawable(AssetFinder.transparentTexture());
        var button = new CustomButton("", style);
        button.addListener(Input.hover(() -> {
                AssetFinder.getSound("button_click").play(GlobalSettings.effectVolume);
                button.getLabel().setColor(Color.GOLD);
            },
            () -> button.getLabel().setColor(Color.WHITE)
        ));
        return button;
    }

    public void open() {
        this.setVisible(true);
        this.toFront();
        this.addAction(Actions.moveTo(0, 0, 0.1F));
    }

    public void close() {
        this.addAction(Actions.sequence(
            Actions.moveTo(OPTION_WINDOW_INIT_X, OPTION_WINDOW_INIT_Y, 0.1F),
            Actions.run(() -> this.setVisible(false))
        ));
    }

    protected Label newLabel(String text) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        Label label = super.newLabel(text, style);
        label.setAlignment(Align.center);
        return label;
    }

    enum Volume {
        MASTER,
        BGM,
        EFFECT
    }

}
