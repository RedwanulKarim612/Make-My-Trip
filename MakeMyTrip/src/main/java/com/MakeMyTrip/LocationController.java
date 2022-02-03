package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LocationController {
    @Autowired
    LocationDAO locationDAO;
    @Autowired
    CityDAO cityDAO;
    @RequestMapping("/admin/locations")
    @PostMapping(path = "admin/locations" , params = "action=reset")
    public ModelAndView getAllLocations(){
        ModelAndView modelAndView = new ModelAndView("admin-locations");
        modelAndView.addObject("locations" , locationDAO.getAllLocations());
        return modelAndView;
    }

    @PostMapping(path = "admin/locations", params = "action=search")
    public ModelAndView searchLocationByCity(@RequestParam String cityNameLike){
        ModelAndView modelAndView = new ModelAndView("admin-locations");
        try{
            modelAndView.addObject("locations",  locationDAO.searchLocationByCityLike(cityNameLike));
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @GetMapping("admin/locations/add")
    public ModelAndView getAddLocationView(){
        ModelAndView modelAndView = new ModelAndView("admin-locations-add");
        modelAndView.addObject("location", new Location());
        modelAndView.addObject("cities",cityDAO.getAllCitiesWithCountry());
        return modelAndView;
    }

    @PostMapping("admin/locations/add")
    public ModelAndView addLocation(Location location){
        try {
            locationDAO.addLocation(location);
            return new ModelAndView("redirect:/admin/locations");
        }
        catch (DataIntegrityViolationException e){
            return new ModelAndView("redirect:/admin/locations");
        }
    }

    @PostMapping(value = "admin/locations/add", params = "action=cancel")
    public ModelAndView cancelAdd(){
        return new ModelAndView("redirect:/admin/locations");
    }

    @RequestMapping("/admin/locations/{locationId}/edit")
    public ModelAndView getEditPage(@PathVariable String locationId){
        ModelAndView modelAndView = new ModelAndView("admin-location-edit");
        modelAndView.addObject("location", locationDAO.getLocationById(locationId));
        modelAndView.addObject("cities", cityDAO.getAllCitiesWithCountry());
        return modelAndView;
    }

    @PostMapping(path = "/admin/locations/{locationId}/edit", params = "action=save")
    public ModelAndView editCity(@PathVariable String locationId,Location location){
        try{
            locationDAO.editLocation(locationId,location);
        }
        catch (DataIntegrityViolationException e){
            //cannot edit primary key
        }
        return new ModelAndView("redirect:/admin/locations/" + locationId);
    }

    @PostMapping(path = "/admin/locations/{locationId}/edit", params = "action=cancel")
    public ModelAndView cancelEdit(@PathVariable String locationId){
        return new ModelAndView("redirect:/admin/locations/" + locationId );
    }

    @GetMapping("admin/locations/{locationId}")
    public ModelAndView getLocationInfoById(@PathVariable String locationId){
        ModelAndView modelAndView = new ModelAndView("admin-location-single");
        try{
            modelAndView.addObject("location", locationDAO.getLocationInfoById(locationId).get(0));
        }
        catch (EmptyResultDataAccessException e){
            //
        }
        return modelAndView;
    }

    @PostMapping(value = "/admin/locations/{locationId}", params = "action=delete")
    public ModelAndView deleteLocation(@PathVariable String locationId){
        try{
            locationDAO.deleteLocation(locationId);
        }
        catch (DataIntegrityViolationException e){
            //
        }
        return new ModelAndView("redirect:/admin/locations");
    }

}
