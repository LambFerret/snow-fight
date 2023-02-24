package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Direction;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.HorizontalScroll;
import com.lambferret.game.component.ScrollObserver;
import com.lambferret.game.screen.ui.container.SoldierContainer;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.SilvanusPark;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.CustomInputProcessor;

import java.util.ArrayList;
import java.util.List;

public class SoldierOverlay extends AbstractOverlay implements ScrollObserver {
    private Hitbox plate;
    private boolean isHidden = true;
    private boolean isSimplify = false;
    private List<Soldier> soldiers = new ArrayList<>();
    private final List<SoldierContainer> hand = new ArrayList<>();
    private final HorizontalScroll scroll = new HorizontalScroll(Direction.DOWN);

    // TODO : 이부분은 좀 고정 사이즈를 가질 필요를 느낀다
    private static final float OVERLAY_HEIGHT = 200.0F;

    @Override
    public void create() {
        int index = 0;

        //=-=-=-=-=-=--=-=//
        for (int i = 0; i < 6; i++) {
            soldiers.add(new SilvanusPark());
        }
        //=-=-=-=-=-=--=-=//

        this.plate = new Hitbox(0.0F, 0.0F, GlobalSettings.currWidth * 2 / 3.0F, OVERLAY_HEIGHT);
        scroll.create(this.plate);
        for (Soldier soldier : soldiers) {
            var container = new SoldierContainer(soldier, index++);
            container.create(plate);
            hand.add(container);
        }
    }

    private void switchInfo() {
        if (isSimplify) {
            for (SoldierContainer soldier : hand) {
                soldier.standard();
            }
            isSimplify = false;
        } else {
            for (SoldierContainer soldier : hand) {
                soldier.simplify();
            }
            isSimplify = true;
        }
    }

    @Override
    public void update(float delta) {

        if (CustomInputProcessor.pressedKeyUp(Input.Keys.Q)) {
            switchInfo();
        }
        if (this.plate.isHovered) {
            this.scroll.update(delta);
        }
        this.plate.update(delta);
        for (SoldierContainer soldier : hand) {
            soldier.update(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        this.scroll.render(batch);
        this.plate.render(batch);
        for (SoldierContainer soldier : hand) {
            soldier.render(batch);
        }
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
        isHidden = true;
    }

    @Override
    public void scroll(float value) {

    }
}
