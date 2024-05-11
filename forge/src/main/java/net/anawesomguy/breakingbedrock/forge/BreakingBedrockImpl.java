package net.anawesomguy.breakingbedrock.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
