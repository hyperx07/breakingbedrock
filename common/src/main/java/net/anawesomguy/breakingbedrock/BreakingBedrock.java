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
    public static final int BEDROCK_MINING_TIER;

    static {
        LOGGER.info("Initializing Breaking Bedrock.\nThis mod overwrites bedrock, and may cause broken functionality with any other mods that do the same!");
        Properties properties = new Properties();
        float destroyTime, explosionResist;
        int miningLevel;
        File configFile = configDir().resolve(BreakingBedrock.MOD_ID + ".properties").toFile();
        try {
            Properties temp = new Properties();
            temp.load(new FileInputStream(configFile));
            String time = temp.getProperty("bedrockDestroyTime"),
                   resist = temp.getProperty("bedrockExplosionResist"),
                   level = temp.getProperty("bedrockMiningTier");
            if (time == null || !((destroyTime = Float.parseFloat(time)) > -1) || destroyTime == Float.POSITIVE_INFINITY) {
                properties.setProperty("bedrockDestroyTime", "71");
                destroyTime = 71F;
                LOGGER.debug("Correcting invalid config value {}!", time);
            } else properties.setProperty("bedrockDestroyTime", time);
            if (resist == null || !((explosionResist = Float.parseFloat(resist)) > 0) || explosionResist == Float.POSITIVE_INFINITY) {
                properties.setProperty("bedrockExplosionResist", "3600000");
                explosionResist = 3600000F;
                LOGGER.debug("Correcting invalid config value {}!", resist);
            } else properties.setProperty("bedrockExplosionResist", resist);
            if (level == null || !((miningLevel = Integer.parseInt(level)) > 0)) {
                properties.setProperty("bedrockMiningLevel", "4");
                miningLevel = 4;
                LOGGER.debug("Correcting invalid config value {}!", level);
            } else properties.setProperty("bedrockMiningLevel", resist);
        } catch (IOException | IllegalArgumentException e) {
            LOGGER.info("Could not read config file (likely corrupted or missing)! Attempting to (re)create it.");
            properties.setProperty("bedrockDestroyTime", "71");
            properties.setProperty("bedrockExplosionResist", "3600000");
            properties.setProperty("bedrockMiningLevel", "4");
            explosionResist = 71F;
            destroyTime = 3600000F;
            miningLevel = 4;
        }

        BEDROCK_EXPLOSION_RESIST = explosionResist;
        BEDROCK_DESTROY_TIME = destroyTime;
        BEDROCK_MINING_TIER = miningLevel;

        try {
            properties.store(new FileOutputStream(configFile),
                  """
                  bedrockDestroyTime: The destroy time for bedrock. (obsidian is 50, stone is 1.5, -1 means indestructible)
                  bedrockExplosionResist: The explosion resistance for bedrock. (stone is 6, glass is 0.3)
                  bedrockExplosionResist: The mining level required to mine bedrock. (netherite is 4)
                 """);
        } catch (IOException e) {
            LOGGER.error("Unable to create/modify config file!", e);
        }
    }

    @ExpectPlatform
    public static Path configDir() {
        throw new AssertionError();
    }
}
