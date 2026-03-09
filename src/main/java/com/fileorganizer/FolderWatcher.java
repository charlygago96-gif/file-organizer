package com.fileorganizer;

import java.io.IOException;
import java.nio.file.*;

public class FolderWatcher {

    private final Path folderToWatch;
    private final FileClassifier classifier;
    private final FileMover mover;
    private final Logger logger;

    public FolderWatcher(Path folderToWatch) {
        this.folderToWatch = folderToWatch;
        this.classifier = new FileClassifier();
        this.mover = new FileMover();
        this.logger = new Logger(folderToWatch);
    }

    public void start() throws IOException, InterruptedException {
        logger.logInicio(folderToWatch);
        System.out.println("Presiona Ctrl+C para detener.\n");

        WatchService watchService = FileSystems.getDefault().newWatchService();
        folderToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.OVERFLOW) continue;

                Path fileName = (Path) event.context();
                Path fullPath = folderToWatch.resolve(fileName);

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

    private void processFile(Path file) {
        if (!Files.exists(file) || Files.isDirectory(file)) return;

        String fileName = file.getFileName().toString();

        // No procesar el propio archivo de log
        if (fileName.equals("organizador.log")) return;

        FileType fileType = classifier.classify(fileName);

        try {
            mover.move(file, folderToWatch, fileType);
            logger.logMovimiento(fileName, fileType);
        } catch (IOException e) {
            logger.logError(fileName, e.getMessage());
        }
    }
}