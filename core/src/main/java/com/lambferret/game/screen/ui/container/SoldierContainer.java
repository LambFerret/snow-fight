package com.lambferret.game.screen.ui.container;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoldierContainer {
    private static final Logger logger = LogManager.getLogger(SoldierContainer.class.getName());
    private Hitbox box;
    private Hitbox plate;
    private int index;
    private int oddIndex;

    private static final float EXTEND_WIDTH = 300.0F;
    private static float EXTEND_HEIGHT;
    private static final float SHRINK_WIDTH = 50;
    private static float SHRINK_HEIGHT;
    private static final float VERTICAL_SPACING = 10.0F;
    private static final float HORIZONTAL_SPACING = 5.0F;


    public SoldierContainer(Soldier soldier, int index) {
        this.index = index;
        this.oddIndex = index / 2;
        this.box = new Hitbox();
    }

    public void standard() {
        logger.info("standard |  🐳 ");

        this.box.resize(EXTEND_WIDTH, EXTEND_HEIGHT);
        this.box.move(
            plate.getX() + HORIZONTAL_SPACING * (index + 1) + EXTEND_WIDTH * index,
            plate.getY() + VERTICAL_SPACING
        );
    }

    public void simplify() {

        this.box.resize(SHRINK_WIDTH, SHRINK_HEIGHT);
        if (index % 2 == 0) {
            logger.info("simplify 111|  🐳 " + oddIndex);

            this.box.move(
                plate.getX() + HORIZONTAL_SPACING * (oddIndex + 1) + SHRINK_WIDTH * oddIndex,
                plate.getY() + VERTICAL_SPACING * 2 + SHRINK_HEIGHT
            );
        } else {
            logger.info("simplify 222|  🐳 " + oddIndex);

            this.box.move(
                plate.getX() + HORIZONTAL_SPACING * (oddIndex + 1) + SHRINK_WIDTH * oddIndex,
                plate.getY() + VERTICAL_SPACING
            );
        }

    }

    private void hoverAction() {

    }

    private void clickAction() {
    }

    public void create(Hitbox plate) {
        this.plate = plate;
        EXTEND_HEIGHT = plate.getHeight() - (VERTICAL_SPACING * 2);
        SHRINK_HEIGHT = (plate.getHeight() - (VERTICAL_SPACING * 3)) / 2;
        this.standard();
    }

    public void render(SpriteBatch batch) {
        this.box.render(batch);
    }

    public void update(float delta) {
        this.box.update(delta);
        clickAction();
        if (this.box.isClicked) {
//            clickAction();
        }
        if (this.box.isHovered) {
            hoverAction();
        }
    }
}
