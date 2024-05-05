package net.anawesomguy.breakingbedrock.mixin;

import net.anawesomguy.breakingbedrock.BreakingBedrock;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -1F, ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;BEDROCK:Lnet/minecraft/world/level/block/Block;")))
    private static float bedrockBreaking$modifyBedrockDestroyTime(float constant) {
        return BreakingBedrock.getBedrockDestroyTime();
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = -1F, ordinal = 1), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;BEDROCK:Lnet/minecraft/world/level/block/Block;")))
    private static float bedrockBreaking$modifyBedrockExplosionResist(float constant) {
        return BreakingBedrock.getBedrockExplosionResist();
    }
}
