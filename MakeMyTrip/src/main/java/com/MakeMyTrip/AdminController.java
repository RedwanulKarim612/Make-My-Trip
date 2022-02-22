package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController

public class AdminController {
    @Autowired
    AdminDAO adminDAO;
    @RequestMapping("/admin/home")
    public ModelAndView getAdminHome(){
        ModelAndView modelAndView = new ModelAndView("admin-home");
        modelAndView.addObject("admin", adminDAO.getAdminInfo());
        return modelAndView;
    }

    @PostMapping(path = "/admin/home" , params = "action=logout")
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


}
