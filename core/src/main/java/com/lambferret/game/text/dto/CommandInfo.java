package com.lambferret.game.text.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CommandInfo {
    private String name;
    private String description;
    private String effect;
    private String shortDescription;
}
