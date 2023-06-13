package com.lambferret.game.component;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.lambferret.game.setting.GlobalSettings;

import java.util.List;

public class PagingTable extends Table {
    private static final int ROWS_PER_PAGE = 10;

    private List<CustomButton> buttons;
    private int currentPage;
    private Label pageLabel;
    Skin skin;
    Button leftButton;
    Button rightButton;

    public PagingTable(List<CustomButton> buttons) {
        super();
        this.buttons = buttons;
        this.skin = GlobalSettings.skin;
        this.currentPage = 0;

        leftButton = new TextButton("<", skin);
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentPage > 0) {
                    currentPage--;
                    populateTable();
                }
            }
        });

        rightButton = new TextButton(">", skin);
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ((currentPage + 1) * ROWS_PER_PAGE < buttons.size()) {
                    currentPage++;
                    populateTable();
                }
            }
        });

        this.pageLabel = new Label("press", skin);


        populateTable();
    }

    private void addPagingButton() {
        add(leftButton).expandX().left();
        add(this.pageLabel).expandX();
        add(rightButton).expandX().right();
        row();
    }

    private void populateTable() {
        clearChildren();
        // Remove all existing rows
        addPagingButton();
        // Add rows from the current page
        for (int i = currentPage * ROWS_PER_PAGE; i < Math.min((currentPage + 1) * ROWS_PER_PAGE, buttons.size()); i++) {
            CustomButton button = buttons.get(i);
            add(button).expandX().height(button.getLabel().getHeight()).colspan(3).left();
            row();
        }

        pageLabel.setText((currentPage + 1) + "/" + ((buttons.size() - 1) / ROWS_PER_PAGE + 1));
    }
}

