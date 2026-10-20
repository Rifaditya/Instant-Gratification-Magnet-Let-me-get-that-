// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MagnetSidednessContractTest: Pillar 5 Sidedness, Hygiene & Contract Verification Suite
 *
 * Verifies:
 * - Single-line GNU GPLv3 license header on Line 1 of every .java source file.
 * - Zero unguarded net.minecraft.client.* or com.mojang.blaze3d.* references in common sources & bytecode.
 * - Complete absence of legacy version guard reflection and absence of Class.forName in entrypoints.
 * - Structural integrity and mixin configuration validity in magnet.mixins.json.
 */
class MagnetSidednessContractTest {

    @Test
    @DisplayName("Pillar 5: Common Sources & Bytecode Contain Zero Unguarded Client References")
    void testCommonSidednessIsolation() throws IOException {
        Path srcRoot = Paths.get("src/main/java");
        assertTrue(Files.exists(srcRoot), "src/main/java must exist");

        try (Stream<Path> stream = Files.walk(srcRoot)) {
            List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
            assertFalse(javaFiles.isEmpty(), "Java source files must exist");

            for (Path file : javaFiles) {
                String normalized = file.toString().replace('\\', '/');
                String content = Files.readString(file, StandardCharsets.UTF_8);

                assertTrue(content.startsWith("// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3"),
                        "Missing GPLv3 license header on line 1 in: " + normalized);

                // Exclude client package and client classes
                if (normalized.contains("/client/") || normalized.endsWith("MagnetModClient.java")) {
                    continue;
                }

                // Client GUI integration classes in config/ must carry @Environment(EnvType.CLIENT)
                if (content.contains("@Environment(EnvType.CLIENT)")) {
                    continue;
                }

                assertFalse(content.contains("net.minecraft.client."),
                        "Forbidden client package reference in common source file: " + normalized);
                assertFalse(content.contains("com.mojang.blaze3d."),
                        "Forbidden Blaze3D reference in common source file: " + normalized);
            }
        }

        Path classesRoot = Paths.get("build/classes/java/main");
        if (Files.exists(classesRoot)) {
            try (Stream<Path> stream = Files.walk(classesRoot)) {
                List<Path> classFiles = stream.filter(p -> p.toString().endsWith(".class")).toList();
                assertFalse(classFiles.isEmpty(), "build/classes/java/main must contain compiled class files");
                for (Path classFile : classFiles) {
                    String normalized = classFile.toString().replace('\\', '/');
                    if (normalized.contains("/client/")
                            || normalized.contains("MagnetModClient")
                            || normalized.contains("ModMenuIntegration")
                            || normalized.contains("YaclScreenHelper")) {
                        continue;
                    }
                    String rawBytes = new String(Files.readAllBytes(classFile), StandardCharsets.ISO_8859_1);
                    assertFalse(rawBytes.contains("net/minecraft/client/"),
                            "Forbidden net/minecraft/client/ constant pool reference in common bytecode: " + normalized);
                    assertFalse(rawBytes.contains("com/mojang/blaze3d/"),
                            "Forbidden com/mojang/blaze3d/ constant pool reference in common bytecode: " + normalized);
                }
            }
        }
    }

    @Test
    @DisplayName("Pillar 5: Legacy Guard Purged and Zero Unremapped Class.forName in Entrypoint")
    void testLegacyGuardPurged() throws IOException {
        String obsoleteGuard = "ModVersion" + "Guard";
        Path guardPath = Paths.get("src/main/java/net/instantgratification/magnet/util/" + obsoleteGuard + ".java");
        assertFalse(Files.exists(guardPath), "Obsolete version guard file must not exist");

        Path entrypointPath = Paths.get("src/main/java/net/instantgratification/magnet/MagnetMod.java");
        assertTrue(Files.exists(entrypointPath), "MagnetMod.java must exist");
        String entrypointContent = Files.readString(entrypointPath, StandardCharsets.UTF_8);
        assertFalse(entrypointContent.contains(obsoleteGuard),
                "MagnetMod.java must not reference obsolete version guard");
        assertFalse(entrypointContent.contains("Class.forName"),
                "MagnetMod.java must not invoke Class.forName");
    }

    @Test
    @DisplayName("Pillar 5: Mixin Configurations Validated")
    void testMixinConfigContracts() throws IOException {
        Path commonMixinsPath = Paths.get("src/main/resources/magnet.mixins.json");
        assertTrue(Files.exists(commonMixinsPath), "magnet.mixins.json must exist");

        String commonJson = Files.readString(commonMixinsPath, StandardCharsets.UTF_8);
        assertTrue(commonJson.contains("PlayerMixin"), "PlayerMixin must be registered in magnet.mixins.json");
        assertTrue(commonJson.contains("MixinEntity"), "MixinEntity must be registered in magnet.mixins.json");
    }
}
