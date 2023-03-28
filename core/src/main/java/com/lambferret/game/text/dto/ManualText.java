package com.lambferret.game.text.dto;

import com.lambferret.game.constant.Affiliation;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class ManualText {
    private Map<String, ManualInfo> ID;
}

