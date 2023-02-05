package ca.teamdman.dyelicious.client;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TrailMixRenderer extends BlockEntityWithoutLevelRenderer {


	public TrailMixRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet entityModelSet) {
		super(dispatcher, entityModelSet);
	}
}
