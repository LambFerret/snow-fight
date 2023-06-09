package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.AnimationImage;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.lambferret.game.level.Level.LEVEL_EACH_SIZE_BIG;
import static com.lambferret.game.screen.ui.AbstractOverlay.SNOW_BAR_HEIGHT;
import static com.lambferret.game.screen.ui.AbstractOverlay.SNOW_BAR_Y;

public class ActionPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    /**
     * action phase 가 자동으로 넘어가기까지 시간
     */
    public static float actionPhaseTime;
    public static final float ASSIGNED_TIME_FOR_EACH_SOLDIER = 0.5F;
    Container<Table> mapContainer;
    Player player;
    Level level;
    List<Soldier> regularForEntireGameMember = new ArrayList<>();
    List<Soldier> regularForOnceMember = new ArrayList<>();
    List<Soldier> actionMember = new ArrayList<>();
    Map<Command, List<Soldier>> commandMap;
    Random random;

    public ActionPhaseScreen() {
        this.mapContainer = new Container<>();
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
        this.level = PhaseScreen.level;
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void startPhase() {
        random = PhaseScreen.handRandom;
        setMapTable();
        executeCommand();
        setMembers();
        executePhase();
    }

    @Override
    public void executePhase() {
        for (Soldier soldier : actionMember) {
            soldier.talent(actionMember, commandMap, level, player);
        }
        logger.info("이번 턴의 희생자들 ㅠㅠ : " + actionMember);

        Queue<Soldier> soldierQueue = new LinkedList<>(actionMember);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (soldierQueue.size() > 0) {
                    Soldier nextSoldier = soldierQueue.remove();
                    happyWorking(nextSoldier);
                } else {
                    this.cancel();
                }
            }
        }, 0, ASSIGNED_TIME_FOR_EACH_SOLDIER);
    }

    /**
     * 맵에 대한 정보로드, 주로 레디페이즈의 정보를 토대로 만들기 위함
     */
    private void setMapTable() {
        mapContainer.setActor(level.makeTable(false));
        mapContainer.setPosition(ReadyPhaseScreen.getTableX(), ReadyPhaseScreen.getTableY());
        mapContainer.setSize(LEVEL_EACH_SIZE_BIG * level.COLUMNS, LEVEL_EACH_SIZE_BIG * level.ROWS);
        stage.addActor(this.mapContainer);
        OrthographicCamera cam = (OrthographicCamera) stage.getCamera();
        cam.zoom = ReadyPhaseScreen.getScaleValue();
        cam.update();

        mapContainer.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
                camera.zoom += amountY * 0.05;// adjust the 0.1 value to zoom faster or slower
                if (camera.zoom < 0.5f) camera.zoom = 0.5f;
                if (camera.zoom > 3f) camera.zoom = 3f;
                camera.update();
                return true;
            }
        });
    }

    private void setMembers() {
        actionMember.clear();
        actionMember.addAll(regularForEntireGameMember);
        actionMember.addAll(regularForOnceMember);
        int capacity = level.getMaxSoldierCapacity() - actionMember.size();
        actionMember.addAll(getRandomSoldiersFromHand(capacity));
        actionPhaseTime = actionMember.size() * ASSIGNED_TIME_FOR_EACH_SOLDIER;
    }

    private List<Soldier> getRandomSoldiersFromHand(int number) {
        Set<Soldier> soldierSet = new HashSet<>();
        List<Soldier> hand = player.getSoldiers();
        List<Soldier> result;
        if (number > player.getSoldiers().size()) {
            result = hand;
        } else {
            while (soldierSet.size() < number) {
                int randInt = PhaseScreen.handRandom.random(hand.size());
                soldierSet.add(hand.get(randInt));
            }
            result = soldierSet.stream().toList();
        }
        return result;
    }

    /**
     * 로드된 작전 사용
     */
    private void executeCommand() {
        this.commandMap = PhaseScreen.getCommands();
        for (Command command : commandMap.keySet()) {
            var value = commandMap.get(command);
            if (value == null) {
                command.execute();
            } else {
                command.execute(value);
            }
        }
    }

    private void happyWorking(Soldier soldier) {
        logger.info("=========================================");
        logger.info("현재 군인 이름은 " + soldier.getName() + " 쨩");

        if (random.randomBoolean(soldier.getRunAwayProbability())) {
            String a = MathUtils.random.nextBoolean() ? "runaway_1" : "runaway_2";
            CustomButton runAwayImage = GlobalUtil.simpleButton(a);
            runAwayImage.setSize(300, 200);
            runAwayImage.setPosition(-runAwayImage.getWidth(), SNOW_BAR_HEIGHT + SNOW_BAR_Y + 50);
            runAwayImage.setText(soldier.getName() + " run away!");
            Overlay.uiSpriteBatch.addActor(runAwayImage);
            runAwayImage.addAction(Actions.sequence(
                Actions.moveTo(50, SNOW_BAR_HEIGHT + SNOW_BAR_Y + 50, ASSIGNED_TIME_FOR_EACH_SOLDIER / 5F, Interpolation.fastSlow),
                Actions.delay(ASSIGNED_TIME_FOR_EACH_SOLDIER / 2F),
                Actions.removeActor()
            ));
            return;
        }

        int rows = level.ROWS;
        int cols = level.COLUMNS;

        int i = soldier.getRangeX() > cols ? cols : soldier.getRangeX();
        int j = soldier.getRangeY() > rows ? rows : soldier.getRangeY();
        int speed = soldier.getSpeed();

        int topLeftCol = random.random(cols - i + 1);
        int topLeftRow = random.random(rows - j + 1);

        int usedSnowAmount = 0;
        for (int row = topLeftRow; row < topLeftRow + j; row++) {
            for (int col = topLeftCol; col < topLeftCol + i; col++) {
                usedSnowAmount += level.modifyMapCurrentAmountBy(row, col, speed);
            }
        }

        logger.info("\n " + soldier.getName() + " 쨩 스테이터스 | \n " +
            " x, y " + soldier.getRangeX() + ", " + soldier.getRangeY() + " \n"
            + " speed " + soldier.getSpeed() + " | " + "run away probability " + soldier.getRunAwayProbability() + "\n"
            + " 이번턴 작업량 : " + usedSnowAmount
        );
        // animation
        int w = LEVEL_EACH_SIZE_BIG, h = LEVEL_EACH_SIZE_BIG;

        int translated_y = rows - topLeftRow - j;
        int region_x = topLeftCol * w;
        int region_y = translated_y * h;

        var animation = soldier.getAnimation();
        AnimationImage newImage = new AnimationImage(animation);
        newImage.setSize(w, h);

        Vector2 v2 = this.mapContainer.getActor().localToStageCoordinates(new Vector2(0, 0));
        float animationX = v2.x + region_x + ((soldier.getRangeX() * w) / 2F) - (newImage.getWidth() / 2);
        float animationY = v2.y + region_y + ((soldier.getRangeY() * h) / 2F) - (newImage.getHeight() / 2);

        newImage.setPosition(animationX, animationY);
        stage.addActor(newImage);

        newImage.addAction(Actions.sequence(
            Actions.delay(animation.getAnimationDuration()),
            Actions.removeActor()
        ));

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = GlobalUtil.getNinePatchDrawableFromTexture("happyWorking", 5);
        style.font = GlobalSettings.font;
        CustomButton workingRange = new CustomButton("", style);
        workingRange.setSize(w * i, h * j);
        workingRange.setPosition(v2.x + region_x, v2.y + region_y);
        stage.addActor(workingRange);

        workingRange.addAction(Actions.sequence(
            Actions.delay(animation.getAnimationDuration()),
            Actions.removeActor()
        ));

//        logger.info("=========================================");
//        logger.info(" table x, y | " + mapContainer.getActor().getX() + ", " + mapContainer.getActor().getY());
//        logger.info(" container x, y | " + mapContainer.getX() + ", " + mapContainer.getY());
//        logger.info(" region x, y | " + region_x + ", " + region_y);
//        logger.info(" v2 x, y | " + v2.x + ", " + v2.y);
//        logger.info(" relative x,y  | " + (region_x + ((soldier.getRangeX() * w) / 2F) - (newImage.getWidth() / 2) + ", " + (region_y + ((soldier.getRangeY() * h) / 2F) - (newImage.getHeight() / 2))));
//        logger.info(" result x, y | " + newImage.getX() + ", " + newImage.getY());
//        logger.info("=========================================");

        player.setSnowAmount(player.getSnowAmount() - usedSnowAmount);
    }

    public void render() {
        stage.draw();
        stage.act();
    }

}
