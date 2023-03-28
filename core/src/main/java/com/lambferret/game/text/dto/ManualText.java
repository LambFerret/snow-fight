package com.lambferret.game.text.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class ManualText {
    private Map<String, ManualInfo> ID;
}

