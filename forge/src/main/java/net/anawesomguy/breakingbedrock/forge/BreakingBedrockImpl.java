package net.anawesomguy.breakingbedrock.forge;

import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

@Mod(BreakingBedrock.MOD_ID)
public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
