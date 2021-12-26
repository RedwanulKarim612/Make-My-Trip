package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Model getModelById(@PathVariable String modelId){
        return modelDAO.getModelById(modelId);
    }

    @PostMapping(path = "admin/models")
    public boolean addModel(@RequestBody Model model){
        return modelDAO.addModel(model);
    }

    @DeleteMapping(path = "admin/models/{modelId}")
    public boolean deleteModel(@PathVariable String modelId){
        return modelDAO.deleteModel(modelId);
    }

    @PutMapping(path = "admin/models/{modelId}")
    public boolean editModel(@PathVariable String modelId, @RequestBody Model model){
        return modelDAO.editModel(modelId,model);
    }
}
