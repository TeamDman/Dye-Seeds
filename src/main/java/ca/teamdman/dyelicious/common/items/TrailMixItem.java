package ca.teamdman.dyelicious.common.items;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Consumer;

public class TrailMixItem extends Item {
	public static DyeColor COLOUR;

	public TrailMixItem(DyeColor colour) {
		super(new Properties());
		this.COLOUR = colour;
	}

	public static ItemStack getIngredient(ItemStack trailMixStack) {
		throw new NotImplementedException("todo");
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return IClientItemExtensions.super.getCustomRenderer();
			}
		})
	}
}
