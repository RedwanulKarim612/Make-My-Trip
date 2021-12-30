package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class CityDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize() {
        setDataSource(dataSource);
    }

    public List<City> getAllCities() throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM city";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(City.class));
    }

    public City getCityById(String cityId) throws EmptyResultDataAccessException{
        String sql = "SELECT * FROM city WHERE city_id = ?";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(City.class),cityId);
    }


    public void addCity(City city) throws DataIntegrityViolationException {
        String sql = "INSERT INTO city VALUES(?,?,?,?)";
        getJdbcTemplate().update(sql,city.getCityId(),city.getCityName(),city.getTimeZone(),city.getCountryId());
    }

    public void editCity(String cityId,City city) throws DataIntegrityViolationException{
        if(!cityId.equals(city.getCityId())) throw new DataIntegrityViolationException("cannot edit cityId");
        String sql = "UPDATE city " +
                "SET city_id = ?," +
                "city_name = ?," +
                "local_time = ?," +
                "country_id = ?";
        getJdbcTemplate().update(sql,city.getCityId(),city.getCityName(),city.getTimeZone(),city.getCountryId());
    }

    public void deleteCity(String cityId) throws DataIntegrityViolationException{
        String sql = "DELETE FROM city WHERE city_id = ?";
        getJdbcTemplate().update(sql,cityId);
    }


}
