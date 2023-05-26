package com.lambferret.game.screen.phase;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.AnimationImage;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.lambferret.game.level.Level.LEVEL_EACH_SIZE_BIG;

public class ActionPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    Container<Table> mapContainer;
    Player player;
    Level level;
    List<Soldier> regularForEntireGameMember = new ArrayList<>();
    List<Soldier> regularForOnceMember = new ArrayList<>();
    List<Soldier> actionMember = new ArrayList<>();
    Map<Command, List<Soldier>> commandMap;

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
        setMapTable();
        setCommand();
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
        for (Soldier soldier : actionMember) {
            happyWorking(soldier);
        }
    }

    private void setMapTable() {
        mapContainer.setActor(level.makeTable(false));
        mapContainer.setPosition(ReadyPhaseScreen.getTableX(), ReadyPhaseScreen.getTableY());
        mapContainer.setSize(LEVEL_EACH_SIZE_BIG * level.COLUMNS, LEVEL_EACH_SIZE_BIG * level.ROWS);
        stage.addActor(this.mapContainer);
    }

    private void setMembers() {
        actionMember.clear();
        actionMember.addAll(regularForEntireGameMember);
        actionMember.addAll(regularForOnceMember);
        int capacity = level.getMaxSoldierCapacity() - actionMember.size();
        actionMember.addAll(getRandomSoldiersFromHand(capacity));
    }

    private List<Soldier> getRandomSoldiersFromHand(int number) {
        Random random = new Random();
        Set<Soldier> soldierSet = new HashSet<>();
        List<Soldier> hand = player.getSoldiers();
        List<Soldier> result;
        if (number > player.getSoldiers().size()) {
            result = hand;
        } else {
            while (soldierSet.size() < number) {
                int randInt = random.nextInt(hand.size());
                soldierSet.add(hand.get(randInt));
            }
            result = soldierSet.stream().toList();
        }
        return result;
    }

    private void setCommand() {
        this.commandMap = PhaseScreen.getCommands();
    }

    private void executeCommand() {
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
        logger.info("플레이어가 가진 남은 적설량 : " + player.getSnowAmount());
        logger.info("최소 적설량 : " + level.getMinSnowForClear());
        logger.info("현재 군인 이름은 " + soldier.getName() + " 쨩");

        Random random = new Random();

        double randomValue = Math.floor(random.nextDouble() * 100);
        if (randomValue < soldier.getRunAwayProbability()) {
            logger.info(
                soldier.getRunAwayProbability() + " 확률에 "
                    + randomValue + " 만큼이 걸려 군인 " + soldier.getName() + " 떠났습니다."
            );
            return;
        }

        int rows = level.ROWS;
        int cols = level.COLUMNS;

        int i = soldier.getRangeX() > cols ? cols : soldier.getRangeX();
        int j = soldier.getRangeY() > rows ? rows : soldier.getRangeY();
        int speed = soldier.getSpeed();

        int topLeftCol = random.nextInt(cols - i + 1);
        int topLeftRow = random.nextInt(rows - j + 1);

        logger.info("좌표 : " + topLeftCol + ", " + topLeftRow);
        logger.info("범위 : " + soldier.getRangeX() + ", " + soldier.getRangeY());
        logger.info("속도 : " + speed);

        int usedSnowAmount = 0;
        for (int row = topLeftRow; row < topLeftRow + j; row++) {
            for (int col = topLeftCol; col < topLeftCol + i; col++) {
                usedSnowAmount += level.modifyMapCurrentAmountBy(row, col, speed);
            }
        }

        logger.info("이번턴 작업량 : " + usedSnowAmount);
        logger.info("=========================================");

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
