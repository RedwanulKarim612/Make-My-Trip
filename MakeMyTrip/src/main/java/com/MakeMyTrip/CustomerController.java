package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    CityDAO cityDAO;
    @Autowired
    TripDAO tripDAO;

    @GetMapping("/user/profile")
    public ModelAndView getCustomerProfile(){
        ModelAndView modelAndView = new ModelAndView("customer-profile");
        modelAndView.addObject("customer", customerDAO.getCustomerInfo(SecurityContextHolder.getContext().getAuthentication().getName()));
        modelAndView.addObject("amount",customerDAO.getAmountInWallet(SecurityContextHolder.getContext().getAuthentication().getName()));
        return modelAndView;
    }

    @PostMapping(path = "/user/profile" , params = "action=verifyTID")
    public ModelAndView verifyTransaction(@RequestParam String transactionId){
        customerDAO.verifyTransaction(transactionId, SecurityContextHolder.getContext().getAuthentication().getName());
        return new ModelAndView("redirect:/user/profile");
    }


    @PostMapping(path = "/user/profile" , params = "action=logout")
    public ModelAndView handleLogout(HttpServletResponse response){
//        System.out.println("logout");
        Cookie cookie = new Cookie("jwt", null );
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("");
        cookie.setMaxAge(1);
        response.addCookie(cookie);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/user/home")
    public ModelAndView getHomePage(){
        ModelAndView modelAndView = new ModelAndView("home");
        var cities = cityDAO.getAllCitiesWithCountry();
        modelAndView.addObject("startFrom", cities);
        modelAndView.addObject("destination", cities);
        modelAndView.addObject("searchRequest", new SearchRequest());

        return modelAndView;
    }

    @RequestMapping(value = "/user/searchResults", method = RequestMethod.GET)
    public ModelAndView getSearchResultsView(ArrayList<Plan>plans){
        ModelAndView modelAndView = new ModelAndView("user-search-result");
        modelAndView.addObject("plans",plans);
        modelAndView.addObject("plan",new Plan());
        return modelAndView;
    }

    @PostMapping(path = "/user/searchResults", params = "action=search")
    public ModelAndView handleSearch(SearchRequest searchRequest, Model model){
        System.out.println(searchRequest);
        ArrayList<Plan>plans = (ArrayList<Plan>) tripDAO.searchPlan(searchRequest);
//        for(Plan plan: plans){
//            System.out.println(plan);
//        }
        //        modelAndView.addObject("plans", plans);
        return getSearchResultsView(plans);
    }

    @PostMapping(path = "/user/searchResults/book", params = "action=book")
    public ModelAndView handleBooking(Plan plan, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            for(Trip trip:plan.getTrips()){
                trip.setDate(trip.getDate());
            }
        }
//        System.out.println(plan);
//        ModelAndView modelAndView = new ModelAndView("user-book");
//        ArrayList<Traveller> travellers = new ArrayList<Traveller>(3);
//        for (Traveller traveller: travellers){
//            traveller = new Traveller();
//        }
//        modelAndView.addObject("travellers", travellers);
//        modelAndView.addObject("plan",plan);
        return getBookingView(plan);
    }

    @RequestMapping(value = "/user/searchResults/book", method = RequestMethod.GET)
    public ModelAndView getBookingView(Plan plan){
        ModelAndView modelAndView = new ModelAndView("user-book");
        modelAndView.addObject("trips", tripDAO.getTripsInPlan(plan));
        modelAndView.addObject("plan", plan);

        System.out.println(plan);
//        System.out.println("d" + plan.getRequestedTickets());
        modelAndView.addObject("form", new TravellersForm(plan.getRequestedTickets(),plan.getNumberOfTrips()));
        return modelAndView;
    }

    @PostMapping(path = "/user/searchResults/book", params = "action=confirm")
    public ResponseEntity<InputStreamResource> confirmBooking(TravellersForm form, BindingResult bindingResult, HttpServletResponse response){
//        for (Traveller traveller: form.getTravellers()){
//            System.out.println(traveller.getName());
//        }
//        System.out.println("size " + form.getTripIds().size());
//        for(String str: form.getTripIds()){
//            System.out.println(str);
//        }
        ByteArrayInputStream bis = PDFGenerator.generateTicket(form, tripDAO.getTripsInList(form.getTripIds()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ticket.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
