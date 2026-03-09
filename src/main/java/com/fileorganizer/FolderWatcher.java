package com.fileorganizer;

import java.io.IOException;
import java.nio.file.*;

// Monitorea una carpeta y reacciona cuando se añade un archivo nuevo
public class FolderWatcher {

    private final Path folderToWatch;
    private final FileClassifier classifier;
    private final FileMover mover;

    public FolderWatcher(Path folderToWatch) {
        this.folderToWatch = folderToWatch;
        this.classifier = new FileClassifier();
        this.mover = new FileMover();
    }

    // Inicia el monitoreo en bucle infinito
    public void start() throws IOException, InterruptedException {
        System.out.println("👀 Monitoreando carpeta: " + folderToWatch);
        System.out.println("Presiona Ctrl+C para detener.\n");

        WatchService watchService = FileSystems.getDefault().newWatchService();
        folderToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        while (true) {
            WatchKey key = watchService.take(); // espera hasta que ocurra algo

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // Ignorar eventos de desbordamiento
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                Path fileName = (Path) event.context();
                Path fullPath = folderToWatch.resolve(fileName);

                // Esperar un momento para que el archivo termine de copiarse
                Thread.sleep(500);

                processFile(fullPath);
            }

            boolean valid = key.reset();
            if (!valid) {
                System.out.println("⚠️ La carpeta ya no es accesible. Deteniendo...");
                break;
            }
        }
    }

    // Clasifica y mueve el archivo detectado
    private void processFile(Path file) {
        if (!Files.exists(file) || Files.isDirectory(file)) {
            return;
        }

        String fileName = file.getFileName().toString();
        FileType fileType = classifier.classify(fileName);

        try {
            mover.move(file, folderToWatch, fileType);
            System.out.println("✅ Movido: " + fileName + " → " + fileType.name());
        } catch (IOException e) {
            System.out.println("❌ Error al mover " + fileName + ": " + e.getMessage());
        }
    }
}