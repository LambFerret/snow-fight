package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.command.Command;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ActionPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());
    Stage stage;
    Table map;
    Container<Table> mapContainer;
    Player player;
    Level level;
    List<Soldier> regularForEntireGameMember = new ArrayList<>();
    List<Soldier> regularForOnceMember = new ArrayList<>();
    List<Soldier> actionMember = new ArrayList<>();


    public ActionPhaseScreen(Container<Table> mapContainer) {
        this.stage = new Stage();
        this.mapContainer = mapContainer;
        this.stage.addActor(this.mapContainer);
        this.map = mapContainer.getActor();
    }

    public void create() {
    }

    @Override
    public void init(Player player) {
        this.player = player;
        this.level = PhaseScreen.level;
    }

    @Override
    public void startPhase() {
        setCommand();
        setMembers();
    }

    @Override
    public void executePhase() {
        for (Soldier soldier : actionMember) {
            soldier.talent();
        }
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
        Map<Command, List<Soldier>> commandMap = PhaseScreen.getCommands();
        for (Command command : commandMap.keySet()) {
            var value = commandMap.get(command);
            if (value == null) {
                command.execute();
            }
            command.execute(commandMap.get(command));
        }
    }

    private void happyWorking(Soldier soldier) {
        Random random = new Random();

        double randomValue = random.nextDouble() * 100;
        if (randomValue < soldier.getRunAwayProbability()) return;

        var map = level.getMaxAmountMap();
        int rows = level.ROWS;
        int cols = level.COLUMNS;

        int i = soldier.getRangeY();
        int j = soldier.getRangeX();
        int speed = soldier.getSpeed();


        int topLeftRow = random.nextInt(rows - i + 1);
        int topLeftCol = random.nextInt(cols - j + 1);

        System.out.println("i'll add from |" + rows + ", " + cols);

        int[][] currentAmount = level.getCurrentAmount();

        int usedSnowAmount = 0;
        for (int row = topLeftRow; row < topLeftRow + i; row++) {
            for (int col = topLeftCol; col < topLeftCol + j; col++) {
                int result = currentAmount[row][col] + speed;
                if (result > map[row][col]) {
                    result = map[row][col];
                }
                usedSnowAmount += result - currentAmount[row][col];
                currentAmount[row][col] = result;
            }
        }

//        for (int[] row : currentAmount) {
//            System.out.println(Arrays.toString(row));
//        }

        System.out.println("플레이어가 가진 남은 적설량 : " + player.getSnowAmount());
        System.out.println("한 군인의 이번턴 작업량 : " + usedSnowAmount);

        player.setSnowAmount(player.getSnowAmount() - usedSnowAmount);
    }


    public Stage getStage() {
        return this.stage;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }

}
