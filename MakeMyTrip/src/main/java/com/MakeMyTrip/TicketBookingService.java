package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class TicketBookingService {
    @Autowired
    TripDAO tripDAO;
    @Autowired
    TicketDAO ticketDAO;
    @Autowired
    PDFGenerator pdfGenerator;
    public ByteArrayOutputStream bookTickets(TravellersForm form){
        List<String> travellerIds = tripDAO.bookTickets(form);
        if(travellerIds == null) return null;

        return pdfGenerator.generateTicket(travellerIds,tripDAO.getTripsInList(form.getTripIds()));
    }
}
