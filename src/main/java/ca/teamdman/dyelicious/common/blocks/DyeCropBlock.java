package ca.teamdman.dyelicious.common.blocks;

import ca.teamdman.dyelicious.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;

public class DyeCropBlock extends net.minecraft.world.level.block.CropBlock {
	public final DyeColor COLOUR;

	public DyeCropBlock(DyeColor colour) {
		super(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
		this.COLOUR = colour;
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.SEEDS_LOOKUP.get(COLOUR)
									.get();
	}

	@Override
	public void playerDestroy(Level p_49827_, Player p_49828_, BlockPos p_49829_, BlockState p_49830_, @Nullable BlockEntity p_49831_, ItemStack p_49832_) {
		super.playerDestroy(p_49827_, p_49828_, p_49829_, p_49830_, p_49831_, p_49832_);
	}

	/**
	 * Debugging method to force a crop to grow when right-clicked.
	 * Does nothing in production.
	 */
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!FMLEnvironment.production && level instanceof ServerLevel se) {
			this.randomTick(state, se, pos, level.getRandom());
		}
		return super.use(state, level, pos, player, hand, hit);
	}

}
