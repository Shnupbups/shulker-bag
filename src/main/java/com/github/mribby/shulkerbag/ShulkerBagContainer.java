package com.github.mribby.shulkerbag;

import net.minecraft.container.ShulkerBoxContainer;
import net.minecraft.container.ShulkerBoxSlot;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.chat.Component;

public class ShulkerBagContainer extends ShulkerBoxContainer {
	public final PlayerInventory playerInventory;
	public final Component bagName;
	private final ItemStack bagStack;

	public ShulkerBagContainer(int syncId, PlayerInventory playerInventory_1, Inventory inventory_1, Component bagName, ItemStack bagStack) {
		super(syncId, playerInventory_1, inventory_1);
		this.playerInventory = playerInventory_1;
		this.bagName = bagName;
		this.bagStack = bagStack;

		slotList.clear();

		int int_8;
		int int_7;
		for(int_8 = 0; int_8 < 3; ++int_8) {
			for(int_7 = 0; int_7 < 9; ++int_7) {
				this.reAddSlot(new ShulkerBagSlot(inventory_1, int_7 + int_8 * 9, 8 + int_7 * 18, 18 + int_8 * 18));
			}
		}

		for(int_8 = 0; int_8 < 3; ++int_8) {
			for(int_7 = 0; int_7 < 9; ++int_7) {
				this.reAddSlot(new Slot(playerInventory_1, int_7 + int_8 * 9 + 9, 8 + int_7 * 18, 84 + int_8 * 18));
			}
		}

		for(int_8 = 0; int_8 < 9; ++int_8) {
			this.reAddSlot(new Slot(playerInventory_1, int_8, 8 + int_8 * 18, 142));
		}
	}

	protected Slot reAddSlot(Slot slot_1) {
		slot_1.id = this.slotList.size();
		this.slotList.add(slot_1);
		return slot_1;
	}

	@Override
	public void close(PlayerEntity playerEntity_1) {
		super.close(playerEntity_1);
	}
}
