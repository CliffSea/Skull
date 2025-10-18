package me.cliff.skull;

import me.cliff.skull.config.SkullConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;

import java.util.Random;

public class Skull implements ClientModInitializer{

    public static String modid = "skull";
    private static SkullConfig skullConfig;
    public static final Random random = new Random();
    private static SoundsManager soundManager;

    private static PositionedSoundInstance currentSound;

    public static int freezeTimer = 0;
    public static int freezeDurationTicks = 100;
    public static int cooldownTimer = 0;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(SkullConfig.class, JanksonConfigSerializer::new);
        soundManager = new SoundsManager();
        soundManager.registry();
        skullConfig = AutoConfig.getConfigHolder(SkullConfig.class).getConfig();

        ClientTickEvents.START_CLIENT_TICK.register(Skull::randomFreeze);
    }

    public static void randomFreeze(MinecraftClient client){
        if (client.world == null) return;

        if (client.currentScreen instanceof SkullScreen){
            Skull.freezeTimer++;
            if (freezeTimer >= freezeDurationTicks){
                client.currentScreen.close();
                MinecraftClient.getInstance().getSoundManager().stop(currentSound);
                Skull.freezeTimer = 0;
            }

            return;
        }

        if (client.currentScreen != null) {
            return;
        }

        cooldownTimer++;
        if (cooldownTimer < (getConfig().secsCooldown * 20)){
            return;
        }

        if (cooldownTimer % 20 == 0){
            if (random.nextInt(100) < getConfig().probability){
                client.setScreen(getSkullScreen());
                currentSound = PositionedSoundInstance.master(SoundsManager.getRandomSoundEvent(),1f, 5);
                MinecraftClient.getInstance().getSoundManager().play(currentSound);
                cooldownTimer = 0;
                Skull.freezeTimer = 0;
            }
        }
    }


    public static SkullConfig getConfig(){
        return skullConfig;
    }

    public static SkullScreen getSkullScreen() {
        return new SkullScreen();
    }

    public static SoundsManager getSoundManager() {
        return soundManager;
    }
}
