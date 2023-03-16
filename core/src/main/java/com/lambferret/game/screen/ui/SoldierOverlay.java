package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.HorizontalScroll;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.screen.ui.container.SoldierContainer;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.CustomInputProcessor;

import java.util.ArrayList;
import java.util.List;

public class SoldierOverlay extends AbstractOverlay {
    private Hitbox plate;
    private boolean isHidden = true;
    private List<Soldier> soldiers = new ArrayList<>();
    private final HorizontalScroll scroll = new HorizontalScroll(Direction.DOWN);
    private static final float locationX = 0.0F;
    private static final float locationY = 0.0F;
    private static final float width = OVERLAY_WIDTH * 2;
    private SoldierContainer container;
    private boolean isInit = false;

    // TODO : 이부분은 좀 고정 사이즈를 가질 필요를 느낀다

    @Override
    public void create() {

        this.plate = new Hitbox(locationX, locationY, width, OVERLAY_HEIGHT);
        scroll.create(this.plate);

    }

    private void setPlayer() {
        soldiers = SnowFight.player.getSoldiers();
        container = new SoldierContainer(soldiers);
        container.create(plate);
    }

    @Override
    public void update() {

        if (CustomInputProcessor.pressedKeyUp(Input.Keys.Q)) {
            container.switchInfo();
        }
        if (CustomInputProcessor.pressedKeyUp(Input.Keys.W)) {
            this.plate.move(50, 50);
        }

        if (this.plate.isHovered) {
            this.scroll.update();
        }

        container.update(scroll.getPercent());
        this.plate.update();

    }

    @Override
    public void render() {
        if (!isInit) {
            setPlayer();
            isInit = true;
        }

        this.scroll.render();
        this.plate.render();
        container.render();
    }

    @Override
    public void hide(Direction direction) {
        if (isHidden) return;
        this.plate.hide(direction);
        isHidden = true;
    }

    @Override
    public void show(boolean instantly) {
        if (!isHidden) return;
        if (instantly) {
            this.plate.showInstantly();
        } else {
            this.plate.show();
        }
        isHidden = false;
    }

}
