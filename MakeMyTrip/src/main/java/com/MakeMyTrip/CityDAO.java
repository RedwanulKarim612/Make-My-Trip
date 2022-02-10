package com.MakeMyTrip;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> getAllCitiesWithCountry() throws EmptyResultDataAccessException{
        String sql = "SELECT CT.CITY_ID , CT.CITY_NAME, CN.COUNTRY_ID, CN.COUNTRY_NAME, CT.TIMEZONE\n" +
                "FROM CITY CT \n" +
                "JOIN COUNTRY CN\n" +
                "ON(CT.COUNTRY_ID = CN.COUNTRY_ID) " +
                "ORDER BY CT.CITY_NAME";

        return getJdbcTemplate().queryForList(sql);
    }

    public List<Map<String, Object>> getCityInfoById(String cityId) throws EmptyResultDataAccessException{
        String sql = "SELECT CT.CITY_ID , CT.CITY_NAME, CN.COUNTRY_ID, CN.COUNTRY_NAME, CT.TIMEZONE\n" +
                "FROM CITY CT \n" +
                "JOIN COUNTRY CN\n" +
                "ON(CT.COUNTRY_ID = CN.COUNTRY_ID) " +
                "WHERE CT.CITY_ID = ?";
        Map<String, Object> mp =  getJdbcTemplate().queryForMap(sql,cityId);
        List<Map<String, Object>> l =new ArrayList<>();
        l.add(mp);
        return l;
    }
    public List<Map<String, Object>> getCityInfoLikeName(String cityName) throws EmptyResultDataAccessException{
        String sql = "SELECT CT.CITY_ID , CT.CITY_NAME, CN.COUNTRY_ID, CN.COUNTRY_NAME, CT.TIMEZONE\n" +
                "FROM CITY CT \n" +
                "JOIN COUNTRY CN\n" +
                "ON(CT.COUNTRY_ID = CN.COUNTRY_ID) " +
                "WHERE UPPER(CT.CITY_NAME) LIKE ?";
        return getJdbcTemplate().queryForList(sql, cityName.toUpperCase().concat("%"));

    }


    public void addCity(City city) throws DataIntegrityViolationException {
        String sql = "INSERT INTO city(city_name,timezone,country_id) VALUES(?,?,?)";
        getJdbcTemplate().update(sql,city.getCityName(),city.getTimezone(),city.getCountryId());
    }

    public void editCity(String cityId,City city) throws DataIntegrityViolationException{
        if(!cityId.equals(city.getCityId())) {
            throw new DataIntegrityViolationException("cannot edit cityId");
        }
        String sql = "UPDATE city SET " +
                "city_id = ?," +
                "city_name = ?," +
                "TIMEZONE = ?," +
                "country_id = ? " +
                "WHERE city_id = ?";
        getJdbcTemplate().update(sql,city.getCityId(),city.getCityName(),city.getTimezone(),city.getCountryId(),cityId);
    }

    public void deleteCity(String cityId) throws DataIntegrityViolationException{
        String sql = "DELETE FROM city WHERE city_id = ?";
        getJdbcTemplate().update(sql,cityId);
    }


    public City getCityById(String cityId) {
        String sql = "SELECT * FROM CITY WHERE CITY_ID = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(City.class),cityId);
    }
}
