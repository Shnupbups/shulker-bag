package com.github.mribby.shulkerbag;

import net.minecraft.ChatFormat;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ShulkerBagItem extends Item {
    public ShulkerBagItem() {
        super((new Settings()).stackSize(1).itemGroup(ItemGroup.MISC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1) {
        ItemStack itemStack_1 = playerEntity_1.getStackInHand(hand_1);
        ShulkerBag.openContainer(playerEntity_1, hand_1);
        playerEntity_1.incrementStat(Stats.USED.getOrCreateStat(this));
        return new TypedActionResult<>(ActionResult.SUCCESS, itemStack_1);
    }

    /** @see net.minecraft.block.ShulkerBoxBlock#buildTooltip */
    @Override
    public void buildTooltip(ItemStack itemStack_1, World world_1, List<Component> list_1, TooltipContext tooltipContext_1) {
        super.buildTooltip(itemStack_1, world_1, list_1, tooltipContext_1);
        CompoundTag compoundTag_1 = itemStack_1.getSubCompoundTag("BlockEntityTag");
        if (compoundTag_1 != null) {
            if (compoundTag_1.containsKey("Items", 9)) {
                DefaultedList<ItemStack> defaultedList_1 = DefaultedList.create(27, ItemStack.EMPTY);
                Inventories.fromTag(compoundTag_1, defaultedList_1);
                int int_1 = 0;
                int int_2 = 0;
                Iterator var9 = defaultedList_1.iterator();

                while(var9.hasNext()) {
                    ItemStack itemStack_2 = (ItemStack)var9.next();
                    if (!itemStack_2.isEmpty()) {
                        ++int_2;
                        if (int_1 <= 4) {
                            ++int_1;
                            Component component_1 = itemStack_2.getDisplayName().copy();
                            component_1.append(" x").append(String.valueOf(itemStack_2.getAmount()));
                            list_1.add(component_1);
                        }
                    }
                }

                if (int_2 - int_1 > 0) {
                    list_1.add((new TranslatableComponent("container.shulkerBox.more", new Object[]{int_2 - int_1})).applyFormat(ChatFormat.ITALIC));
                }
            }
        }
    }
}
