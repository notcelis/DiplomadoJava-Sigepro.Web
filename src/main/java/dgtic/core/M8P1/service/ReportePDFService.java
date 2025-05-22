package dgtic.core.M8P1.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dgtic.core.M8P1.model.Tarea;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePDFService {

    public ByteArrayInputStream generarReporteTareas(List<Tarea> tareas, String nombreProyecto) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Reporte de tareas - Proyecto: " + nombreProyecto, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" ")); // espacio

            // Tabla
            PdfPTable tabla = new PdfPTable(4); // columnas
            tabla.setWidthPercentage(100);
            tabla.addCell("ID");
            tabla.addCell("Título");
            tabla.addCell("Estado");
            tabla.addCell("Responsable");

            for (Tarea tarea : tareas) {
                tabla.addCell(String.valueOf(tarea.getId()));
                tabla.addCell(tarea.getNombre());
                tabla.addCell(tarea.getEstado().toString());
                tabla.addCell(tarea.getUsuario() != null ? tarea.getUsuario().getNombre() : "Sin asignar");
            }

            document.add(tabla);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error generando PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

