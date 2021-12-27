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
    public List<Model> getAllModels(){
        return modelDAO.getAllModels();
    }

    @RequestMapping("admin/models/{modelId}")
    public ModelAndView getModelById(@PathVariable String modelId){

        ModelAndView modelAndView = new ModelAndView("admin-model-single");
        try{
            Model model = modelDAO.getModelById(modelId);
            modelAndView.addObject("model",model);
        }
        catch (EmptyResultDataAccessException e){
            //no model found by id
        }
        return modelAndView;
    }

    @PostMapping(path = "admin/models")
    public String addModel(@RequestBody Model model){
        try{
            modelDAO.addModel(model);
            return "success";
        }
        catch (DuplicateKeyException e){
            return "duplicate model id";
        }
        catch (DataIntegrityViolationException e){
            return "invalid model type";
        }
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
