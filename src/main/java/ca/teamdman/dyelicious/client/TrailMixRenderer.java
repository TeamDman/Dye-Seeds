package ca.teamdman.dyelicious.client;

import ca.teamdman.dyelicious.Dyelicious;
import ca.teamdman.dyelicious.common.items.TrailMixItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Dyelicious.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TrailMixRenderer extends BlockEntityWithoutLevelRenderer {

	private static final ResourceLocation BASE_MODEL = new ResourceLocation(Dyelicious.MOD_ID, "item/trail_mix_base");
	public TrailMixRenderer() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	// Thanks Shadows
	// https://github.com/Shadows-of-Fire/Hostile-Neural-Networks/blob/1.18/src/main/java/shadows/hostilenetworks/client/DataModelItemStackRenderer.java#L71
	// https://discord.com/channels/313125603924639766/915304642668290119/1029330876208795758
	@Override
	public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBuffer, int packedLight, int packedOverlay) {
		var renderer = Minecraft.getInstance().getItemRenderer();
		var baseModel = renderer.getItemModelShaper().getModelManager().getModel(BASE_MODEL);
		var renderType = ItemBlockRenderTypes.getRenderType(stack, true);
		var buffer = ItemRenderer.getFoilBufferDirect(multiBuffer, renderType, true, stack.hasFoil());
		poseStack.pushPose();

		// render base item
		if (transformType == ItemTransforms.TransformType.FIXED) {
//			poseStack.translate(1, 1, 0);
//			float scale = 0.5F;
//			poseStack.scale(scale, scale, scale);
//			poseStack.translate(-1.5F, -0.5F, 0.5F);
//			poseStack.mulPose(Axis.XP.rotationDegrees(90));
//			poseStack.mulPose(Axis.XP.rotationDegrees(90));
//			poseStack.translate(0, 0, -1);
		} else if (transformType == ItemTransforms.TransformType.GUI) {
//			poseStack.translate(0, -.5F, -.5F);
//			poseStack.mulPose(Axis.XN.rotationDegrees(75));
//			poseStack.mulPose(Axis.ZP.rotationDegrees(45));
//			float scale = 0.9F;
//			poseStack.scale(scale, scale, scale);
//			poseStack.translate(0.775, 0, -0.0825);
		} else { // rendering in hand
			poseStack.translate(0.60, 0.5, 0.25);
			poseStack.scale(0.5F, 0.5F, 0.5F);
			poseStack.mulPose(Axis.YP.rotationDegrees(-65));
		}

		renderer.renderModelLists(baseModel, stack, packedLight, packedOverlay, poseStack, buffer);

		// render food
		var food = TrailMixItem.getFood(stack);
		if (!food.isEmpty()) {
			var foodModel = renderer.getItemModelShaper().getItemModel(food.getItem());
			poseStack.scale(0.75f,0.75f,1f);
			poseStack.translate(0.25,0.25,0f);
			renderer.renderModelLists(foodModel, stack, packedLight, packedOverlay, poseStack, buffer);
		}

		// render other
		var other = TrailMixItem.getOther(stack);
		if (!other.isEmpty()) {
			var otherModel = renderer.getItemModelShaper().getItemModel(other.getItem());
			poseStack.scale(0.75f,0.75f,1f);
			poseStack.translate(0.25,0.25,0.05f);
			renderer.renderModelLists(otherModel, stack, packedLight, packedOverlay, poseStack, buffer);
		}

		// render seeds
		var seedsModel = renderer.getItemModelShaper().getItemModel(Items.WHEAT_SEEDS);
		poseStack.scale(0.75f,0.75f,1f);
		poseStack.translate(0.25,0.25,0.05f);
		renderer.renderModelLists(seedsModel, stack, packedLight, packedOverlay, poseStack, buffer);

		poseStack.popPose();
	}

	@SubscribeEvent
	public static void registerModels(ModelEvent.RegisterAdditional event) {
		event.register(new ResourceLocation(Dyelicious.MOD_ID, "item/trail_mix_base"));
	}
}
