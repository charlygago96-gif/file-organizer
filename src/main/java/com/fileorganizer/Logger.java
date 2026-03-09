package com.fileorganizer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Guarda un registro de todas las acciones realizadas por el organizador
public class Logger {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final Path logFile;

    public Logger(Path watchedFolder) {
        this.logFile = watchedFolder.resolve("organizador.log");
    }

    // Registra un movimiento exitoso
    public void logMovimiento(String fileName, FileType fileType) {
        String mensaje = String.format("[%s] ✅ Movido: %s → %s",
                LocalDateTime.now().format(FORMATTER),
                fileName,
                fileType.name());
        escribir(mensaje);
        System.out.println(mensaje);
    }

    // Registra un error
    public void logError(String fileName, String error) {
        String mensaje = String.format("[%s] ❌ Error con %s: %s",
                LocalDateTime.now().format(FORMATTER),
                fileName,
                error);
        escribir(mensaje);
        System.out.println(mensaje);
    }

    // Registra el inicio del programa
    public void logInicio(Path folder) {
        String mensaje = String.format("[%s] 🚀 Organizador iniciado en: %s",
                LocalDateTime.now().format(FORMATTER),
                folder.toString());
        escribir(mensaje);
        System.out.println(mensaje);
    }

    // Escribe una línea en el archivo de log
    private void escribir(String mensaje) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile.toFile(), true))) {
            writer.println(mensaje);
        } catch (IOException e) {
            System.out.println("⚠️ No se pudo escribir en el log: " + e.getMessage());
        }
    }
}