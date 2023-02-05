package ca.teamdman.dyelicious.common.items;

import ca.teamdman.dyelicious.client.TrailMixRenderer;
import ca.teamdman.dyelicious.common.registry.ModItems;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class TrailMixItem extends Item {

	public static DyeColor COLOUR;

	public TrailMixItem(DyeColor colour) {
		super(new Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(1.23f).build()));
		this.COLOUR = colour;
	}

	public static ItemStack withData(DyeColor dye, ItemStack food, ItemStack other) {
		var stack = new ItemStack(ModItems.TRAIL_MIX_LOOKUP.get(dye).get());
		if (!food.isEmpty())
			stack.addTagElement("food", food.save(new net.minecraft.nbt.CompoundTag()));
		if (!other.isEmpty())
			stack.addTagElement("other", other.save(new net.minecraft.nbt.CompoundTag()));
		return stack;
	}

	@Override
	public SoundEvent getEatingSound() {
		return super.getEatingSound();
	}

	public static ItemStack getOther(ItemStack stack) {
		if (!(stack.getItem() instanceof TrailMixItem)) return ItemStack.EMPTY;
		var tag = stack.getTagElement("other");
		if (tag == null) return ItemStack.EMPTY;
		var other = ItemStack.of(tag);
		return other;
	}

	public static ItemStack getFood(ItemStack stack) {
		if (!(stack.getItem() instanceof TrailMixItem)) return ItemStack.EMPTY;
		var tag = stack.getTagElement("food");
		if (tag == null) return ItemStack.EMPTY;
		var food = ItemStack.of(tag);
		return food;
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
