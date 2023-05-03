package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ActionPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    Container<Level> mapContainer;
    Player player;
    Level level;
    List<Soldier> regularForEntireGameMember = new ArrayList<>();
    List<Soldier> regularForOnceMember = new ArrayList<>();
    List<Soldier> actionMember = new ArrayList<>();
    Map<Command, List<Soldier>> commandMap;

    public ActionPhaseScreen(Container<Level> mapContainer) {
        this.mapContainer = mapContainer;
        stage.addActor(this.mapContainer);
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
        setCommand();
        executeCommand();
        setMembers();
    }

    @Override
    public void executePhase() {
        for (Soldier soldier : actionMember) {
            soldier.talent(actionMember, commandMap, level, player);
        }
        logger.info("these are today's victim : " + actionMember);
        for (Soldier soldier : actionMember) {
            happyWorking(soldier);
        }
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

        int[][] maxSnowCapacityMap = level.getMaxAmountMap();
        int[][] currentAmountSnowInMap = level.getCurrentAmount();
        int rows = level.ROWS;
        int cols = level.COLUMNS;

        int i = soldier.getRangeX() > cols ? cols : soldier.getRangeX();
        int j = soldier.getRangeY() > rows ? rows : soldier.getRangeY();
        int speed = soldier.getSpeed();

        int topLeftCol = random.nextInt(cols - i + 1);
        int topLeftRow = random.nextInt(rows - j + 1);

        logger.info("좌표 : " + (topLeftCol + 1) + ", " + (topLeftRow + 1));
        logger.info("범위 : " + soldier.getRangeX() + ", " + soldier.getRangeY());
        logger.info("속도 : " + speed);

        int usedSnowAmount = 0;
        for (int row = topLeftRow; row < topLeftRow + j; row++) {
            for (int col = topLeftCol; col < topLeftCol + i; col++) {
                int result = currentAmountSnowInMap[row][col] + speed;
                if (result > maxSnowCapacityMap[row][col]) {
                    result = maxSnowCapacityMap[row][col];
                }
                usedSnowAmount += result - currentAmountSnowInMap[row][col];
                currentAmountSnowInMap[row][col] = result;
            }
        }

        System.out.println();
        for (int k = currentAmountSnowInMap.length; k > 0; k--) {
            System.out.println(Arrays.toString(currentAmountSnowInMap[k - 1]));

        }

        System.out.println();
        logger.info("이번턴 작업량 : " + usedSnowAmount);
        logger.info("=========================================");

        player.setSnowAmount(player.getSnowAmount() - usedSnowAmount);
    }

    public void render() {
        stage.draw();
        stage.act();
    }

}
