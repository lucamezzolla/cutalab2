package com.cutalab.cutalab2.utils;

import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PDFFileGenerator {

    public File writeDisksToPdf(List<DiskDTO> disks) {
        String fileName = String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))+".pdf";
        File temp = new File(fileName);
        try {
            Document document = new Document(PageSize.A3.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(temp.getAbsolutePath()));
            document.open();
            LocalDateTime now = LocalDateTime.now();
            String nowStr = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy (HH:mm)"));
            document.add(new Paragraph("Data esportazione: "+nowStr));
            Chunk linebreak = new Chunk(new DottedLineSeparator());
            document.add(linebreak);
            //float bigCol = 96 / 9;
            PdfPTable table = new PdfPTable(new float[] { 23, 12, 12, 8, 12, 5, 12, 12 });
            table.setWidthPercentage(100f);
            for(int aw = 0; aw < 1; aw++) {
                table.addCell("TITOLO");
                table.addCell("AUTORE");
                table.addCell("ETICHETTA");
                table.addCell("RISTAMPA");
                table.addCell("VALORE PRESUNTO");
                table.addCell("ANNO");
                table.addCell("STATO DISCO");
                table.addCell("STATO COPERTINA");
            }
            for(int aw = 0; aw < disks.size(); aw++){
                table.addCell(disks.get(aw).getTitle());
                table.addCell(disks.get(aw).getAuthor());
                table.addCell(disks.get(aw).getLabel());
                table.addCell(disks.get(aw).getReprint());
                table.addCell(String.valueOf(disks.get(aw).getPresumedValue()));
                table.addCell(disks.get(aw).getYear());
                table.addCell(disks.get(aw).getDiskStatus().getName());
                table.addCell(disks.get(aw).getCoverStatus().getName());
            }
            document.add(table);
            document.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return temp;
    }



}