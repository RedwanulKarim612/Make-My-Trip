package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;

@Repository
public class ModelDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public List<Model> getAllModels() throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM MODEL";
        List<Model> models = getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Model.class));
        return models;
    }

    public void addModel(Model model) throws DuplicateKeyException,DataIntegrityViolationException {
        String sql = "INSERT INTO MODEL VALUES(?,?,?,?)";
        getJdbcTemplate().update(sql,model.getModelId(),model.getType(),Integer.toString(model.getBusinessSeats()), Integer.toString(model.getEconomicSeats()));
    }

    public Model getModelById(String modelId) throws EmptyResultDataAccessException{
        String sql = "SELECT * FROM MODEL WHERE model_id = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Model.class), modelId);
    }

    public boolean modelExists(String modelId){
        String sql = "SELECT COUNT(*) FROM MODEL WHERE model_id = ?";
        return getJdbcTemplate().queryForObject(sql,Integer.class, modelId)==1;
    }

    public void deleteModel(String modelId) throws DataIntegrityViolationException {
        String sql = "DELETE FROM model " +
                "WHERE model_id = ?";
        getJdbcTemplate().update(sql,modelId);
    }

    public void editModel(String modelId, Model model) throws DataIntegrityViolationException {
        if(!modelId.equals(model.getModelId())) throw new DataIntegrityViolationException("cannot edit primary key");
        String sql = "UPDATE model " +
                "SET model_id = ?, " +
                "type = ?, " +
                "business_seats = ?, " +
                "economic_seats = ? " +
                "WHERE model_id = ?";
        getJdbcTemplate().update(sql,model.getModelId(),model.getType(),Integer.toString(model.getBusinessSeats()), Integer.toString(model.getEconomicSeats()),modelId);
    }

//    public boolean
}
