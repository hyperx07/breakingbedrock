package net.anawesomguy.breakingbedrock.forge;

import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(BreakingBedrock.MOD_ID)
public final class BreakingBedrockImpl {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ForgeConfigSpec.DoubleValue BEDROCK_DESTROY_TIME;
    public static final ForgeConfigSpec.DoubleValue BEDROCK_EXPLOSION_RESIST;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        BEDROCK_DESTROY_TIME = builder.comment("The destroy time for bedrock, allows decimals. (obsidian is 50, stone is 1.5, use -1 to make it indestructible)")
                                      .defineInRange("bedrockDestroyTime", 60, -1, Float.MAX_VALUE);
        BEDROCK_EXPLOSION_RESIST = builder.comment("The explosion resistance for bedrock, allows decimals. (stone is 6, glass is 0.3)")
                                          .defineInRange("bedrockExplosionResist", 3600000, 0, Float.MAX_VALUE);
        CONFIG_SPEC = builder.build();
    }

    public BreakingBedrockImpl() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_SPEC);
    }

    public static float getBedrockDestroyTime() {
        return BEDROCK_DESTROY_TIME.get().floatValue();
    }

    public static float getBedrockExplosionResist() {
        return BEDROCK_EXPLOSION_RESIST.get().floatValue();
    }
}
