// Verified against: ClientModInitializer.java (26.1.2)
package net.instantgratification.magnet;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class MagnetModClient implements ClientModInitializer {
    public static final KeyMapping.Category MAGNET_CATEGORY = KeyMapping.Category.register(
        Identifier.fromNamespaceAndPath(MagnetMod.MOD_ID, "magnet")
    );

    public static final KeyMapping toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
        "key.ig_magnet.toggle",
        InputConstants.Type.KEYSYM,
        GLFW.GLFW_KEY_M,
        MAGNET_CATEGORY
    ));

    public static boolean isControlDown() {
        Window window = Minecraft.getInstance().getWindow();
        boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        if (isMac) {
            return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SUPER) ||
                   InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SUPER);
        }
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL) ||
               InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                if (client.player != null) {
                    if (isControlDown()) {
                        boolean currentLocalState = MagnetPlayerState.isMagnetEnabled(client.player);
                        boolean newState = !currentLocalState;
                        MagnetPlayerState.setMagnetEnabled(client.player, newState);

                        ClientPlayNetworking.send(new MagnetTogglePayload(newState));

                        if (newState) {
                            client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.enabled"), true);
                        } else {
                            client.gui.setOverlayMessage(Component.translatable("chat.ig_magnet.disabled"), true);
                        }
                    }
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(MagnetTogglePayload.TYPE, (payload, context) -> {
            context.client().execute(() -> {
                if (context.player() != null) {
                    MagnetPlayerState.setMagnetEnabled(context.player(), payload.enabled());
                }
            });
        });
    }
}
