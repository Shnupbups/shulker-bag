package com.github.mribby.shulkerbag;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;

public class ShulkerBagClient implements ClientModInitializer {

	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(ShulkerBag.BAG_ID, ShulkerBagClient::createScreen);
	}

	private static AbstractContainerScreen createScreen(ShulkerBagContainer container) {
		return new ShulkerBoxScreen(container, container.playerInventory, container.bagName);
	}
}
