package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryDAO countryDAO;

    @RequestMapping("admin/countries")
    @PostMapping(path = "admin/countries" , params = "action=reset")
    public ModelAndView getAllCountries() {
        ModelAndView modelAndView = new ModelAndView("admin-countries");
        modelAndView.addObject("countries" , countryDAO.getAllCountries());
        return modelAndView;
    }

    @GetMapping(path = "/admin/countries/{countryId}")
    public ModelAndView getCountryById(@PathVariable String countryId){
        ModelAndView modelAndView = new ModelAndView("admin-country-single");
        try{
            modelAndView.addObject("country",countryDAO.getCountryById(countryId));
            return modelAndView;
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @PostMapping(path = "/admin/countries/add", params = "action=add")
    public ModelAndView addCountry(@ModelAttribute ("country") Country country){
        try{
            countryDAO.addCountry(country);
            return new ModelAndView("redirect:/admin/countries/" + country.getCountryId());
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/countries");
            //duplicate primary key
        }
    }

    @PostMapping(path = "/admin/countries/add", params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/countries");
    }


    @PostMapping(path = "admin/countries", params = "action=search")
    public ModelAndView searchCountry(@RequestParam String countryId){
        ModelAndView modelAndView = new ModelAndView("admin-countries");
        try{
            modelAndView.addObject("countries", countryDAO.getCountryById(countryId));
        }
        catch (EmptyResultDataAccessException e){

        }
        return modelAndView;
    }

    @PostMapping(path = "/admin/countries/{countryId}", params = "action=delete")
    public ModelAndView deleteCountry(@PathVariable String countryId){
        try{
            countryDAO.deleteCountry(countryId);

        }
        catch (DataIntegrityViolationException e){
            //cannot delete country, linked with city
        }
        return new ModelAndView("redirect:admin/countries");
    }

    @RequestMapping("/admin/countries/{countryId}/edit")
    public ModelAndView getEditPage(@PathVariable String countryId){
        ModelAndView modelAndView = new ModelAndView("admin-country-edit");
        modelAndView.addObject("country",countryDAO.getCountryById(countryId));
        return modelAndView;
    }


    @PostMapping(path = "/admin/countries/{countryId}/edit", params = "action=save")
    public ModelAndView editCountry(@PathVariable String countryId,Country country){
        try{
            countryDAO.editCountry(countryId, country);
            ModelAndView modelAndView = new ModelAndView("admin-country-edit");
            return getCountryById(countryId);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
            return null;
        }
    }
    @PostMapping(path = "/admin/countries/{countryId}/edit", params = "action=cancel")
    public ModelAndView cancelEdit(@PathVariable String countryId){
        return new ModelAndView("redirect:admin/countries/" + countryId);
    }



}
