package com.lambferret.game.buff;

import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.PhaseText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Buff {
    private static final Logger logger = LogManager.getLogger(Buff.class.getName());

    private int turn;
    private int turnAfter;
    private final Figure figure;
    private final int value;
    private final Operation operation;
    private final List<Soldier> soldiers;
    private final Level level;
    private final Player player;
    private final boolean isPermanent;
    private final boolean isManual;
    private final EmpowerLevel empowerLevel;
    private boolean isEnable = true;


    private Buff(SetFigure fig) {
        this.figure = fig.figure;
        this.turn = fig.turn;
        this.turnAfter = fig.turnAfter;
        this.value = fig.value;
        this.operation = fig.operation;
        this.soldiers = fig.soldiers;
        this.level = fig.level;
        this.player = fig.player;
        this.isPermanent = fig.isPermanent;
        this.isManual = fig.isManual;
        this.empowerLevel = fig.empowerLevel;
        // TODO experimental feature
        effect();
    }

    public void effect() {
        if (!isEnable) {
            logger.info("effect |  🐳 버프 비활성화됨 | " + this);
            return;
        }

        if (turnAfter > 0) {
            logger.info("effect |  🐳 버프 지금 발현 타이밍아님 | " + this);
            this.turnAfter--;
            return;
        }
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=버프 발현-=-=-=-=-=-=-=-=-=-=-=-=-=");
        logger.info("effect |  🐳 버프 발현 | " + this);
        logger.info("effect |  🐳 타이밍 | " + PhaseScreen.getCurrentScreen());
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        if (empowerLevel != null) {
            for (Soldier soldier : soldiers) {
                soldier.setEmpowerLevel(empowerLevel);
            }
        } else {
            switch (figure) {
                case SOLDIER_X -> {
                    switch (operation) {
                        case ADD -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeX((byte) (soldier.getRangeX() + value));
                            }
                        }
                        case SUB -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeX((byte) (soldier.getRangeX() - value));
                            }
                        }
                        case MUL -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeX((byte) (soldier.getRangeX() * value));
                            }
                        }
                        case DIV -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeX((byte) (soldier.getRangeX() / value));
                            }
                        }
                    }
                }
                case SOLDIER_Y -> {
                    switch (operation) {
                        case ADD -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeY((byte) (soldier.getRangeY() + value));
                            }
                        }
                        case SUB -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeY((byte) (soldier.getRangeY() - value));
                            }
                        }
                        case MUL -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeY((byte) (soldier.getRangeY() * value));
                            }
                        }
                        case DIV -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setRangeY((byte) (soldier.getRangeY() / value));
                            }
                        }
                    }
                }
                case SOLDIER_SPEED -> {
                    switch (operation) {
                        case ADD -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((byte) (soldier.getSpeed() + value));
                            }
                        }
                        case SUB -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((byte) (soldier.getSpeed() - value));
                            }
                        }
                        case MUL -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((byte) (soldier.getSpeed() * value));
                            }
                        }
                        case DIV -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((byte) (soldier.getSpeed() / value));
                            }
                        }
                    }
                }
                case CAPACITY -> {
                    switch (operation) {
                        case ADD -> level.setMaxSoldierCapacity(level.getMaxSoldierCapacity() + value);
                        case SUB -> level.setMaxSoldierCapacity(level.getMaxSoldierCapacity() - value);
                        case MUL -> level.setMaxSoldierCapacity(level.getMaxSoldierCapacity() * value);
                        case DIV -> level.setMaxSoldierCapacity(level.getMaxSoldierCapacity() / value);
                    }
                }
            }
        }
        this.turn--;
        isEnable = false;
    }

    public static SetFigure figure(Figure figure) {
        return new SetFigure(figure);
    }

    public static class SetFigure {
        private final Figure figure;
        private int turn;
        private int turnAfter;
        private int value;
        private Operation operation;
        private boolean isPermanent;
        private boolean isManual = false;
        private List<Soldier> soldiers = new ArrayList<>();
        private Level level = null;
        private Player player = null;
        private EmpowerLevel empowerLevel;

        public SetFigure(Figure figure) {
            this.figure = figure;
        }

        public SetFigure turn(int turn) {
            this.turn = turn;
            return this;
        }

        public SetFigure turnAfter(int turnAfter) {
            this.turnAfter = turnAfter;
            return this;
        }

        public SetFigure operation(Operation operation) {
            this.operation = operation;
            return this;
        }

        public SetFigure to(List<Soldier> soldiers) {
            this.soldiers = soldiers;
            return this;
        }

        public SetFigure to(Level level) {
            this.level = level;
            return this;
        }

        public SetFigure to(Player player) {
            this.player = player;
            return this;
        }

        public SetFigure permanently() {
            this.turn = 9999;
            this.isPermanent = true;
            return this;
        }

        public SetFigure isManual() {
            this.isManual = true;
            return this;
        }

        private boolean verify() {
            List<Object> a = Arrays.asList(soldiers, level, player);

            long nonNullCount = a.stream()
                .filter(Objects::nonNull)
                .filter(obj -> !(obj instanceof String) || !((String) obj).isEmpty())
                .filter(obj -> !(obj instanceof List) || !((List<?>) obj).isEmpty())
                .count();

            return nonNullCount == 1;
        }

        public Buff value(int value) {
            this.value = value;
            if (verify()) return new Buff(this);
            else throw new IllegalStateException("Buff 를 생성할 수 없습니다.");
        }

        public Buff empower(EmpowerLevel level) {
            this.empowerLevel = level;
            if (verify() && !soldiers.isEmpty()) return new Buff(this);
            else throw new IllegalStateException("Buff 를 생성할 수 없습니다.");
        }
    }

    public boolean isExpired() {
        return this.turn <= 0;
    }

    public void setEnable() {
        isEnable = true;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public boolean isManual() {
        return isManual;
    }

    public String getDescription() {
        var description = this.figure.description;
        var isIncreased =
            (this.operation == Operation.ADD || this.operation == Operation.MUL) ?
                text.getIncreased() : text.getDecreased();
        description = description.replace("{isIncreased}", isIncreased)
            .replace("{turn}", String.valueOf(this.turn));
        return description;
    }

    @Override
    public String toString() {
        StringBuilder a = new StringBuilder("\n BUFF | ");
        if (turnAfter > 0) {
            a.append("after ").append(turnAfter).append(" turn, ");
        }
        a.append(figure).append(" Buff ").append(turn).append(" turn left ")
            .append("operation ").append(operation).append(" by value ").append(value);
        if (!soldiers.isEmpty()) {
            a.append(" to SOLDIERS : ").append(soldiers);
        }
        if (level != null) {
            a.append(" to current LEVEL ").append(level.getClass().getSimpleName());
        }
        if (player != null) {
            a.append(" to current PLAYER ");
        }

        return a.toString();
    }

    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV,
        ;
    }

    private static final PhaseText text;

    static {
        text = LocalizeConfig.uiText.getPhaseText();
    }

    public enum Figure {
        COST(text.getBuffCost()),
        CAPACITY(text.getBuffCapacity()),
        SOLDIER_X(text.getBuffSoldierX()),
        SOLDIER_Y(text.getBuffSoldierY()),
        SOLDIER_SPEED(text.getBuffSoldierSpeed()),
        ;
        final String description;

        Figure(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
