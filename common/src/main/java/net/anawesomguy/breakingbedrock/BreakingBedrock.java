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

    public static final float DESTROY_TIME;
    public static final float EXPLOSION_RESIST;
    public static final int MINING_TIER;

    static {
        LOGGER.info("Initializing Breaking Bedrock. This mod overwrites bedrock, and may break with other mods that do the same!");
        Properties properties = new Properties();
        float destroyTime, explosionResist;
        int miningLevel;
        File configFile = configDir().resolve(BreakingBedrock.MOD_ID + ".properties").toFile();
        try {
            Properties temp = new Properties();
            temp.load(new FileInputStream(configFile));
            String time = temp.getProperty("destroy_time"),
                   resist = temp.getProperty("explosion_resist"),
                   level = temp.getProperty("mining_level");
            if (time == null || !((destroyTime = Float.parseFloat(time)) > -1) || destroyTime == Float.POSITIVE_INFINITY) {
                properties.setProperty("destroy_time", "100");
                destroyTime = 100F;
                LOGGER.debug("Correcting invalid config value {}!", time);
            } else properties.setProperty("destroy_time", time);
            if (resist == null || !((explosionResist = Float.parseFloat(resist)) > 0) || explosionResist == Float.POSITIVE_INFINITY) {
                properties.setProperty("explosion_resist", "3600000");
                explosionResist = 3600000F;
                LOGGER.debug("Correcting invalid config value {}!", resist);
            } else properties.setProperty("explosion_resist", resist);
            if (level == null || !((miningLevel = Integer.parseInt(level)) > 0)) {
                properties.setProperty("mining_level", "4");
                miningLevel = 4;
                LOGGER.debug("Correcting invalid config value {}!", level);
            } else properties.setProperty("mining_level", level);
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.info("Couldn't read config file (likely corrupted or missing)! Attempting to (re)create it.");
            properties.setProperty("destroy_time", "100");
            properties.setProperty("explosion_resist", "3600000");
            properties.setProperty("mining_level", "4");
            destroyTime = 100F;
            explosionResist = 3600000F;
            miningLevel = 4;
        }

        EXPLOSION_RESIST = explosionResist;
        DESTROY_TIME = destroyTime;
        MINING_TIER = miningLevel;

        try {
            properties.store(new FileOutputStream(configFile),
                  """
                  destroy_time: The destroy time for bedrock. (obsidian is 50, stone is 1.5, -1 is indestructible)
                  explosion_resist: The explosion resistance for bedrock. (stone is 6, glass is 0.3)
                  mining_level: The mining level required to mine bedrock. (netherite is 4)
                 """);
        } catch (IOException e) {
            LOGGER.error("Unable to create/modify config file!", e);
        }

        LOGGER.debug("Config initialized with values: destroyTime={}, explosionResist={}, miningLevel={}", destroyTime, explosionResist, miningLevel);
    }

    @ExpectPlatform
    public static Path configDir() {
        throw new AssertionError();
    }
}
