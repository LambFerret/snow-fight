package com.lambferret.game.screen.ui;

import com.lambferret.game.command.Command;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public interface SoldierSelectionListener {
    void onSoldierSelected(List<Soldier> soldiers, Command command);
}
