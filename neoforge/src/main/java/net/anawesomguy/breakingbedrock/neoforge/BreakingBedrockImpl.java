package net.anawesomguy.breakingbedrock.neoforge;

import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class BreakingBedrockImpl {
    public static Path configDir() {
        try {
            Class.forName("net.neoforged.fml.loading.FMLPaths");
        } catch (ClassNotFoundException e) {
            return net.minecraftforge.fml.loading.FMLPaths.CONFIGDIR.get();
        }
        return FMLPaths.CONFIGDIR.get();
    }
}
