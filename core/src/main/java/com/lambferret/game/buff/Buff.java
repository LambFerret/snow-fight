package com.lambferret.game.buff;

import com.badlogic.gdx.graphics.Texture;
import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.PhaseText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Buff {
    private static final Logger logger = LogManager.getLogger(Buff.class.getName());
    private static final PhaseText text;

    private int turn;
    private int turnAfter;
    private int count;
    private final Figure figure;
    private final int value;
    private final Operation operation;
    private final List<Soldier> soldiers;
    private final Level level;
    private final Player player;
    private final boolean isPermanent;
    private final boolean isManual;
    private final EmpowerLevel empowerLevel;
    private final boolean isIncreased;
    private boolean isEnable = true;
    private boolean isExpired = false;


    private Buff(SetFigure fig) {
        this.figure = fig.figure;
        this.turn = fig.turn;
        this.turnAfter = fig.turnAfter;
        this.count = fig.count;
        this.value = fig.value;
        this.operation = fig.operation;
        this.soldiers = fig.soldiers;
        this.level = fig.level;
        this.player = fig.player;
        this.isPermanent = fig.isPermanent;
        this.isManual = fig.isManual;
        this.empowerLevel = fig.empowerLevel;
        this.isIncreased = fig.isIncreased;
        // TODO experimental feature
        effect();
    }

    public boolean effectCountdown() {
        if (count > 0) {
            count--;
            if (count == 0) {
                isExpired = true;
            }
            return true;
        }
        return false;
    }

    public void effect() {
        if (!isEnable || isExpired) {
            return;
        }

        if (turnAfter > 0) {
            this.turnAfter--;
            return;
        }

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
                                soldier.setSpeed((short) (soldier.getSpeed() + value));
                            }
                        }
                        case SUB -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((short) (soldier.getSpeed() - value));
                            }
                        }
                        case MUL -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((short) (soldier.getSpeed() * value));
                            }
                        }
                        case DIV -> {
                            for (Soldier soldier : soldiers) {
                                soldier.setSpeed((short) (soldier.getSpeed() / value));
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
        private int count;
        private int value;
        private Operation operation;
        private boolean isPermanent;
        private boolean isManual = false;
        private List<Soldier> soldiers = new ArrayList<>();
        private Level level = null;
        private Player player = null;
        private boolean isIncreased;
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

        public SetFigure count(int count) {
            this.count = count;
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
            this.player = SnowFight.player;
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

            isIncreased = this.operation == Operation.ADD || this.operation == Operation.MUL;
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
        return this.turn <= 0 || isExpired;
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

    public Texture getTexture() {
        return isIncreased ? AssetFinder.getTexture("sun") : AssetFinder.getTexture("moon");
    }

    public String getDescription() {
        String description = this.figure.description.replace("{isIncreased}", isIncreased ? text.getIncreased() : text.getDecreased());
        if (this.soldiers.size() > 0) {
            description = description.replace(
                "{soldier}", soldiers.stream().map(Soldier::getName).collect(Collectors.joining(", "))
            );
        }
        if (this.turn < 1000) description += text.getTurn().replace("{turn}", String.valueOf(this.turn));
        return description;
    }

    public int getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public Figure getFigure() {
        return figure;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n BUFF | ");
        if (turnAfter > 0) {
            sb.append("after ").append(turnAfter).append(" turn, ");
        }
        sb.append(figure).append(" Buff ").append(turn).append(" turn left ")
            .append("operation ").append(operation).append(" by value ").append(value);
        if (!soldiers.isEmpty()) {
            sb.append(" to SOLDIERS : ").append(soldiers);
        }
        if (level != null) {
            sb.append(" to current LEVEL ").append(level.getClass().getSimpleName());
        }
        if (player != null) {
            sb.append(" to current PLAYER ");
        }

        return sb.toString();
    }

    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV,
        ;
    }

    static {
        text = LocalizeConfig.uiText.getPhaseText();
    }

    public enum Figure {
        COST(text.getBuffCost()),
        CAPACITY(text.getBuffCapacity()),
        SOLDIER_X(text.getBuffSoldierX()),
        SOLDIER_Y(text.getBuffSoldierY()),
        SOLDIER_SPEED(text.getBuffSoldierSpeed()),
        NEXT_COMMAND(text.getBuffSoldierSpeed() + "TODO");
        final String description;

        Figure(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
