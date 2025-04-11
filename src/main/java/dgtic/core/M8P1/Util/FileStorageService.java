package dgtic.core.M8P1.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        // Utiliza la carpeta dentro de resources/static
        this.fileStorageLocation = Paths.get("src/main/resources/static/imagenes").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio para guardar archivos.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String nombreLimpio = StringUtils.cleanPath(file.getOriginalFilename());
        String nuevoNombre = UUID.randomUUID() + "_" + nombreLimpio;

        try {
            if (nombreLimpio.contains("..")) {
                throw new RuntimeException("Nombre de archivo inválido: " + nombreLimpio);
            }

            // Codificar el nombre del archivo para que sea seguro en la URL
            String nombreArchivoCodificado = URLEncoder.encode(nuevoNombre, "UTF-8");

            Path targetLocation = this.fileStorageLocation.resolve(nombreArchivoCodificado);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return nombreArchivoCodificado;

        } catch (IOException ex) {
            throw new RuntimeException("No se pudo guardar el archivo " + nuevoNombre, ex);
        }
    }

    public Path getFilePath(String filename) {
        return this.fileStorageLocation.resolve(filename).normalize();
    }
}

