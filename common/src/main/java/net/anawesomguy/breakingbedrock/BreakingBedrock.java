package net.anawesomguy.breakingbedrock;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BreakingBedrock {
    public static final String MOD_ID = "breakingbedrock";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @ExpectPlatform
    public static float getBedrockDestroyTime() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getBedrockExplosionResist() {
        throw new AssertionError();
    }
}
