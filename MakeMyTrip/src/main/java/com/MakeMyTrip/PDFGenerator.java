package com.MakeMyTrip;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PDFGenerator {
    public static ByteArrayInputStream generateTicket(TravellersForm form, List<Map<String,Object>> trips){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            PdfWriter.getInstance(document,out);
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("Passengers' Information", font);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfPTable passengerTable = new PdfPTable(3);

            Stream.of("Name", "Identification Type", "Identification No")
                    .forEach(headerTitle->{
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        passengerTable.addCell(header);
                    });
            for(Traveller traveller: form.getTravellers()){
                PdfPCell nameCell = new PdfPCell(new Phrase(traveller.getName().toString()));
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                passengerTable.addCell(nameCell);

                PdfPCell idTypeCell = new PdfPCell(new Phrase(traveller.getIdentificationType().toString()));
                idTypeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idTypeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                passengerTable.addCell(idTypeCell);

                PdfPCell idNoCell = new PdfPCell(new Phrase(traveller.getIdentificationNo().toString()));
                idNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idNoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                passengerTable.addCell(idNoCell);
            }
            document.add(passengerTable);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Booked Trips", FontFactory.getFont(FontFactory.COURIER_BOLD)));
            document.add(Chunk.NEWLINE);
            PdfPTable tripTable = new PdfPTable(7);
            Stream.of("Trip Id", "From", "To", "Starts at", "Duration","Arrives at", "Total")
                    .forEach(headerTitle->{
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        tripTable.addCell(header);
                    });
            for(Map<String,Object> trip: trips){
                PdfPCell idCell = new PdfPCell(new Phrase(trip.get("TRIP_ID").toString()));
                addCellToTable(idCell,tripTable);


                PdfPCell startFromCell = new PdfPCell(new Phrase(trip.get("SADD").toString()+ " " + trip.get("CS")));
                addCellToTable(startFromCell,tripTable);

                PdfPCell destinationCell = new PdfPCell(new Phrase(trip.get("DADD").toString()+ " " + trip.get("CD")));
                addCellToTable(destinationCell,tripTable);

                PdfPCell startTimeCell = new PdfPCell(new Phrase(trip.get("START_TIME").toString()));
                addCellToTable(startTimeCell,tripTable);

                PdfPCell durationCell = new PdfPCell(new Phrase(trip.get("DURATION").toString()));
                addCellToTable(durationCell,tripTable);


                PdfPCell finishTimeCell = new PdfPCell(new Phrase(trip.get("FINISHTIME").toString()));
                addCellToTable(finishTimeCell,tripTable);


                PdfPCell totalCell = new PdfPCell(new Phrase(String.valueOf((Double.parseDouble(trip.get("BASE_PRICE").toString())*form.getTravellers().size()))));
                addCellToTable(totalCell,tripTable);

            }
            document.add(tripTable);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    private static void addCellToTable(PdfPCell cell, PdfPTable table){
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}
