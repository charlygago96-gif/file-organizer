package com.fileorganizer;

import java.util.HashMap;
import java.util.Map;

// Se encarga de clasificar un archivo según su extensión
public class FileClassifier {

    private static final Map<String, FileType> EXTENSION_MAP = new HashMap<>();

    static {
        // Imágenes
        EXTENSION_MAP.put("jpg",  FileType.IMAGENES);
        EXTENSION_MAP.put("jpeg", FileType.IMAGENES);
        EXTENSION_MAP.put("png",  FileType.IMAGENES);
        EXTENSION_MAP.put("gif",  FileType.IMAGENES);
        EXTENSION_MAP.put("svg",  FileType.IMAGENES);
        EXTENSION_MAP.put("webp", FileType.IMAGENES);

        // Vídeos
        EXTENSION_MAP.put("mp4",  FileType.VIDEOS);
        EXTENSION_MAP.put("avi",  FileType.VIDEOS);
        EXTENSION_MAP.put("mkv",  FileType.VIDEOS);
        EXTENSION_MAP.put("mov",  FileType.VIDEOS);

        // Documentos
        EXTENSION_MAP.put("pdf",  FileType.DOCUMENTOS);
        EXTENSION_MAP.put("doc",  FileType.DOCUMENTOS);
        EXTENSION_MAP.put("docx", FileType.DOCUMENTOS);
        EXTENSION_MAP.put("xls",  FileType.DOCUMENTOS);
        EXTENSION_MAP.put("xlsx", FileType.DOCUMENTOS);
        EXTENSION_MAP.put("txt",  FileType.DOCUMENTOS);

        // Música
        EXTENSION_MAP.put("mp3",  FileType.MUSICA);
        EXTENSION_MAP.put("wav",  FileType.MUSICA);
        EXTENSION_MAP.put("flac", FileType.MUSICA);
        EXTENSION_MAP.put("ogg",  FileType.MUSICA);

        // Código
        EXTENSION_MAP.put("java", FileType.CODIGO);
        EXTENSION_MAP.put("py",   FileType.CODIGO);
        EXTENSION_MAP.put("js",   FileType.CODIGO);
        EXTENSION_MAP.put("html", FileType.CODIGO);
        EXTENSION_MAP.put("css",  FileType.CODIGO);
        EXTENSION_MAP.put("sql",  FileType.CODIGO);

        // Comprimidos
        EXTENSION_MAP.put("zip",  FileType.COMPRIMIDOS);
        EXTENSION_MAP.put("rar",  FileType.COMPRIMIDOS);
        EXTENSION_MAP.put("7z",   FileType.COMPRIMIDOS);
        EXTENSION_MAP.put("tar",  FileType.COMPRIMIDOS);
        EXTENSION_MAP.put("gz",   FileType.COMPRIMIDOS);
    }

    // Devuelve el tipo de archivo según su extensión
    public FileType classify(String fileName) {
        String extension = getExtension(fileName);
        return EXTENSION_MAP.getOrDefault(extension, FileType.OTROS);
    }

    // Extrae la extensión de un nombre de archivo
    private String getExtension(String fileName) {
        int lastDot = fileName.lastIndexOf(".");
        if (lastDot == -1 || lastDot == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(lastDot + 1).toLowerCase();
    }
}