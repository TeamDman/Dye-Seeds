package ca.teamdman.dyelicious.common.items;

import ca.teamdman.dyelicious.client.TrailMixRenderer;
import ca.teamdman.dyelicious.common.registry.ModItems;
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

	public static ItemStack withIngredient(DyeColor dye, ItemStack ingredient) {
		var stack = new ItemStack(ModItems.TRAIL_MIX_LOOKUP.get(dye).get());
		stack.addTagElement("ingredient", ingredient.save(new net.minecraft.nbt.CompoundTag()));
		return stack;
	}

	public static ItemStack getIngredient(ItemStack stack) {
		if (!(stack.getItem() instanceof  TrailMixItem)) return ItemStack.EMPTY;
		var tag = stack.getTagElement("ingredient");
		if (tag == null) return ItemStack.EMPTY;
		var ingredient = ItemStack.of(tag);
		return ingredient;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new TrailMixRenderer();
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}
}
