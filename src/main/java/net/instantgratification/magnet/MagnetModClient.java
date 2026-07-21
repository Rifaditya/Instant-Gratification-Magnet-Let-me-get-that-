// Verified against: ClientModInitializer.java (26.2+)
package net.instantgratification.magnet;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class MagnetModClient implements ClientModInitializer {
    public static final KeyMapping.Category MAGNET_CATEGORY = KeyMapping.Category.register(
        Identifier.fromNamespaceAndPath(MagnetMod.MOD_ID, "magnet")
    );

    private static InputConstants.Type ig_magnet$getKeyboardType() {
        try {
            return Enum.valueOf(InputConstants.Type.class, "KEYBOARD");
        } catch (IllegalArgumentException e) {
            return Enum.valueOf(InputConstants.Type.class, "KEYSYM");
        }
    }

    public static final KeyMapping toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
        "key.ig_magnet.toggle",
        ig_magnet$getKeyboardType(),
        GLFW.GLFW_KEY_BACKSLASH,
        MAGNET_CATEGORY
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

                    // Verified against: Gui.java (26.2+) and Hud.java (26.2+)
                    if (newState) {
                        client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
                    } else {
                        client.gui.hud.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
                    }
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                if (context.player() != null) {
                    MagnetDebugLogger.log("MagnetModClient: Received S2C sync packet for %s (%s) enabled=%b", context.player().getScoreboardName(), context.player().getUUID(), payload.enabled());
                    ((IMagnetPlayer) context.player()).ig_magnet$setMagnetEnabled(payload.enabled());
                } else {
                    MagnetDebugLogger.log("MagnetModClient: Received S2C sync packet, but context player is null!");
                }
            });
        });
    }
}
