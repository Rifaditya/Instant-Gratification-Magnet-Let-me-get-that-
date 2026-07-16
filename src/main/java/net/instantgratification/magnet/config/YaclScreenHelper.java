package net.instantgratification.magnet.config;

// Verified against: YaclScreenHelper.java (YACL 3.9.5)
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        MagnetConfig config = MagnetConfig.get();

        return YetAnotherConfigLib.createBuilder()
            .title(Component.translatable("config.ig_magnet.title"))
            
            // GENERAL CATEGORY
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.ig_magnet.category.general"))
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.ig_magnet.category.general"))
                    
                    // Enabled
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.enabled"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.enabled.description")))
                        .binding(
                            true,
                            () -> config.enabled,
                            val -> config.enabled = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // Range
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.ig_magnet.range"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.range.description")))
                        .binding(
                            12,
                            () -> config.range,
                            val -> config.range = val
                        )
                        .customController(opt -> new IntegerSliderController(opt, 1, 64, 1))
                        .build()
                    )
                    
                    // Instant
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.instant"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.instant.description")))
                        .binding(
                            false,
                            () -> config.instant,
                            val -> config.instant = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // NoClip
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.noClip"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.noClip.description")))
                        .binding(
                            true,
                            () -> config.noClip,
                            val -> config.noClip = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    .build()
                )
                .build()
            )
            
            // SPEEDS CATEGORY
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.ig_magnet.category.speeds"))
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.ig_magnet.category.speeds"))
                    
                    // Speed
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.ig_magnet.speed"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.speed.description")))
                        .binding(
                            80,
                            () -> config.speed,
                            val -> config.speed = val
                        )
                        .customController(opt -> new IntegerSliderController(opt, 1, 1000, 1))
                        .build()
                    )
                    
                    // Acceleration
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.ig_magnet.acceleration"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.acceleration.description")))
                        .binding(
                            10,
                            () -> config.acceleration,
                            val -> config.acceleration = val
                        )
                        .customController(opt -> new IntegerSliderController(opt, 1, 1000, 1))
                        .build()
                    )
                    .build()
                )
                .build()
            )
            
            // LINE OF SIGHT CATEGORY
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.ig_magnet.category.los"))
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.ig_magnet.category.los"))
                    
                    // losOnly
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.losOnly"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.losOnly.description")))
                        .binding(
                            true,
                            () -> config.losOnly,
                            val -> config.losOnly = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // keepMovingIfUnseen
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.keepMovingIfUnseen"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.keepMovingIfUnseen.description")))
                        .binding(
                            true,
                            () -> config.keepMovingIfUnseen,
                            val -> config.keepMovingIfUnseen = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // blockedByTransparent
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.blockedByTransparent"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.blockedByTransparent.description")))
                        .binding(
                            false,
                            () -> config.blockedByTransparent,
                            val -> config.blockedByTransparent = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // blockedByFlora
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.blockedByFlora"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.blockedByFlora.description")))
                        .binding(
                            false,
                            () -> config.blockedByFlora,
                            val -> config.blockedByFlora = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // blockedByBlockEntities
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.blockedByBlockEntities"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.blockedByBlockEntities.description")))
                        .binding(
                            false,
                            () -> config.blockedByBlockEntities,
                            val -> config.blockedByBlockEntities = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    .build()
                )
                .build()
            )
            
            // VISUALS & PERFORMANCE CATEGORY
            .category(ConfigCategory.createBuilder()
                .name(Component.translatable("config.ig_magnet.category.visuals"))
                .group(OptionGroup.createBuilder()
                    .name(Component.translatable("config.ig_magnet.category.visuals"))
                    
                    // affectsXp
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.affectsXp"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.affectsXp.description")))
                        .binding(
                            true,
                            () -> config.affectsXp,
                            val -> config.affectsXp = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // particles
                    .option(Option.<Boolean>createBuilder()
                        .name(Component.translatable("config.ig_magnet.particles"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.particles.description")))
                        .binding(
                            true,
                            () -> config.particles,
                            val -> config.particles = val
                        )
                        .controller(BooleanControllerBuilder::create)
                        .build()
                    )
                    
                    // particleCount
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.ig_magnet.particleCount"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.particleCount.description")))
                        .binding(
                            1,
                            () -> config.particleCount,
                            val -> config.particleCount = val
                        )
                        .customController(opt -> new IntegerSliderController(opt, 0, 100, 1))
                        .build()
                    )
                    
                    // maxParticleSources
                    .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("config.ig_magnet.maxParticleSources"))
                        .description(OptionDescription.of(Component.translatable("config.ig_magnet.maxParticleSources.description")))
                        .binding(
                            5,
                            () -> config.maxParticleSources,
                            val -> config.maxParticleSources = val
                        )
                        .customController(opt -> new IntegerSliderController(opt, 0, 100, 1))
                        .build()
                    )
                    .build()
                )
                .build()
            )
            .save(MagnetConfig::save)
            .build()
            .generateScreen(parent);
    }
}
