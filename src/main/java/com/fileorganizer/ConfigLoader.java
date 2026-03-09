package com.fileorganizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

// Carga la configuración desde un archivo .properties
public class ConfigLoader {

    private final Properties properties = new Properties();

    public ConfigLoader(String configFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
        }
    }

    // Devuelve la carpeta a monitorear
    public Path getFolderToWatch() {
        String path = properties.getProperty("carpeta.monitorear",
                System.getProperty("user.home") + "/Downloads");
        return Paths.get(path);
    }
}