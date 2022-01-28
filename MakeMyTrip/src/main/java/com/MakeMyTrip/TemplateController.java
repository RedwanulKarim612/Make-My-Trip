package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class TemplateController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;


    @GetMapping("admin/models/add")
    public String getAddModelView(Model model1){
        com.MakeMyTrip.Model model =  new com.MakeMyTrip.Model();
        model1.addAttribute(model);
        return "admin-models-add";
    }
    @GetMapping("admin/countries/add")
    public String getAddCountryView(Model model){
        Country country = new Country();
        model.addAttribute(country);
        return "admin-countries-add";
    }

    @GetMapping("admin/companies/add")
    public String getAddCompanyAddView(Model model){
        Company company = new Company();
        model.addAttribute(company);
        return "admin-companies-add";
    }

    @GetMapping("admin/transactions/add")
    public String getAddTransactionView(Model model){
        Transaction transaction = new Transaction();
        model.addAttribute(transaction);
        return "admin-transactions-add";
    }

    @GetMapping("admin/cities/add")
    public String getAddCityView(Model model){
        City city = new City();
        model.addAttribute(city);
        return "admin-cities-add";
    }


    @GetMapping("admin/vehicles/add")
    public String getAddVehicleView(Model model){
        Vehicle vehicle = new Vehicle();
        model.addAttribute(vehicle);
        return "admin-vehicles-add";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        model.addAttribute(authenticationRequest);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST )
    public @ResponseBody
    ModelAndView createAuthenticationToken(@ModelAttribute("request") AuthenticationRequest authenticationRequest, HttpServletResponse response) throws Exception{
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
//            System.out.println("bad credential");
//            throw new BadCredentialsException("incorrect username or password");
            response.addCookie(cookie);
            return new ModelAndView("redirect:/login");

        }
        final UserDetails userDetails = customerUserDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        cookie.setValue("Bearer" + jwt);
        

        System.out.println("d");
        response.addCookie(cookie);
        return new ModelAndView("redirect:/admin/countries");
    }

}
