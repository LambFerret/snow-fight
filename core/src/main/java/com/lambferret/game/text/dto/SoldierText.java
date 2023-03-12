package com.lambferret.game.text.dto;

import com.lambferret.game.component.constant.Affiliation;
import com.lambferret.game.component.constant.Branch;
import com.lambferret.game.component.constant.Rank;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class SoldierText {
    private Map<Rank, String> rank;
    private Map<Affiliation, String> affiliation;
    private Map<String, String> name;
    private Map<Branch, String> branch;

}

