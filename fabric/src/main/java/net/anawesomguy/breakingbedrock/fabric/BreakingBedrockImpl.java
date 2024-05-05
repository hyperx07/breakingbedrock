package net.anawesomguy.breakingbedrock.fabric;

import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class BreakingBedrockImpl {
    public static final float BEDROCK_DESTROY_TIME;
    public static final float BEDROCK_EXPLOSION_RESIST;

    static {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve(BreakingBedrock.MOD_ID + ".properties").toFile();
        Properties properties = new Properties();
        float destroyTime, explosionResist;
        try {
            Properties temp = new Properties();
            temp.load(new FileInputStream(configFile));
            String time = temp.getProperty("bedrockDestroyTime"),
                   resist = temp.getProperty("bedrockExplosionResist");
            destroyTime = Float.parseFloat(time);
            explosionResist = Float.parseFloat(resist);
            boolean timeBounds = !(destroyTime > -1) || Float.isInfinite(destroyTime),
                    resistBounds = !(explosionResist > 0) || Float.isInfinite(destroyTime);
            if (timeBounds) {
                properties.setProperty("bedrockDestroyTime", "60");
                destroyTime = 60;
            } else properties.setProperty("bedrockDestroyTime", time);
            if (resistBounds) {
                properties.setProperty("bedrockDestroyTime", "3600000");
                explosionResist = 3600000;
            } else properties.setProperty("bedrockDestroyTime", resist);
        } catch (IOException | IllegalArgumentException e) {
            BreakingBedrock.LOGGER.info("Could not read config file (likely corrupted or missing)! Attempting to (re)create it.");
            properties.setProperty("bedrockDestroyTime", "60");
            properties.setProperty("bedrockExplosionResist", "3600000");
            explosionResist = 60;
            destroyTime = 3600000;
        }

        BEDROCK_EXPLOSION_RESIST = explosionResist;
        BEDROCK_DESTROY_TIME = destroyTime;

        try {
            properties.store(new FileOutputStream(configFile),
                """
                bedrockDestroyTime: The destroy time for bedrock, allows decimals. (obsidian is 50, stone is 1.5, use -1 to make it indestructible)
                bedrockExplosionResist: The explosion resistance for bedrock, allows decimals. (stone is 6, glass is 0.3)
                """);
        } catch (IOException e) {
            BreakingBedrock.LOGGER.error("Unable to create/modify config file!", e);
        }
    }

    public static float getBedrockDestroyTime() {
        return BEDROCK_DESTROY_TIME;
    }

    public static float getBedrockExplosionResist() {
        return BEDROCK_EXPLOSION_RESIST;
    }
}
