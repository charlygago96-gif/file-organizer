# 📁 File Organizer

Herramienta de automatización en Java que monitorea una carpeta en tiempo real
y organiza automáticamente los archivos según su tipo.

## ¿Qué hace?

Cuando detecta un archivo nuevo en la carpeta configurada, lo mueve
automáticamente a una subcarpeta según su extensión:

| Carpeta | Extensiones |
|---|---|
| IMAGENES | jpg, jpeg, png, gif, svg, webp |
| VIDEOS | mp4, avi, mkv, mov |
| DOCUMENTOS | pdf, doc, docx, xls, xlsx, txt |
| MUSICA | mp3, wav, flac, ogg |
| CODIGO | java, py, js, html, css, sql |
| COMPRIMIDOS | zip, rar, 7z, tar, gz |
| OTROS | cualquier otra extensión |

Además genera un archivo `organizador.log` con el historial de todo lo movido.

## 🚀 Requisitos

- Java 21 o superior
- Maven

## ⚙️ Configuración

Edita el archivo `config.properties` en la raíz del proyecto:
```properties
carpeta.monitorear=C:/Users/TU_USUARIO/Downloads
```

## ▶️ Cómo ejecutar

### Desde IntelliJ
Abre `Main.java` y pulsa el triángulo verde.

### Desde terminal
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.fileorganizer.Main"
```

### Generar JAR ejecutable
```bash
mvn package
java -jar target/file-organizer-1.0-SNAPSHOT.jar
```

## 📂 Estructura del proyecto
```
file-organizer/
├── src/main/java/com/fileorganizer/
│   ├── Main.java            # Punto de entrada
│   ├── FolderWatcher.java   # Monitoreo en tiempo real
│   ├── FileClassifier.java  # Clasificación por extensión
│   ├── FileMover.java       # Movimiento de archivos
│   ├── FileType.java        # Tipos de archivo (enum)
│   ├── Logger.java          # Sistema de logs
│   └── ConfigLoader.java    # Carga de configuración
├── config.properties        # Configuración de la carpeta
└── pom.xml
```

## 🛠️ Tecnologías

- Java 25
- Maven
- `java.nio.file.WatchService` para monitoreo en tiempo real
```

---

### Paso 2: Añadir `config.properties` al `.gitignore`
El `config.properties` tiene una ruta específica de tu máquina, no debería subirse a GitHub. Abre el `.gitignore` y añade al final:
```
# Configuración local
config.properties
