package com.lambferret.game.text.dto;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class SoldierText {
    private Map<String, SoldierInfo> ID;
    private Map<Rank, String> rank;
    private Map<Branch, String> branch;

}


