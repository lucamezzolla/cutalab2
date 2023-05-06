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
import java.util.stream.IntStream;

@Component
public class PDFFileGenerator {

    public File writeDisksToPdf(List<DiskDTO> disks) {
        String fileName = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) +".pdf";
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
            IntStream.range(0, 1).forEach(aw -> {
                table.addCell("TITOLO");
                table.addCell("AUTORE");
                table.addCell("ETICHETTA");
                table.addCell("RISTAMPA");
                table.addCell("VALORE PRESUNTO");
                table.addCell("ANNO");
                table.addCell("STATO DISCO");
                table.addCell("STATO COPERTINA");
            });
            for (DiskDTO disk : disks) {
                table.addCell(disk.getTitle());
                table.addCell(disk.getAuthor());
                table.addCell(disk.getLabel());
                table.addCell(disk.getReprint());
                table.addCell(String.valueOf(disk.getPresumedValue()));
                table.addCell(disk.getYear());
                table.addCell(disk.getDiskStatus().getName());
                table.addCell(disk.getCoverStatus().getName());
            }
            document.add(table);
            document.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return temp;
    }



}