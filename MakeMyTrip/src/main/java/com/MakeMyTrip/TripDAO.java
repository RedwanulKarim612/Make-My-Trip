package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.*;

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

    public Map<String,Object> getTripInfoById(String tripId) throws EmptyResultDataAccessException {
            String sql = "SELECT t.TRIP_ID, l1.LOCATION_ID as SID, l1.address as SADD, l2.location_id as DID, l2.address as DADD,c1.city_name as CS, c2.city_name as CD, " +
                        "t.VEHICLE_ID, t.BASE_PRICE,t.UPGRADE_PCT, t.DURATION, com.COMPANY_NAME AS COMPANY_NAME " +
                        "FROM TRIP t join location l1 on (t.start_from = l1.location_id) " +
                        "join location l2 on (t.destination = l2.location_id) " +
                        "join city c1 on (l1.city_id = c1.city_id) " +
                        "join city c2 on (l2.city_id = c2.city_id) " +
                        "join vehicle v on(t.vehicle_id = v.vehicle_id) " +
                        "join company com on(v.company_id = com.company_id) " +
                "WHERE t.trip_id = ?";
        return getJdbcTemplate().queryForMap(sql, tripId);
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

    public List<Map<String,Object>> getAllTripsByCompany(String companyId) {
        String sql =
                "SELECT t.trip_id, t.BASE_PRICE, t.UPGRADE_PCT, t.START_TIME,t.DURATION, c1.CITY_NAME as STARTFROM, c2.CITY_NAME as DES\n" +
                        "from TRIP t JOIN LOCATION l1 ON(t.START_FROM = l1.LOCATION_ID)\n" +
                        "JOIN LOCATION l2 on(t.DESTINATION = l2.LOCATION_ID)\n" +
                        "JOIN CITY c1 ON(l1.CITY_ID = c1.CITY_id)\n" +
                        "JOIN CITY c2 on(l2.CITY_ID = c2.CITY_ID)\n " +
                        "JOIN VEHICLE v on(t.vehicle_id = v.vehicle_id)" +
                        "WHERE v.company_id = ?";
        return getJdbcTemplate().queryForList(sql,companyId);
    }

    public Map<String,Object> getTripByIdAndCompany(String tripId, String companyId) {
        String sql = "SELECT t.TRIP_ID, l1.LOCATION_ID as SID, l1.address as SADD, l2.location_id as DID, l2.address as DADD,c1.city_name as CS, c2.city_name as CD, " +
                "t.VEHICLE_ID, t.BASE_PRICE,t.UPGRADE_PCT, t.DURATION, com.COMPANY_NAME AS COMPANY_NAME " +
                "FROM TRIP t join location l1 on (t.start_from = l1.location_id) " +
                "join location l2 on (t.destination = l2.location_id) " +
                "join city c1 on (l1.city_id = c1.city_id) " +
                "join city c2 on (l2.city_id = c2.city_id) " +
                "join vehicle v on(t.vehicle_id = v.vehicle_id) " +
                "join company com on(v.company_id = com.company_id) " +
                "WHERE t.trip_id = ? AND com.company_id = ?";
        return getJdbcTemplate().queryForMap(sql, tripId,companyId);
    }

    public List<Map<String,Object>> searchTrips(String cityName) {
        String sql =
                "SELECT t.trip_id, t.BASE_PRICE, t.UPGRADE_PCT, t.START_TIME,t.DURATION, c1.CITY_NAME as STARTFROM, c2.CITY_NAME as DES " +
                        "from TRIP t JOIN LOCATION l1 ON(t.START_FROM = l1.LOCATION_ID)\n" +
                        "JOIN LOCATION l2 on(t.DESTINATION = l2.LOCATION_ID)\n" +
                        "JOIN CITY c1 ON(l1.CITY_ID = c1.CITY_id)\n" +
                        "JOIN CITY c2 on(l2.CITY_ID = c2.CITY_ID) " +
                        "where c1.CITY_NAME like ?";
        return getJdbcTemplate().queryForList(sql,cityName.concat("%"));
    }


    public List<Map<String,Object>> searchTrips(String cityName,String companyId) {
        String sql =
                "SELECT t.trip_id, t.BASE_PRICE, t.UPGRADE_PCT, t.START_TIME,t.DURATION, c1.CITY_NAME as STARTFROM, c2.CITY_NAME as DES\n" +
                        "from TRIP t JOIN LOCATION l1 ON(t.START_FROM = l1.LOCATION_ID)\n" +
                        "JOIN LOCATION l2 on(t.DESTINATION = l2.LOCATION_ID)\n" +
                        "JOIN CITY c1 ON(l1.CITY_ID = c1.CITY_id)\n" +
                        "JOIN CITY c2 on(l2.CITY_ID = c2.CITY_ID)\n " +
                        "JOIN VEHICLE v on(t.vehicle_id = v.vehicle_id) " +
                        "WHERE v.company_id = ? and c1.CITY_NAME like ?";
        return getJdbcTemplate().queryForList(sql,companyId,cityName.concat("%"));
    }



    public List<Trip> searchTripReq(SearchRequest req) {
        String sql =
                "SELECT * FROM TRIP t " +
                        "WHERE t.START_TIME >= ? " +
                        "AND (SELECT L.CITY_ID FROM LOCATION L WHERE t.START_FROM = L.LOCATION_ID) = ? " +
                        "AND (SELECT COUNT(*) FROM TICKET t2 WHERE t.TRIP_ID = t2.TRIP_ID AND t2.TYPE = ?) >= ? ";
        return getJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(Trip.class), req.getTravellingDate(),
                req.getStartingCity(), req.getType(), req.getNumberOfTravellers());

    }

    private String getCity(String loc){
        String sql = "SELECT L.CITY_ID FROM LOCATION L WHERE L.LOCATION_ID = ?";
        return getJdbcTemplate().queryForObject(sql, BeanPropertyRowMapper.newInstance(String.class), loc);
    }

    private List<Plan> searchPlanUtil(SearchRequest req, int dep){
        if(req.getStartingCity() == req.getDestinationCity())return new ArrayList<Plan>();
        if(dep == 0)return null;
        List<Plan> ret = new ArrayList<Plan>();
        List<Trip> l = searchTripReq(req);
        CityDAO cd = new CityDAO();
        City c = cd.getCityById(req.getStartingCity());
        for(Trip t : l){
            SearchRequest req2 = new SearchRequest(req);
            City c2 = cd.getCityById(getCity(t.getDestination()));
            req2.setStartingCity(c2.getCityId());
            req2.setTravellingDate(new Date(req.getTravellingDate().getTime() + (long)((t.getDuration() +
                    (long)c2.getTimezone() - (long)c.getTimezone())* 3600 * 1000)));
            List<Plan> temp = searchPlanUtil(req2, dep - 1);
            if(temp == null)continue;
            for(Plan p : temp){
                boolean ok = true;
                for(Trip k : p.getTrips()){
                    if(getCity(k.getDestination()) == c.getCityId())ok = false;
                }
                if(ok){
                    p.addTrip(t);
                    ret.add(p);
                }
            }
        }

        return ret;
    }
    public List<Plan> searchPlan(SearchRequest req){
        List<Plan> ret = searchPlanUtil(req, 3);
        for(Plan p : ret)p.organize(req);
        return ret;
    }

}
