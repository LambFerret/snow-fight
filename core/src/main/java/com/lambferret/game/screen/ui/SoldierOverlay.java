package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.screen.ui.container.SoldierContainer;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.SilvanusPark;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.CustomInputProcessor;

import java.util.ArrayList;
import java.util.List;

public class SoldierOverlay extends AbstractOverlay {
    private Hitbox plate;
    private boolean isHidden = true;
    private boolean isSimplify = false;
    private final List<Soldier> soldiers = new ArrayList<>();
    private final List<SoldierContainer> hand = new ArrayList<>();
    //    private final HorizontalScroll scroll = new HorizontalScroll(Direction.DOWN, );
    private float locationX = 0.0F;
    private float locationY = 0.0F;
    private float width = GlobalSettings.currWidth * 2 / 3.0F;
    private float height = 200.0F;
    private SoldierContainer container;

    // TODO : 이부분은 좀 고정 사이즈를 가질 필요를 느낀다

    @Override
    public void create() {

        //=-=-=-=-=-=--=-=//
        for (int i = 0; i < 6; i++) {
            soldiers.add(new SilvanusPark());
        }
        //=-=-=-=-=-=--=-=//

        this.plate = new Hitbox(locationX, locationY, width, height);
//        scroll.create(this.plate);

        container = new SoldierContainer(soldiers);
        container.create(plate);
        hand.add(container);
    }


    private void switchInfo() {
        if (isSimplify) {
            container.standard();
            isSimplify = false;
        } else {
            container.simplify();
            isSimplify = true;
        }
    }

    @Override
    public void update(float delta) {

        if (CustomInputProcessor.pressedKeyUp(Input.Keys.Q)) {
            switchInfo();
        }
        if (CustomInputProcessor.pressedKeyUp(Input.Keys.W)) {
            this.plate.move(50, 50);
        }

        if (this.plate.isHovered) {
//            this.scroll.update(delta);
        }
        for (SoldierContainer soldier : hand) {
            soldier.update(delta);
        }
        this.plate.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
//        this.scroll.render(batch);
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

}
