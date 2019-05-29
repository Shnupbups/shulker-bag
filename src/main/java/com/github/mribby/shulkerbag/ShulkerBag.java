package com.github.mribby.shulkerbag;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;

public class ShulkerBag implements ModInitializer {

	public static final Identifier BAG_ID = new Identifier("shulker_bag", "shulker_bag");

	@Override
	public void onInitialize() {

		// TODO: dyed shulker bags

		// TODO: automatically close the inventory if the bag item is not equipped in the player's hand(s) anymore

		ContainerProviderRegistry.INSTANCE.registerFactory(BAG_ID, ShulkerBag::createContainer);
		ScreenProviderRegistry.INSTANCE.registerFactory(BAG_ID, ShulkerBag::createScreen);
		Registry.register(Registry.ITEM, BAG_ID, new ShulkerBagItem());
	}

	public static void openContainer(PlayerEntity playerEntity_1, Hand hand_1) {
		if (playerEntity_1 instanceof ServerPlayerEntity) {
			ContainerProviderRegistry.INSTANCE.openContainer(ShulkerBag.BAG_ID, playerEntity_1, buf -> buf.writeEnumConstant(hand_1));
		}
	}

	private static Container createContainer(int syncId, Identifier identifier, PlayerEntity player, PacketByteBuf buf) {
		Hand hand = buf.readEnumConstant(Hand.class);
		ItemStack stack = player.getStackInHand(hand);
		ShulkerBagInventory inventory = new ShulkerBagInventory(27);
		deserializeInventory(inventory, stack.getSubCompoundTag("BlockEntityTag"));
		// TODO: maybe this is too slow so maybe write to the item stack only when the container is closed
		inventory.addListener(inv -> serializeInventory(inv, stack.getOrCreateSubCompoundTag("BlockEntityTag")));
		return new ShulkerBagContainer(syncId, player.inventory, inventory, stack.getDisplayName(), stack);
	}

    private static void serializeInventory(Inventory inventory, CompoundTag compoundTag) {
        ShulkerBagInventory.toTag(compoundTag, inventory, true); // pass true to overwrite existing Items tag including when the inventory is empty
    }

    private static void deserializeInventory(Inventory inventory, CompoundTag compoundTag) {
        if (compoundTag != null && compoundTag.containsKey("Items", 9)) {
            ShulkerBagInventory.fromTag(compoundTag, inventory);
        }
    }

	private static AbstractContainerScreen createScreen(ShulkerBagContainer container) {
		return new ShulkerBoxScreen(container, container.playerInventory, container.bagName);
	}
}
