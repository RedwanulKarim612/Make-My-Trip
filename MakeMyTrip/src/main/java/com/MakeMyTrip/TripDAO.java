package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class TripDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public List<Map<String,Object>> getAllTrips(){
        String sql =
                "SELECT t.trip_id, t.BASE_PRICE, t.UPGRADE_PCT, t.START_TIME,t.DURATION, c1.CITY_NAME as STARTFROM, c2.CITY_NAME as DES\n" +
                "from TRIP t JOIN LOCATION l1 ON(t.START_FROM = l1.LOCATION_ID)\n" +
                "JOIN LOCATION l2 on(t.DESTINATION = l2.LOCATION_ID)\n" +
                "JOIN CITY c1 ON(l1.CITY_ID = c1.CITY_id)\n" +
                "JOIN CITY c2 on(l2.CITY_ID = c2.CITY_ID) ";
        return getJdbcTemplate().queryForList(sql);
    }

    public Trip getTripInfoById(String tripId) throws EmptyResultDataAccessException {
        String sql = "SELECT * FROM TRIP WHERE trip_id = ?";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(Trip.class), tripId);
    }

    public void addTrip(Trip trip) {
        String sql = "INSERT INTO trip (BASE_PRICE, UPGRADE_PCT, START_TIME,DURATION, VEHICLE_ID, START_FROM, DESTINATION) " +
                "VALUES(?,?,?,?,?,?,?)";
        getJdbcTemplate().update(sql,trip.getBasePrice(),
                                    trip.getUpgradePct(),
                                    trip.getDate(),
                                    trip.getDuration(),
                                    trip.getVehicleId(),
                                    trip.getStartFrom(),
                                    trip.getDestination()) ;
    }
}
