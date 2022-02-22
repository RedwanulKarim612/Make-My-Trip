package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@RestController

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomerDAO customerDAO;


    @RequestMapping(value = "/login", method = RequestMethod.POST )
    public @ResponseBody ModelAndView customerLogin(@ModelAttribute("request") AuthenticationRequest authenticationRequest, HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", "Bearer" );
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("");
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            System.out.println("bad credential");
            cookie.setMaxAge(0);
            return new ModelAndView("redirect:/login");
//            response.addCookie(cookie);
        }
        final UserDetails userDetails = customerUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        cookie.setValue("Bearer" + jwt);

        Collection<GrantedAuthority> authorityList = (Collection<GrantedAuthority>) userDetails.getAuthorities();
//        for (GrantedAuthority a : authorityList){
//            System.out.println(a.getAuthority());
//        }
//        System.out.println(jwtUtil.getUsernameFromToken(jwt));

        response.addCookie(cookie);
        if(authorityList.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return new ModelAndView("redirect:/admin/home");
        else if(authorityList.contains(new SimpleGrantedAuthority("ROLE_COMPANY")))
            return new ModelAndView("redirect:/company/home");
        else  return new ModelAndView("redirect:/user/home");


    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST )
    public @ResponseBody ModelAndView adminLogin(@ModelAttribute("request") AuthenticationRequest authenticationRequest, HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", "Bearer" );
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("");
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            System.out.println("bad credential");
            cookie.setMaxAge(0);
            return new ModelAndView("redirect:/admin/login");
//            response.addCookie(cookie);
        }
        final UserDetails userDetails = customerUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        cookie.setValue("Bearer" + jwt);

//        System.out.println(jwtUtil.getUsernameFromToken(jwt));

        response.addCookie(cookie);
        return new ModelAndView("redirect:/admin/home");
    }

    @RequestMapping(value = "company/login", method = RequestMethod.POST)
    public @ResponseBody ModelAndView companyLogin(@ModelAttribute("request") AuthenticationRequest authenticationRequest, HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", "Bearer" );
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("");
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            System.out.println("bad credential");
            cookie.setMaxAge(0);
            return new ModelAndView("redirect:/company/login");
//            response.addCookie(cookie);
        }
        final UserDetails userDetails = customerUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        cookie.setValue("Bearer" + jwt);

//        System.out.println(jwtUtil.getUsernameFromToken(jwt));

        response.addCookie(cookie);
        return new ModelAndView("redirect:/company/home");
    }

    public @ResponseBody void createAuthenticationToken(@ModelAttribute("request") AuthenticationRequest authenticationRequest, HttpServletResponse response) throws Exception{
        Cookie cookie = new Cookie("jwt", "Bearer" );

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("");
        cookie.setDomain("");
        try{authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e){
            System.out.println("bad credential");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            throw new Exception("incorrect username or password");
        }
        final UserDetails userDetails = customerUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        cookie.setValue("Bearer" + jwt);

//        System.out.println(jwtUtil.getUsernameFromToken(jwt));

//        System.out.println("d");
        response.addCookie(cookie);
//        return new ModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST )
    public @ResponseBody ModelAndView registerUser(@ModelAttribute("customer") Customer customer, HttpServletResponse response) throws Exception {
        try{
            customerDAO.registerCustomer(customer);
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/register");
        }
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(customer.getUserId());
        request.setPassword(customer.getPassword());
        createAuthenticationToken(request,response);
        return new ModelAndView("redirect:/user/home");
    }

//    @PostMapping(path = "/user/profile/edit", params = "action=save")
//    public @ResponseBody  ModelAndView customerProfileEdited(Customer customer, HttpServletResponse response){
//        System.out.println(customer.getUserId() + " " + SecurityContextHolder.getContext().getAuthentication().getName());
//        ModelAndView modelAndView = new ModelAndView("customer-profile");
//        customerDAO.editProfile(customer, SecurityContextHolder.getContext().getAuthentication().getName());
//        Cookie cookie = new Cookie("jwt", "Bearer" );
//
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        cookie.setPath("");
//        cookie.setDomain("");
//        final UserDetails userDetails = customerUserDetailsService
//                .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        final String jwt = jwtUtil.generateToken(userDetails);
//        cookie.setValue("Bearer" + jwt);
//        response.addCookie(cookie);
//
//        return modelAndView;
//    }
}
