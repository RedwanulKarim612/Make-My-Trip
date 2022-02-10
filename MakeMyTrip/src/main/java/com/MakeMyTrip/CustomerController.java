package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CustomerController {
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    CityDAO cityDAO;

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

    @PostMapping(path = "/user/home", params = "action=search")
    public ModelAndView handleSearch(SearchRequest searchRequest){
        System.out.println(searchRequest);
        return new ModelAndView("redirect:/user/home");
    }
}
