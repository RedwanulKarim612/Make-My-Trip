package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ModelController {
    @Autowired
    ModelDAO modelDAO;

    @RequestMapping("admin/models")
    @PostMapping(path = "admin/models" , params = "action=reset")
    public ModelAndView getAllModels() {
        ModelAndView modelAndView = new ModelAndView("admin-models");
        modelAndView.addObject("models",modelDAO.getAllModels());
        return modelAndView;

    }

    @RequestMapping("admin/models/{modelId}")
    public ModelAndView getModelById(@PathVariable String modelId){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-model-single");

        try{
            Model model = modelDAO.getModelById(modelId);
            modelAndView.addObject("model",model);
        }
        catch (EmptyResultDataAccessException e){

            //no model found by id
        }
        return modelAndView;
    }




    @PostMapping(path = "/admin/models/add",params = "action=add")
    public ModelAndView addModel(@ModelAttribute ("model") Model model){
        System.out.println(model.getModelId());
        ModelAndView modelAndView = new ModelAndView("admin-models");
        try{
            modelDAO.addModel(model);

        }
        catch (DuplicateKeyException e){
        }
        catch (DataIntegrityViolationException e){
        }
        return modelAndView;
    }


    @PostMapping(path = "admin/models", params = "action=search")
    public ModelAndView searchModel(@RequestParam String modelId){

        ModelAndView modelAndView = new ModelAndView("admin-models");
        try{
            modelAndView.addObject("models",modelDAO.getModelById(modelId));
        }
        catch (EmptyResultDataAccessException e){
            //no model;
        }
        return modelAndView;
    }

    @DeleteMapping(path = "admin/models/{modelId}")
    public String deleteModel(@PathVariable String modelId){
        try{
            modelDAO.deleteModel(modelId);
            return "success";
        }
        catch (DataIntegrityViolationException e){
            return "Cannot delete model";
        }
    }

    @PutMapping(path = "admin/models/{modelId}")
    public String editModel(@PathVariable String modelId, @RequestBody Model model){
        try{
            modelDAO.editModel(modelId, model);
            return "success";
        }
        catch (DataIntegrityViolationException e){
            return "Cannot edit model id";
        }
    }
}
