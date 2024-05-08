package net.anawesomguy.breakingbedrock;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public final class BreakingBedrock {
    public static final String MOD_ID = "breakingbedrock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final float BEDROCK_DESTROY_TIME;
    public static final float BEDROCK_EXPLOSION_RESIST;

    static {
        Properties properties = new Properties();
        float destroyTime, explosionResist;
        File configFile = configDir().resolve(BreakingBedrock.MOD_ID + ".properties").toFile();
        try {
            Properties temp = new Properties();
            temp.load(new FileInputStream(configFile));
            String time = temp.getProperty("bedrockDestroyTime"),
                resist = temp.getProperty("bedrockExplosionResist");
            if (time == null || !((destroyTime = Float.parseFloat(time)) > -1) || destroyTime == Float.POSITIVE_INFINITY) {
                properties.setProperty("bedrockDestroyTime", "67");
                destroyTime = 67;
            } else properties.setProperty("bedrockDestroyTime", time);
            if (resist == null || !((explosionResist = Float.parseFloat(resist)) > 0) || explosionResist == Float.POSITIVE_INFINITY) {
                properties.setProperty("bedrockExplosionResist", "3600000");
                explosionResist = 3600000;
            } else properties.setProperty("bedrockExplosionResist", resist);
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.info("Could not read config file (likely corrupted or missing)! Attempting to (re)create it.");
            properties.setProperty("bedrockDestroyTime", "67");
            properties.setProperty("bedrockExplosionResist", "3600000");
            explosionResist = 67;
            destroyTime = 3600000;
        }

        BEDROCK_EXPLOSION_RESIST = explosionResist;
        BEDROCK_DESTROY_TIME = destroyTime;

        try {
            properties.store(new FileOutputStream(configFile),
                """
                 bedrockDestroyTime: The destroy time for bedrock, allows decimals. (obsidian is 50, stone is 1.5, use -1 to make it indestructible)
                 bedrockExplosionResist: The explosion resistance for bedrock, allows decimals. (stone is 6, glass is 0.3)""");
        } catch (IOException e) {
            LOGGER.error("Unable to create/modify config file!", e);
        }
    }

    public static float getBedrockDestroyTime() {
        return BEDROCK_DESTROY_TIME;
    }

    public static float getBedrockExplosionResist() {
        return BEDROCK_EXPLOSION_RESIST;
    }

    @ExpectPlatform
    public static Path configDir() {
        throw new AssertionError();
    }
}
