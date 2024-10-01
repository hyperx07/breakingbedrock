package net.anawesomguy.breakingbedrock;

import net.anawesomguy.breakingbedrock.mixin.DiggerItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BedrockBlock extends Block {
    public BedrockBlock(Properties properties) {
        super(properties);
    }

    @Override @SuppressWarnings("deprecation")
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return player.getInventory().getSelected().getItem() instanceof DiggerItem item &&
               item.getTier().getLevel() >= BreakingBedrock.MINING_TIER &&
               state.is(((DiggerItemAccessor)item).getBlocks()) ?
            player.getDestroySpeed(state) / state.getDestroySpeed(level, pos) / 30 :
            0F;
    }
}
