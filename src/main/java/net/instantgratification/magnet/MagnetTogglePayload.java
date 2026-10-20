// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: CustomPacketPayload.java (1.21.1)
package net.instantgratification.magnet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MagnetTogglePayload(boolean enabled) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MagnetTogglePayload> TYPE = new CustomPacketPayload.Type<>(
        ResourceLocation.fromNamespaceAndPath(MagnetMod.MOD_ID, "toggle")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, MagnetTogglePayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        MagnetTogglePayload::enabled,
        MagnetTogglePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
