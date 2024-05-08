package net.anawesomguy.breakingbedrock.mixin;

import net.anawesomguy.breakingbedrock.BedrockBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @Redirect(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=bedrock")))
    private static Block breakingbedrock$replaceBedrock(Properties properties) {
        return new BedrockBlock(properties);
    }
}
