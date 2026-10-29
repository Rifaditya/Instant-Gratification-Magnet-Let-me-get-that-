// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.magnet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import java.util.List;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MagnetCommandTest {

    private static CommandDispatcher<CommandSourceStack> dispatcher;

    @BeforeAll
    static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        MagnetCommand.register();
        dispatcher = new CommandDispatcher<>();
        CommandRegistrationCallback.EVENT.invoker().register(dispatcher, null, null);
    }

    @Test
    @DisplayName("Pillar 3: Verify command tree hierarchy for /magnet and /ig_magnet")
    void testCommandTreeHierarchy() {
        for (String rootLiteral : List.of("magnet", "ig_magnet")) {
            CommandNode<CommandSourceStack> root = dispatcher.getRoot().getChild(rootLiteral);
            assertNotNull(root, "Root literal /" + rootLiteral + " must exist in dispatcher");

            CommandNode<CommandSourceStack> toggleNode = root.getChild("toggle");
            assertNotNull(toggleNode, "Child 'toggle' under /" + rootLiteral + " must exist");

            CommandNode<CommandSourceStack> debugNode = root.getChild("debug");
            assertNotNull(debugNode, "Child 'debug' under /" + rootLiteral + " must exist");

            CommandNode<CommandSourceStack> logNode = debugNode.getChild("log");
            assertNotNull(logNode, "Child 'log' under /" + rootLiteral + " debug must exist");
        }
    }

    @Test
    @DisplayName("Pillar 3: Verify executable command handlers on toggle, debug, and log nodes")
    void testSubcommandExecutionNodes() {
        for (String rootLiteral : List.of("magnet", "ig_magnet")) {
            CommandNode<CommandSourceStack> root = dispatcher.getRoot().getChild(rootLiteral);
            assertNotNull(root, "Root literal /" + rootLiteral + " must exist");

            CommandNode<CommandSourceStack> toggleNode = root.getChild("toggle");
            assertNotNull(toggleNode, "Subcommand /" + rootLiteral + " toggle must exist");
            assertNotNull(toggleNode.getCommand(), "Subcommand /" + rootLiteral + " toggle must have an executable command handler");

            CommandNode<CommandSourceStack> debugNode = root.getChild("debug");
            assertNotNull(debugNode, "Subcommand /" + rootLiteral + " debug must exist");
            assertNotNull(debugNode.getCommand(), "Subcommand /" + rootLiteral + " debug must have an executable command handler");

            CommandNode<CommandSourceStack> logNode = debugNode.getChild("log");
            assertNotNull(logNode, "Subcommand /" + rootLiteral + " debug log must exist");
            assertNotNull(logNode.getCommand(), "Subcommand /" + rootLiteral + " debug log must have an executable command handler");
        }
    }
}
