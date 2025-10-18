package me.cliff.skull;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class SoundsManager {

    public static final Identifier SOUND_0 = Identifier.of(Skull.modid, "0");
   //public static final Identifier SOUND_1 = Identifier.of(Skull.modid, "1");
   //public static final Identifier SOUND_2 = Identifier.of(Skull.modid, "2");
   //public static final Identifier SOUND_3 = Identifier.of(Skull.modid, "3");
   //public static final Identifier SOUND_4 = Identifier.of(Skull.modid, "4");
   //public static final Identifier SOUND_5 = Identifier.of(Skull.modid, "5");

    public static SoundEvent SOUND_EVENT_0 = SoundEvent.of(SOUND_0);
    //public static SoundEvent SOUND_EVENT_1 = SoundEvent.of(SOUND_1);
    //public static SoundEvent SOUND_EVENT_2 = SoundEvent.of(SOUND_2);
    //public static SoundEvent SOUND_EVENT_3 = SoundEvent.of(SOUND_3);
    //public static SoundEvent SOUND_EVENT_4 = SoundEvent.of(SOUND_4);
    //public static SoundEvent SOUND_EVENT_5 = SoundEvent.of(SOUND_5);

    private static final Random RANDOM = new Random();

    public static Map<Identifier, SoundEvent> soundsMap = Map.of(
            SOUND_0, SOUND_EVENT_0
            //SOUND_1, SOUND_EVENT_1,
            //SOUND_2, SOUND_EVENT_2,
            //SOUND_3, SOUND_EVENT_3,
            //SOUND_4, SOUND_EVENT_4,
            //SOUND_5, SOUND_EVENT_5
    );

    public void registry(){
        for (Map.Entry<Identifier, SoundEvent> entry : soundsMap.entrySet()) {
            Registry.register(Registries.SOUND_EVENT, entry.getKey(), entry.getValue());
        }
    }

    public static Map.Entry<Identifier, SoundEvent> getRandomSoundEntry() {
        if (soundsMap.isEmpty()) {
            return null;
        }

        List<Map.Entry<Identifier, SoundEvent>> entryList =
                soundsMap.entrySet().stream().collect(Collectors.toList());

        int randomIndex = RANDOM.nextInt(entryList.size());
        return entryList.get(randomIndex);
    }

    public static SoundEvent getRandomSoundEvent() {
        Map.Entry<Identifier, SoundEvent> randomEntry = getRandomSoundEntry();
        return (randomEntry != null) ? randomEntry.getValue() : null;
    }


}
