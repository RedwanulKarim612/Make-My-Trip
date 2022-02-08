package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class CompanyController {
    @Autowired
    CompanyDAO companyDAO;

    @RequestMapping("/admin/companies")
    public ModelAndView getAllCompanies(){
        ModelAndView modelAndView = new ModelAndView("admin-companies");
        modelAndView.addObject("companies", companyDAO.getAllCompanies());
        return modelAndView;
    }

    @GetMapping(path = "admin/companies/{companyId}")
    public ModelAndView getCompanyById(@PathVariable String companyId){
        try{
            ModelAndView modelAndView = new ModelAndView("admin-company-single");
            modelAndView.addObject("company",companyDAO.getCompanyById(companyId));
            return modelAndView;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping(path = "admin/companies/add", params = "action=add")
    public ModelAndView addCompany(@ModelAttribute("company") Company company){
        try{
            companyDAO.addCompany(company);
            return new ModelAndView("redirect:/admin/companies/" + company.getCompanyId());
        }
        catch (DuplicateKeyException e){
            return new ModelAndView("redirect:/admin/companies");
        }
    }


    @PostMapping(path = "/admin/companies/add", params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/companies");
    }


    @PostMapping(path = "admin/companies", params = "action=search")
    public ModelAndView searchCompany(@RequestParam String companyId){
        ModelAndView modelAndView = new ModelAndView("admin-companies");
        try{
            modelAndView.addObject("companies", companyDAO.getCompanyById(companyId));
        }
        catch (EmptyResultDataAccessException e){

        }
        return modelAndView;
    }

    @PostMapping(path = "/admin/companies/{companyId}", params = "action=delete")
    public ModelAndView deleteCompany(@PathVariable String companyId){
        try{
            companyDAO.deleteCompany(companyId);
            return new ModelAndView("redirect:/admin/companies");
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/companies/" + companyId);
        }
    }

    @RequestMapping("/admin/companies/{companyId}/edit")
    public ModelAndView getEditPage(@PathVariable String companyId){
        ModelAndView modelAndView = new ModelAndView("admin-company-edit");
        modelAndView.addObject("company",companyDAO.getCompanyById(companyId));
        return modelAndView;
    }


    @PostMapping(path = "/admin/companies/{companyId}/edit", params = "action=save")
    public ModelAndView editCompany(@PathVariable String companyId,Company company){
        try{
            companyDAO.editCompany(companyId, company);
            return new ModelAndView("redirect:/admin/companies/" + companyId);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
            return new ModelAndView("redirect:/admin/companies/" + companyId);
        }
    }
    @PostMapping(path = "/admin/companies/{companyId}/edit", params = "action=cancel")
    public ModelAndView cancelEdit(@PathVariable String companyId){
        return new ModelAndView("redirect:/admin/companies/" + companyId);
    }


    @RequestMapping("/company/home")
    public ModelAndView getAdminHome(){
        ModelAndView modelAndView = new ModelAndView("company-home");
        modelAndView.addObject("company", companyDAO.getCompanyHome(SecurityContextHolder.getContext().getAuthentication().getName()));
        return modelAndView;
    }

    @PostMapping(path = "/company/home" , params = "action=logout")
    public ModelAndView handleLogout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt", null );
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setDomain("");
        cookie.setMaxAge(1);
        response.addCookie(cookie);
        return new ModelAndView("redirect:/company/login");
    }


}
