package com.lambferret.game.screen.ui.container;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.HorizontalScroll;
import com.lambferret.game.component.ScrollObserver;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SoldierContainer implements ScrollObserver {
    private static final Logger logger = LogManager.getLogger(SoldierContainer.class.getName());
    private final Hitbox containerBox;
    private Hitbox plate;
    private Hitbox child = new Hitbox();
    private HorizontalScroll scroll;
    private Texture texture;

    private static final float EXTEND_WIDTH = 300.0F;
    private static float EXTEND_HEIGHT;
    private static final float SHRINK_WIDTH = EXTEND_WIDTH;
    private static float SHRINK_HEIGHT;
    private static final float VERTICAL_SPACING = 10.0F;
    private static final float HORIZONTAL_SPACING = 5.0F;
    private float plateInitX;
    private float plateInitY;
    private float plateInitWidth;
    private float plateInitHeight;
    private float containerInitX;
    private float containerInitY;
    private float containerInitWidth;
    private float containerInitHeight;
    private final List<Soldier> soldiers;
    private boolean isStandard = true;


    public SoldierContainer(List<Soldier> soldiers) {
        this.soldiers = soldiers;
        scroll = new HorizontalScroll(Direction.DOWN, this);

        this.containerBox = new Hitbox();
    }

    public void create(Hitbox plate) {
        this.plate = plate;

        plateInitX = plate.getX();
        plateInitY = plate.getY();
        plateInitWidth = plate.getWidth();
        plateInitHeight = plate.getHeight();

        this.containerBox.move(plateInitX, /* scroll height */25.0F);
        this.containerBox.resize(plateInitWidth, plateInitHeight - 25.0F);

        containerInitX = containerBox.getX();
        containerInitY = containerBox.getY();
        containerInitWidth = containerBox.getWidth();
        containerInitHeight = containerBox.getHeight();

        EXTEND_HEIGHT = containerInitHeight - (VERTICAL_SPACING * 2);
        SHRINK_HEIGHT = (containerInitHeight - (VERTICAL_SPACING * 3)) / 2;

        this.standard();
    }

    public void render(SpriteBatch batch) {
        for (Soldier soldier : soldiers) {
            soldier.render(batch);
        }

        this.containerBox.render(batch);
    }

    public void update(float delta) {

        this.containerBox.update(delta);
        for (Soldier soldier : soldiers) {
            soldier.update(delta);
        }

    }

    public void standard() {
        for (int index = 0; index < soldiers.size(); index++) {
            float x = containerBox.getX() + HORIZONTAL_SPACING * (index + 1) + EXTEND_WIDTH * index;
            float y = containerBox.getY() + VERTICAL_SPACING;
            float width = EXTEND_WIDTH;
            float height = EXTEND_HEIGHT;
            this.child.resize(width, height);
            this.child.move(x, y);
            soldiers.get(index).setOffset(this.child, true);

        }
        isStandard = true;
    }

    public void simplify() {
        for (int index = 0; index < soldiers.size(); index++) {
            int odd = index / 2;
            float x = containerBox.getX() + HORIZONTAL_SPACING * (odd + 1) + SHRINK_WIDTH * odd;
            float y = (index % 2 == 0)
                ? containerBox.getY() + VERTICAL_SPACING * 2 + SHRINK_HEIGHT
                : containerBox.getY() + VERTICAL_SPACING;
            float width = SHRINK_WIDTH;
            float height = SHRINK_HEIGHT;
            this.child.resize(width, height);
            this.child.move(x, y);
            soldiers.get(index).setOffset(this.child, false);
        }
        isStandard = false;
    }


    private void hoverAction() {

    }

    private void clickAction() {
    }


    @Override
    public void scroll(float value) {
        this.containerBox.move(GlobalUtil.lerp(containerBox.getX(),containerBox.getX() + value, 30), containerBox.getY());
        if (isStandard) {
            standard();
        } else {
            simplify();
        }

    }
}
