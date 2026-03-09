package com.fileorganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

// Se encarga de mover físicamente los archivos a su carpeta destino
public class FileMover {

    // Mueve el archivo a la subcarpeta correspondiente a su tipo
    public void move(Path file, Path watchedFolder, FileType fileType) throws IOException {
        Path targetFolder = watchedFolder.resolve(fileType.name());

        // Crea la carpeta destino si no existe
        if (!Files.exists(targetFolder)) {
            Files.createDirectories(targetFolder);
        }

        Path destination = resolveConflict(targetFolder, file.getFileName().toString());
        Files.move(file, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    // Si ya existe un archivo con ese nombre, le añade un número al final
    private Path resolveConflict(Path folder, String fileName) {
        Path destination = folder.resolve(fileName);

        if (!Files.exists(destination)) {
            return destination;
        }

        String name = fileName;
        String extension = "";
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex != -1) {
            name = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        }

        int counter = 1;
        while (Files.exists(destination)) {
            destination = folder.resolve(name + "(" + counter + ")" + extension);
            counter++;
        }

        return destination;
    }
}