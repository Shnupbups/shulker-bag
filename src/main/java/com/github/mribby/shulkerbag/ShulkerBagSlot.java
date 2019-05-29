package com.github.mribby.shulkerbag;

import net.minecraft.container.ShulkerBoxSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class ShulkerBagSlot extends ShulkerBoxSlot {
    public ShulkerBagSlot(Inventory inventory_1, int int_1, int int_2, int int_3) {
        super(inventory_1, int_1, int_2, int_3);
    }

    @Override
    public boolean canInsert(ItemStack itemStack_1) {
        return !(itemStack_1.getItem() instanceof ShulkerBagItem) && super.canInsert(itemStack_1);
    }
}
