package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ModelDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public List<Model> getAllModels(){
        String sql = "SELECT * FROM MODEL";
        List<Model> models = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Model.class));
        return models;
    }

    public boolean addModel(Model model){
        if(!modelExists(model.getModelId())){
            String sql = "INSERT INTO MODEL VALUES(?,?,?,?)";
            getJdbcTemplate().update(sql,model.getModelId(),model.getType(),Integer.toString(model.getBusinessSeats()), Integer.toString(model.getEconomicSeats()));
            return true;
        }
        else return false;
    }

    public Model getModelById(String modelId){
        System.out.println(modelExists(modelId));
        if(!modelExists(modelId)) return null;
        String sql = "SELECT * FROM MODEL WHERE model_id = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Model.class), modelId);
    }

    public boolean modelExists(String modelId){
        String sql = "SELECT COUNT(*) FROM MODEL WHERE model_id = ?";
        return getJdbcTemplate().queryForObject(sql,Integer.class, modelId)==1;
    }

    public boolean deleteModel(String modelId) {
        if(!modelExists(modelId)) return false;
        String sql = "DELETE FROM model " +
                "WHERE model_id = ?";
        getJdbcTemplate().update(sql,modelId);
        return true;
    }

    public boolean editModel(String modelId, Model model) {
        if(modelExists(modelId) && modelId.equals(model.getModelId())){
            System.out.println(model);
            String sql = "UPDATE model " +
                    "SET model_id = ?, " +
                    "type = ?, " +
                    "business_seats = ?, " +
                    "economic_seats = ? " +
                    "WHERE model_id = ?";
            getJdbcTemplate().update(sql,model.getModelId(),model.getType(),Integer.toString(model.getBusinessSeats()), Integer.toString(model.getEconomicSeats()),modelId);
            return true;
        }
        else return false;
    }

//    public boolean
}
