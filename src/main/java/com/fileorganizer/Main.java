package com.fileorganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path folder;

        // Intentar cargar desde config.properties
        try {
            ConfigLoader config = new ConfigLoader("config.properties");
            folder = config.getFolderToWatch();
            System.out.println("⚙️ Configuración cargada desde config.properties");
        } catch (IOException e) {
            // Si no existe el archivo, usar argumento o Descargas por defecto
            String folderPath = args.length > 0 ? args[0] : System.getProperty("user.home") + "/Downloads";
            folder = Paths.get(folderPath);
            System.out.println("⚙️ Usando carpeta por defecto: " + folderPath);
        }

        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            System.out.println("❌ La carpeta no existe: " + folder);
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