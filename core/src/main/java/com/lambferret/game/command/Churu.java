package com.lambferret.game.command;

import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Churu extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public Churu() {
        super(
            ID,
            Type.REWARD,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown
        );
    }

    @Override
    public void execute(List<Soldier> soldiers) {
        int count = 0;
        List<Command> deck = PhaseScreen.deck;
        Iterator<Command> iterator = deck.iterator();
        while (iterator.hasNext()) {
            Command c = iterator.next();
            if (c instanceof Churu) {
                count++;
                iterator.remove();
            }
        }
        if (count > soldiers.size()) {
            for (Soldier s : soldiers) {
                s.setEmpowerLevel(EmpowerLevel.EMPOWERED);
            }
            return;
        }
        List<Soldier> temp = new ArrayList<>(soldiers);
        Collections.shuffle(temp);
        for (Soldier s : temp.subList(0, count)) {
            s.setEmpowerLevel(EmpowerLevel.EMPOWERED);
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Churu.class.getSimpleName();
    }

}
