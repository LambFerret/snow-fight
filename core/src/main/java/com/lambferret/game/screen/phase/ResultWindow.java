package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.FontConfig;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ResultWindow extends Window implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(VictoryScreen.class.getName());
    Player player;
    Level level;
    Label.LabelStyle bigFont = new Label.LabelStyle();
    Label.LabelStyle smallFont = new Label.LabelStyle();
    int moneyToGet = 0, upperToGet = 0, middleToGet = 0, lowerToGet = 0;

    public ResultWindow() {
        super("", GlobalSettings.skin);
        bigFont.font = FontConfig.uiFont;
        smallFont.font = FontConfig.uiFont;
        this.setPosition(100, 100);
        this.setSize(800, 500);
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
        this.clearChildren();

        this.add(moneyTable());
        this.add(affinityTable());
        this.add(makeConfirmGroup());

        Overlay.uiSpriteBatch.addActor(this);
    }

    @Override
    public void executePhase() {
        PhaseScreen.end();
        this.remove();
    }

    protected abstract Table makeConfirmGroup();

    protected void confirm() {
        player.setDay(SnowFight.player.getDay() + 1);
        player.setMoneyBy(moneyToGet);
        player.setUpperAffinityBy(upperToGet);
        player.setMiddleAffinityBy(middleToGet);
        player.setDownAffinityBy(lowerToGet);

        ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
        executePhase();
    }

    protected ScrollPane moneyTable() {
        Table table = new Table();
        int moneyToGet = 0;

        switch (level.getRegion()) {
            case RURAL -> moneyToGet = 500;
            case URBAN -> moneyToGet = 1000;
            case NATION -> moneyToGet = 1500;
        }
        Label label = new Label("Basic income : " + moneyToGet, bigFont);
        table.add(label).row();

        Map<Rank, Long> totalSalariesByRank = player.getSoldiers().stream()
            .collect(Collectors.groupingBy(Soldier::getRank, Collectors.counting()));

        for (Map.Entry<Rank, Long> entry : totalSalariesByRank.entrySet()) {
            Rank rank = entry.getKey();
            long count = entry.getValue();
            moneyToGet -= count * rank.getSalary();
            //TODO l8n the rank name
            Label label1 = new Label(rank.name() + " salary : " + rank.name() + " * -"
                + rank.getSalary() + " = -" + count * rank.getSalary(), smallFont);
            table.add(label1).row();
        }

        for (Map.Entry<String, Integer> entry : PhaseScreen.bonusMoney.entrySet()) {
            String name = entry.getKey();
            int bonus = entry.getValue();
            moneyToGet += bonus;
            Label label2 = new Label(name + " : " + bonus, smallFont);
            table.add(label2).row();
        }
        this.moneyToGet = moneyToGet;

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setScrollingDisabled(true, false);
        return scrollPane;

    }

    protected ScrollPane affinityTable() {
        Table table = new Table();
        StringBuilder upperSb = new StringBuilder();
        StringBuilder middleSb = new StringBuilder();
        StringBuilder lowerSb = new StringBuilder();

        int upperToGet = 0, middleToGet = 0, lowerToGet = 0;
        List<PhaseScreen.AffinityBonus> affinityBonuses = PhaseScreen.affinityBonuses;
        for (PhaseScreen.AffinityBonus bonus : affinityBonuses) {
            switch (bonus.affinity()) {
                case UPPER -> {
                    upperSb.append(bonus.byWhom()).append(" ").append(bonus.bonus()).append(", ");
                    upperToGet += bonus.bonus();
                }
                case MIDDLE -> {
                    middleSb.append(bonus.byWhom()).append(" ").append(bonus.bonus()).append(", ");
                    middleToGet += bonus.bonus();
                }
                case DOWN -> {
                    lowerSb.append(bonus.byWhom()).append(" ").append(bonus.bonus()).append(", ");
                    lowerToGet += bonus.bonus();
                }
            }
        }


        Label up = new Label("upper affinity get : " + upperToGet, bigFont);
        Label upDetail = new Label(upperSb, smallFont);
        Label mid = new Label("middle affinity get : " + middleToGet, bigFont);
        Label midDetail = new Label(middleSb, smallFont);
        Label low = new Label("lower affinity get : " + lowerToGet, bigFont);
        Label lowDetail = new Label(lowerSb, smallFont);

        this.upperToGet = upperToGet;
        this.middleToGet = middleToGet;
        this.lowerToGet = lowerToGet;

        table.add(up).left().pad(10).row();
        table.add(upDetail).center().pad(10).row();
        table.add(mid).left().pad(10).row();
        table.add(midDetail).center().pad(10).row();
        table.add(low).left().pad(10).row();
        table.add(lowDetail).center().pad(10).row();

        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setScrollingDisabled(true, false);
        return scrollPane;
    }

    @Override
    public void render() {
        Stage stage = ActionPhaseScreen.stage;
        stage.draw();
        stage.act();
    }
}
