package com.github.mribby.shulkerbag;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class ShulkerBagInventory extends BasicInventory {
	public ShulkerBagInventory(int size) {
		super(size);
	}

	public ShulkerBagInventory(ItemStack... itemStacks_1) {
		super(itemStacks_1);
	}

	@Override
	public ItemStack removeInvStack(int int_1) {
		ItemStack stack = super.removeInvStack(int_1);
		if (!stack.isEmpty())
			markDirty();
		return stack;
	}

	@Override
	public void clear() {
		boolean wasEmpty = isInvEmpty();
		super.clear();
		if (!wasEmpty)
			markDirty();
	}

	@Override
	public void onInvOpen(PlayerEntity playerEntity_1) {
		// TODO: don't use leather armor equip for open sound?
		playerEntity_1.world.playSound((PlayerEntity)null, new BlockPos(playerEntity_1), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 0.5F, playerEntity_1.world.random.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public void onInvClose(PlayerEntity playerEntity_1) {
		// TODO: don't use leather armor equip for close sound?
		playerEntity_1.world.playSound((PlayerEntity)null, new BlockPos(playerEntity_1), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, SoundCategory.BLOCKS, 0.5F, playerEntity_1.world.random.nextFloat() * 0.1F + 0.9F);
	}

	public static void fromTag(CompoundTag compoundTag_1, Inventory inventory) {
		ListTag listTag_1 = compoundTag_1.getList("Items", 10);

		for(int int_1 = 0; int_1 < listTag_1.size(); ++int_1) {
			CompoundTag compoundTag_2 = listTag_1.getCompoundTag(int_1);
			int int_2 = compoundTag_2.getByte("Slot") & 255;
			if (int_2 >= 0 && int_2 < inventory.getInvSize()) {
				inventory.setInvStack(int_2, ItemStack.fromTag(compoundTag_2));
			}
		}

	}

	public static CompoundTag toTag(CompoundTag compoundTag_1, Inventory inventory, boolean boolean_1) {
		ListTag listTag_1 = new ListTag();

		for(int int_1 = 0; int_1 < inventory.getInvSize(); ++int_1) {
			ItemStack itemStack_1 = inventory.getInvStack(int_1);
			if (!itemStack_1.isEmpty()) {
				CompoundTag compoundTag_2 = new CompoundTag();
				compoundTag_2.putByte("Slot", (byte)int_1);
				itemStack_1.toTag(compoundTag_2);
				listTag_1.add(compoundTag_2);
			}
		}

		if (!listTag_1.isEmpty() || boolean_1) {
			compoundTag_1.put("Items", listTag_1);
		}

		return compoundTag_1;
	}
}
