// Verified against: ClothConfigScreenHelper.java (26.1.2+)
package net.instantgratification.magnet.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreenHelper {
    public static ConfigScreenFactory<?> createFactory() {
        return ClothConfigScreenHelper::createScreen;
    }

    public static Screen createScreen(Screen parent) {
        MagnetConfig config = MagnetConfig.get();
        
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.ig_magnet.title"));

        builder.setSavingRunnable(MagnetConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // --- GENERAL CATEGORY ---
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.ig_magnet.category.general"));
        
        // Add static warning block at the top of General settings
        general.addEntry(entryBuilder.startTextDescription(Component.translatable("config.ig_magnet.warning")).build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.enabled"), config.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.ig_magnet.enabled.description"))
                .setSaveConsumer(val -> config.enabled = val)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Component.translatable("config.ig_magnet.range"), config.range, 1, 64)
                .setDefaultValue(12)
                .setTooltip(Component.translatable("config.ig_magnet.range.description"))
                .setSaveConsumer(val -> config.range = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.instant"), config.instant)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.ig_magnet.instant.description"))
                .setSaveConsumer(val -> config.instant = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.noClip"), config.noClip)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.ig_magnet.noClip.description"))
                .setSaveConsumer(val -> config.noClip = val)
                .build());

        // --- SPEEDS CATEGORY ---
        ConfigCategory speeds = builder.getOrCreateCategory(Component.translatable("config.ig_magnet.category.speeds"));
        speeds.addEntry(entryBuilder.startTextDescription(Component.translatable("config.ig_magnet.warning")).build());

        speeds.addEntry(entryBuilder.startIntSlider(Component.translatable("config.ig_magnet.speed"), config.speed, 1, 1000)
                .setDefaultValue(80)
                .setTooltip(Component.translatable("config.ig_magnet.speed.description"))
                .setSaveConsumer(val -> config.speed = val)
                .build());

        speeds.addEntry(entryBuilder.startIntSlider(Component.translatable("config.ig_magnet.acceleration"), config.acceleration, 1, 1000)
                .setDefaultValue(10)
                .setTooltip(Component.translatable("config.ig_magnet.acceleration.description"))
                .setSaveConsumer(val -> config.acceleration = val)
                .build());

        // --- LINE OF SIGHT CATEGORY ---
        ConfigCategory los = builder.getOrCreateCategory(Component.translatable("config.ig_magnet.category.los"));
        los.addEntry(entryBuilder.startTextDescription(Component.translatable("config.ig_magnet.warning")).build());

        los.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.losOnly"), config.losOnly)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.ig_magnet.losOnly.description"))
                .setSaveConsumer(val -> config.losOnly = val)
                .build());

        los.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.keepMovingIfUnseen"), config.keepMovingIfUnseen)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.ig_magnet.keepMovingIfUnseen.description"))
                .setSaveConsumer(val -> config.keepMovingIfUnseen = val)
                .build());

        los.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.blockedByTransparent"), config.blockedByTransparent)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.ig_magnet.blockedByTransparent.description"))
                .setSaveConsumer(val -> config.blockedByTransparent = val)
                .build());

        los.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.blockedByFlora"), config.blockedByFlora)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.ig_magnet.blockedByFlora.description"))
                .setSaveConsumer(val -> config.blockedByFlora = val)
                .build());

        los.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.blockedByBlockEntities"), config.blockedByBlockEntities)
                .setDefaultValue(false)
                .setTooltip(Component.translatable("config.ig_magnet.blockedByBlockEntities.description"))
                .setSaveConsumer(val -> config.blockedByBlockEntities = val)
                .build());

        // --- VISUALS & PERFORMANCE CATEGORY ---
        ConfigCategory visuals = builder.getOrCreateCategory(Component.translatable("config.ig_magnet.category.visuals"));
        visuals.addEntry(entryBuilder.startTextDescription(Component.translatable("config.ig_magnet.warning")).build());

        visuals.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.affectsXp"), config.affectsXp)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.ig_magnet.affectsXp.description"))
                .setSaveConsumer(val -> config.affectsXp = val)
                .build());

        visuals.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.ig_magnet.particles"), config.particles)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.ig_magnet.particles.description"))
                .setSaveConsumer(val -> config.particles = val)
                .build());

        visuals.addEntry(entryBuilder.startIntSlider(Component.translatable("config.ig_magnet.particleCount"), config.particleCount, 0, 100)
                .setDefaultValue(1)
                .setTooltip(Component.translatable("config.ig_magnet.particleCount.description"))
                .setSaveConsumer(val -> config.particleCount = val)
                .build());

        visuals.addEntry(entryBuilder.startIntSlider(Component.translatable("config.ig_magnet.maxParticleSources"), config.maxParticleSources, 0, 100)
                .setDefaultValue(5)
                .setTooltip(Component.translatable("config.ig_magnet.maxParticleSources.description"))
                .setSaveConsumer(val -> config.maxParticleSources = val)
                .build());

        return builder.build();
    }
}
