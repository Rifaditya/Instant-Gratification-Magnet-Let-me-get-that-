// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dasik.social.api.config.GuiHelper;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalYaclFactory(
                "ig_magnet",
                "net.instantgratification.magnet.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
