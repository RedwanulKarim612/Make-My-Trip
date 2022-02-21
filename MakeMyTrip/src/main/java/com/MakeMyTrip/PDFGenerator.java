package com.MakeMyTrip;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Component
public class PDFGenerator {
    @Autowired
    TicketDAO ticketDAO;
    public ByteArrayOutputStream generateTicket(List<String> travellerIds, List<Map<String,Object>> trips){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (String id : travellerIds){
            System.out.println("d " + id);
        }
        if(ticketDAO==null) System.out.println("NULLL");
        try {
            PdfWriter.getInstance(document,out);
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);

            for (Map<String,Object> trip : trips){

                List<Map<String,Object>> travellers = ticketDAO.getTicketsForBooking(travellerIds,trip.get("TRIP_ID").toString());

                Paragraph paragraph = new Paragraph("Trip Information", FontFactory.getFont(FontFactory.TIMES_BOLD,14));
                document.add(paragraph);
                document.add(Chunk.NEWLINE);

                Chunk tripIdChunk = new Chunk("Trip Id: ", FontFactory.getFont(FontFactory.TIMES));
                tripIdChunk.append(String.valueOf(new Chunk(trip.get("TRIP_ID").toString(), FontFactory.getFont(FontFactory.TIMES))));
                document.add(new Paragraph(tripIdChunk));

                Chunk companyNameChunk = new Chunk("Company Name: ", FontFactory.getFont(FontFactory.TIMES));
                companyNameChunk.append(String.valueOf(new Chunk(trip.get("COMPANY_NAME").toString(), FontFactory.getFont(FontFactory.TIMES))));
                document.add(new Paragraph(companyNameChunk));

                Chunk vehicleChunk = new Chunk("Vehicle: ", FontFactory.getFont(FontFactory.TIMES));
                vehicleChunk.append(String.valueOf(new Chunk(trip.get("VEHICLE_ID").toString(), FontFactory.getFont(FontFactory.TIMES))));
                document.add(new Paragraph(vehicleChunk));





                Chunk startsFromChunk = new Chunk("Starts From: ", FontFactory.getFont(FontFactory.TIMES));
                startsFromChunk.append((trip.get("SADD").toString()+ " " + trip.get("CS")).toString());
                document.add(startsFromChunk);
                document.add(Chunk.NEWLINE);

                Chunk destinationChunk = new Chunk("Destination: ", FontFactory.getFont(FontFactory.TIMES));
                destinationChunk.append((trip.get("DADD").toString()+ " " + trip.get("CD")).toString());
                document.add(destinationChunk);
                document.add(Chunk.NEWLINE);


                Chunk startTimeChunk = new Chunk("Starts At: ", FontFactory.getFont(FontFactory.TIMES));
                startTimeChunk.append(trip.get("START_TIME").toString());
                document.add(startTimeChunk);
                document.add(Chunk.NEWLINE);


                Chunk durationChunk = new Chunk("Duration: ", FontFactory.getFont(FontFactory.TIMES));
                durationChunk.append(trip.get("DURATION").toString());
                document.add(durationChunk);
                document.add(Chunk.NEWLINE);


                Chunk arrivesAtChunk = new Chunk("Arrives At: ", FontFactory.getFont(FontFactory.TIMES));
                arrivesAtChunk.append(trip.get("FINISHTIME").toString());
                document.add(arrivesAtChunk);
                document.add(Chunk.NEWLINE);

                document.add(Chunk.NEWLINE);

                paragraph = new Paragraph("Passenger Information", FontFactory.getFont(FontFactory.TIMES_BOLD,14));
                document.add(paragraph);
                document.add(Chunk.NEWLINE);


                PdfPTable passengerTable = new PdfPTable(5);

//
                Stream.of("Seat_no", "Class" ,"Name", "Identification Type", "Identification No")
                     .forEach(headerTitle->{
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        passengerTable.addCell(header);
                    });

                for(Map<String,Object> traveller: travellers){

                    PdfPCell seat = new PdfPCell(new Phrase(traveller.get("SEAT_NO").toString()));
                    seat.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    seat.setHorizontalAlignment(Element.ALIGN_CENTER);
                    passengerTable.addCell(seat);

                    PdfPCell classCell = new PdfPCell(new Phrase(traveller.get("TYPE").toString()));
                    classCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    classCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    passengerTable.addCell(classCell);


                PdfPCell nameCell = new PdfPCell(new Phrase(traveller.get("NAME").toString()));
                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                passengerTable.addCell(nameCell);

                PdfPCell idTypeCell = new PdfPCell(new Phrase(traveller.get("IDENTYFICATION_TYPE").toString()));
                idTypeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idTypeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                passengerTable.addCell(idTypeCell);

                PdfPCell idNoCell = new PdfPCell(new Phrase(traveller.get("IDENTIFICATION_NO").toString()));
                idNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idNoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                passengerTable.addCell(idNoCell);
            }
            document.add(passengerTable);
//

                document.add(Chunk.NEXTPAGE);
            }


//            Paragraph paragraph = new Paragraph("Passengers' Information", font);
//            document.add(paragraph);
//            document.add(Chunk.NEWLINE);
//            PdfPTable passengerTable = new PdfPTable(3);
//
//            Stream.of("Name", "Identification Type", "Identification No")
//                    .forEach(headerTitle->{
//                        PdfPCell header = new PdfPCell();
//                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        header.setBorderWidth(2);
//                        header.setPhrase(new Phrase(headerTitle, headFont));
//                        passengerTable.addCell(header);
//                    });
//            for(Traveller traveller: form.getTravellers()){
//                PdfPCell nameCell = new PdfPCell(new Phrase(traveller.getName().toString()));
//                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                passengerTable.addCell(nameCell);
//
//                PdfPCell idTypeCell = new PdfPCell(new Phrase(traveller.getIdentificationType().toString()));
//                idTypeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                idTypeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                passengerTable.addCell(idTypeCell);
//
//                PdfPCell idNoCell = new PdfPCell(new Phrase(traveller.getIdentificationNo().toString()));
//                idNoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                idNoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                passengerTable.addCell(idNoCell);
//            }
//            document.add(passengerTable);
//
//            document.add(Chunk.NEWLINE);
//            document.add(new Paragraph("Booked Trips", FontFactory.getFont(FontFactory.COURIER_BOLD)));
//            document.add(Chunk.NEWLINE);
//            PdfPTable tripTable = new PdfPTable(7);
//            Stream.of("Trip Id", "From", "To", "Starts at", "Duration","Arrives at", "Total")
//                    .forEach(headerTitle->{
//                        PdfPCell header = new PdfPCell();
//                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
//                        header.setBorderWidth(2);
//                        header.setPhrase(new Phrase(headerTitle, headFont));
//                        tripTable.addCell(header);
//                    });
//            for(Map<String,Object> trip: trips){
//                PdfPCell idCell = new PdfPCell(new Phrase(trip.get("TRIP_ID").toString()));
//                addCellToTable(idCell,tripTable);
//
//
//                PdfPCell startFromCell = new PdfPCell(new Phrase(trip.get("SADD").toString()+ " " + trip.get("CS")));
//                addCellToTable(startFromCell,tripTable);
//
//                PdfPCell destinationCell = new PdfPCell(new Phrase(trip.get("DADD").toString()+ " " + trip.get("CD")));
//                addCellToTable(destinationCell,tripTable);
//
//                PdfPCell startTimeCell = new PdfPCell(new Phrase(trip.get("START_TIME").toString()));
//                addCellToTable(startTimeCell,tripTable);
//
//                PdfPCell durationCell = new PdfPCell(new Phrase(trip.get("DURATION").toString()));
//                addCellToTable(durationCell,tripTable);
//
//
//                PdfPCell finishTimeCell = new PdfPCell(new Phrase(trip.get("FINISHTIME").toString()));
//                addCellToTable(finishTimeCell,tripTable);
//
//
//                PdfPCell totalCell = new PdfPCell(new Phrase(String.valueOf((Double.parseDouble(trip.get("BASE_PRICE").toString())*form.getTravellers().size()))));
//                addCellToTable(totalCell,tripTable);
//
//            }
//            document.add(tripTable);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return out;
    }
    private static void addCellToTable(PdfPCell cell, PdfPTable table){
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}
