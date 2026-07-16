package net.instantgratification.magnet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MagnetDebugLogger {
    private static final Path LOG_PATH = Paths.get("logs", "ig_magnet_debug.log");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static boolean enabled = false;

    public static synchronized void log(String message, Object... args) {
        if (!enabled) {
            return;
        }
        String formattedMessage;
        try {
            formattedMessage = String.format(message, args);
        } catch (Exception e) {
            formattedMessage = message; // Fallback if formatting fails
        }
        
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logLine = "[" + timestamp + "] " + formattedMessage;

        // Also output to vanilla logger
        MagnetMod.LOGGER.info("[DebugLog] " + formattedMessage);

        try {
            Files.createDirectories(LOG_PATH.getParent());
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_PATH.toFile(), true))) {
                writer.println(logLine);
            }
        } catch (IOException e) {
            MagnetMod.LOGGER.error("Failed to write to ig_magnet_debug.log", e);
        }
    }
}
