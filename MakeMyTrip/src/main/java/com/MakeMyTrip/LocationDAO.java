package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;


@Repository
public class LocationDAO extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize() {
        setDataSource(dataSource);
    }

    public List<Map<String,Object>> getAllLocations(){
        String sql = "SELECT l.location_id, l.address, c.city_name\n" +
                        "FROM location l LEFT JOIN city c\n" +
                        " ON (l.city_id = c.city_id)";
        return getJdbcTemplate().queryForList(sql);
    }


    public void addLocation(Location location) {
        String sql = "INSERT INTO location (address,city_id) VALUES(?,?)";
        getJdbcTemplate().update(sql,location.getAddress(),location.getCityId());
    }

    public List<Map<String, Object>> getLocationInfoById(String locationId) {
        String sql = "SELECT l.location_id, l.address, c.city_name\n" +
                "FROM location l LEFT JOIN city c\n" +
                "ON(l.city_id = c.city_id)\n" +
                "WHERE l.location_id = ?";

        return getJdbcTemplate().queryForList(sql,locationId);
    }

    public Location getLocationById(String locationId){
        String sql = "SELECT * FROM location WHERE location_id = ?";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Location.class),locationId);
    }

    public void deleteLocation(String locationId) throws DataIntegrityViolationException {
        String sql = "DELETE FROM location WHERE location_id = ?";
        getJdbcTemplate().update(sql,locationId);
    }

    public void editLocation(String locationId, Location location) throws DataIntegrityViolationException {
        if(!locationId.equalsIgnoreCase(location.getLocationId())){
            throw new DataIntegrityViolationException("cannot edit locationId");
        }
        String sql = "UPDATE location SET " +
                "address = ?," +
                "city_id=  ?" +
                "WHERE location_id = ?";
        getJdbcTemplate().update(sql,location.getAddress(),location.getCityId(),locationId);
    }

    public List<Map<String,Object>> searchLocationByCityLike(String cityNameLike) {
        String sql = "SELECT l.location_id,l.address,c.city_name " +
                "FROM LOCATION l join CITY c " +
                "ON(l.CITY_ID = c.CITY_ID) " +
                "WHERE UPPER(C.CITY_NAME) LIKE ?";
        return getJdbcTemplate().queryForList(sql,cityNameLike.toUpperCase().concat("%"));
    }
}
