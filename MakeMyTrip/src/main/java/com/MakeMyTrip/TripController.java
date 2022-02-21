package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class TripController {
    @Autowired
    TripDAO tripDAO;
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    VehicleDAO vehicleDAO;
    @Autowired
    TicketDAO ticketDAO;
    @RequestMapping("admin/trips")
    @PostMapping(path = "/admin/trips" , params = "action=reset")
    public ModelAndView getAllTrips(){
        ModelAndView modelAndView = new ModelAndView("admin-trips");
        try {
            modelAndView.addObject("trips", tripDAO.getAllTrips());
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @RequestMapping("company/trips")
    @PostMapping(path = "/company/trips" , params = "action=reset")
    public ModelAndView getAllTripsByCompany(){
        ModelAndView modelAndView = new ModelAndView("company-trips");
        try {
            modelAndView.addObject("trips", tripDAO.getAllTripsByCompany(SecurityContextHolder.getContext().getAuthentication().getName()));
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @GetMapping(path = "admin/trips/{tripId}")
    public ModelAndView getTripById(@PathVariable String tripId){
        ModelAndView modelAndView = new ModelAndView("admin-trip-single");
        try{
            modelAndView.addObject("trip",tripDAO.getTripInfoById(tripId));
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @PostMapping(path = "admin/trips", params = "action=search")
    public ModelAndView searchVehicle(@RequestParam String cityName){
        System.out.println(cityName);
        ModelAndView modelAndView = new ModelAndView("admin-trips");
        try{
            modelAndView.addObject("trips", tripDAO.searchTrips(cityName));
        }
        catch (EmptyResultDataAccessException e){

        }
        return modelAndView;
    }

    @PostMapping(path = "company/trips", params = "action=search")
    public ModelAndView searchVehicleByCompany(@RequestParam String cityName){
        ModelAndView modelAndView = new ModelAndView("company-trips");
        try{
            modelAndView.addObject("trips", tripDAO.searchTrips(cityName,SecurityContextHolder.getContext().getAuthentication().getName()));
        }
        catch (EmptyResultDataAccessException e){

        }
        return modelAndView;
    }

    @GetMapping(path = "company/trips/{tripId}")
    public ModelAndView getTripByIdAndCompany(@PathVariable String tripId){
        ModelAndView modelAndView = new ModelAndView("company-trip-single");
        try{
            modelAndView.addObject("trip", tripDAO.getTripByIdAndCompany(tripId, SecurityContextHolder.getContext().getAuthentication().getName()));
            return modelAndView;

        }
        catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @GetMapping({"admin/trips/add","/company/trips/add"})
    public ModelAndView getAddTripView(){
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) modelAndView.setViewName("admin-trips-add");
        else modelAndView.setViewName("company-trips-add");
        modelAndView.addObject("request", new TripAddRequest());
        var locations = locationDAO.getAllLocations();
        modelAndView.addObject("startFrom" , locations);
        modelAndView.addObject("destination" , locations);
        modelAndView.addObject("vehicles",vehicleDAO.getAllVehiclesByCompany());
        return modelAndView;
    }

    @PostMapping(path = {"admin/trips/add","/company/trips/add"})
    public ModelAndView addTrip(TripAddRequest request){
//        System.out.println(trip);

        try{
            tripDAO.addTripForDays(request);
        }
        catch (DataIntegrityViolationException e){

        }

        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) return new ModelAndView("redirect:/admin/trips");
        else return new ModelAndView("redirect:/company/trips");
    }

    @GetMapping(path = "/user/bookings")
    public ModelAndView getBookedTrips(){
        ModelAndView modelAndView = new ModelAndView("customer-bookings");
        modelAndView.addObject("trips", tripDAO.getBookedTripsByCustomer());
        return modelAndView;
    }

    @GetMapping(path = "/user/bookings/{tripId}")
    public ModelAndView getBookedTickets(@PathVariable String tripId){
        ModelAndView modelAndView = new ModelAndView("customer-booking-tickets");
        modelAndView.addObject("trip", tripDAO.getTripInfoById(tripId));
        modelAndView.addObject("tickets", ticketDAO.getTicketBookedByUser(tripId));
        return modelAndView;
    }
}
