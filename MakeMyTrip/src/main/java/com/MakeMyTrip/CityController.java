package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CityController {
    @Autowired
    CityDAO cityDAO;
    @Autowired
    CountryDAO countryDAO;

    @RequestMapping({"admin/cities","company/cities"})
    @PostMapping(path = {"admin/cities","company/cities"} , params = "action=reset")
    public ModelAndView getAllCities(){
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) modelAndView.setViewName("admin-cities");
        else modelAndView.setViewName("company-cities");
        try{
            modelAndView.addObject("cities", cityDAO.getAllCitiesWithCountry());
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }


    @GetMapping(path = "admin/cities/{cityId}")
    public ModelAndView getCityById(@PathVariable String cityId){
        ModelAndView modelAndView = new ModelAndView("admin-city-single");
        try{
            modelAndView.addObject("city", cityDAO.getCityInfoById(cityId).get(0));
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @PostMapping(path = "admin/cities/add")
    public ModelAndView addCity(City city){
        try{
            cityDAO.addCity(city);
            return new ModelAndView("redirect:/admin/cities" );
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/cities");
        }
    }

    @PostMapping(value = "admin/cities/add", params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/cities");
    }


    @PostMapping(path = {"admin/cities","company/cities"} ,params = "action=search")
    public ModelAndView searchCity(@RequestParam String cityId){
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) modelAndView.setViewName("admin-cities");
        else modelAndView.setViewName("company-cities");
        try{
            modelAndView.addObject("cities",cityDAO.getCityInfoById(cityId));
        }
        catch (EmptyResultDataAccessException e){
            //no city;
        }
        return modelAndView;
    }
    @PostMapping(value = "/admin/cities/{cityId}" , params = "action=delete")
    public ModelAndView deleteCity(@PathVariable String cityId){
        try{
            cityDAO.deleteCity(cityId);
        }
        catch (DataIntegrityViolationException e){
        }

        return new ModelAndView("redirect:/admin/cities");
    }

    @RequestMapping("/admin/cities/{cityId}/edit")
    public ModelAndView getEditPage(@PathVariable String cityId){
        ModelAndView modelAndView = new ModelAndView("admin-city-edit");
        modelAndView.addObject("city",cityDAO.getCityById(cityId));
        modelAndView.addObject("countries",countryDAO.getAllCountries());
        return modelAndView;
    }

    @PostMapping(path = "/admin/cities/{cityId}/edit", params = "action=save")
    public ModelAndView editCity(@PathVariable String cityId,City city){
        try{
            cityDAO.editCity(cityId, city);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
        }
        return new ModelAndView("redirect:/admin/cities/" + cityId);
    }
    @PostMapping(path = "/admin/cities/{cityId}/edit", params = "action=cancel")
    public ModelAndView cancelEdit(@PathVariable String cityId){
        return new ModelAndView("redirect:/admin/cities/" + cityId );
    }
    @GetMapping("admin/cities/add")
    public ModelAndView getAddCityView(){
        ModelAndView modelAndView = new ModelAndView("admin-cities-add");
        modelAndView.addObject("city", new City());
        modelAndView.addObject("countries",countryDAO.getAllCountries());
        return modelAndView;
    }
}
