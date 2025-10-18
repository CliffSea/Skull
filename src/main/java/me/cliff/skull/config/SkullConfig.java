package me.cliff.skull.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "skull")
public class SkullConfig implements ConfigData {

    public int probability = 1;
    public int secsCooldown = 5;

}
