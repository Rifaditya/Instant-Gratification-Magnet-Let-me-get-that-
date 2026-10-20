// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: FabricPacket.java (1.20.1)
package net.instantgratification.magnet;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record MagnetTogglePayload(boolean enabled) implements FabricPacket {
    public static final PacketType<MagnetTogglePayload> TYPE = PacketType.create(
        new ResourceLocation(MagnetMod.MOD_ID, "toggle"),
        MagnetTogglePayload::new
    );

    public MagnetTogglePayload(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.enabled);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }
}
