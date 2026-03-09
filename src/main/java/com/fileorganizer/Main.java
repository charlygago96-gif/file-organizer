package com.fileorganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// Punto de entrada del programa
public class Main {

    public static void main(String[] args) {
        // Si se pasa una ruta como argumento se usa, si no se usa la carpeta actual
        String folderPath = args.length > 0 ? args[0] : System.getProperty("user.home") + "/Downloads";

        Path folder = Paths.get(folderPath);

        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            System.out.println("❌ La carpeta no existe: " + folderPath);
            System.exit(1);
        }

        FolderWatcher watcher = new FolderWatcher(folder);

        try {
            watcher.start();
        } catch (IOException e) {
            System.out.println("❌ Error al iniciar el monitor: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("🛑 Monitor detenido.");
        }
    }
}