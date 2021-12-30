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
import java.util.List;

@Repository
public class CountryDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public List<Country> getAllCountries() throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM COUNTRY";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Country.class));

    }

    public Country getCountryById(String countryId) throws EmptyResultDataAccessException{
        String sql = "SELECT * FROM country WHERE country_id = ?";
        return getJdbcTemplate().queryForObject(sql,BeanPropertyRowMapper.newInstance(Country.class),countryId);
    }

    public void addCountry(Country country) throws DuplicateKeyException {
        String sql = "INSERT INTO country VALUES(?,?)";
        getJdbcTemplate().update(sql,country.getCountryId(),country.getCountryName());
    }

    public void deleteCountry(String countryId) throws DataIntegrityViolationException {
        String sql = "DELETE FROM country WHERE country_id = ?";
        getJdbcTemplate().update(sql,countryId);
    }

    public void editCountry(String countryId,Country country) throws DataIntegrityViolationException{
        if(!countryId.equals(country.getCountryId())) throw new DataIntegrityViolationException("cannot edit primary key");
        String sql = "UPDATE country SET " +
                "country_id = ?," +
                "country_name = ?" +
                "WHERE country_id = ?";
        getJdbcTemplate().update(sql,country.getCountryId(),country.getCountryName(),countryId);
    }

}
