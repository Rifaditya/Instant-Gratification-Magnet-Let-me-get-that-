// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class MagnetModClient implements ClientModInitializer {

    public static final KeyMapping toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
        "key.ig_magnet.toggle",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_BACKSLASH,
        "key.category.ig_magnet.magnet"
    ));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                if (client.player != null && !client.player.isDeadOrDying() && !client.player.isSpectator()) {
                    boolean currentLocalState = ((IMagnetPlayer) client.player).ig_magnet$isMagnetEnabled();
                    boolean newState = !currentLocalState;
                    MagnetDebugLogger.log("MagnetModClient: Keybind clicked by %s (%s) currentLocalState=%b newState=%b", client.player.getScoreboardName(), client.player.getUUID(), currentLocalState, newState);
                    ((IMagnetPlayer) client.player).ig_magnet$setMagnetEnabled(newState);

                    ClientPlayNetworking.send(new MagnetTogglePayload(newState));

                    // Verified against: Gui.java (1.20.1)
                    if (newState) {
                        client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
                    } else {
                        client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
                    }
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (packet, player, responseSender) -> {
            Minecraft client = Minecraft.getInstance();
            client.execute(() -> {
                if (client.player instanceof IMagnetPlayer magnetPlayer) {
                    MagnetDebugLogger.log("MagnetModClient: Received S2C sync packet enabled=%b", packet.enabled());
                    magnetPlayer.ig_magnet$setMagnetEnabled(packet.enabled());
                }
            });
        });
    }
}
