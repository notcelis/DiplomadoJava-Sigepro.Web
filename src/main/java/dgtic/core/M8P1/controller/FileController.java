package dgtic.core.M8P1.controller;

import dgtic.core.M8P1.Util.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileController {

    @GetMapping("/archivos/{nombreArchivo:.+}")
    public ResponseEntity<Resource> servirArchivo(@PathVariable String nombreArchivo) {
        try {
            Path archivoPath = Paths.get("uploads").resolve(nombreArchivo).normalize();
            Resource recurso = new UrlResource(archivoPath.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                String contentType = Files.probeContentType(archivoPath);
                if (contentType == null) contentType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(recurso);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
