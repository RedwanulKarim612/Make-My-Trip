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



    @GetMapping("/login")
    public String getLoginPage(Model model){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        model.addAttribute(authenticationRequest);
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        Customer customer = new Customer();
        model.addAttribute(customer);
        return "register";
    }

    @GetMapping("/admin/login")
    public String getAdminLoginPage(Model model){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        model.addAttribute(authenticationRequest);
        return "admin-login";
    }

    @GetMapping("/company/login")
    public String getCompanyLogin(Model model){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        model.addAttribute(authenticationRequest);
        return "company-login";
    }

//    @GetMapping("/user/home")
//    public String getHomePage(){
//        return "home";
//    }
}
