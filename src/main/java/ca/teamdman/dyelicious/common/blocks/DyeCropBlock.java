package ca.teamdman.dyelicious.common.blocks;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.registry.DyeliciousItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DyeCropBlock extends net.minecraft.world.level.block.CropBlock {
	public final DyeColor COLOUR;

	public DyeCropBlock(DyeColor colour) {
		super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
		this.COLOUR = colour;
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return DyeliciousItems.SEEDS_LOOKUP.get(COLOUR)
				.get();
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level instanceof ServerLevel se) {
			this.randomTick(state, se, pos, level.getRandom());
		}
		return super.use(state, level, pos, player, hand, hit);
	}

}
