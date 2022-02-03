package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TripController {
    @Autowired
    TripDAO tripDAO;

    @RequestMapping("admin/trips")
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

    @GetMapping("admin/trips/add")
    public ModelAndView getAddCityView(){
        ModelAndView modelAndView = new ModelAndView("admin-trips-add");
        modelAndView.addObject("trip", new Trip());
        return modelAndView;
    }

    @PostMapping(path = "admin/trips/add")
    public ModelAndView addTrip(Trip trip){
        System.out.println(trip);
        ModelAndView modelAndView = new ModelAndView("admin-trips-add");
        try{
            tripDAO.addTrip(trip);
        }
        catch (DataIntegrityViolationException e){

        }
        return new ModelAndView("redirect:/admin/trips");
    }
}
