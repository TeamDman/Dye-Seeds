package ca.teamdman.dyelicious.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class Dyeguritator extends Block {
	public Dyeguritator() {
		super(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops());
	}


}
