package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class TripDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @Autowired
    CityDAO cd;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

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
                "SELECT t.trip_id, t.BASE_PRICE, t.UPGRADE_PCT, t.START_TIME,t.DURATION,t.VEHICLE_ID, t.START_FROM, t.DESTINATION FROM TRIP t " +
                        "WHERE t.START_TIME >= ? " +
                        "AND (SELECT L.CITY_ID FROM LOCATION L WHERE t.START_FROM = L.LOCATION_ID) = ? " +
                        "AND (SELECT COUNT(*) FROM TICKET t2 WHERE t.TRIP_ID = t2.TRIP_ID AND t2.TYPE = ?) >= ? ";
        List<Map<String,Object>> ms =  getJdbcTemplate().queryForList(sql, req.getTravellingDate(),
                req.getStartingCity(), req.getType(), req.getNumberOfTravellers());
        List<Trip> trips = new ArrayList<>();

        for(Map m : ms){
            Trip trip = new Trip();
            trip.setTripId(m.get("TRIP_ID").toString());
            trip.setBasePrice(Double.parseDouble(m.get("BASE_PRICE").toString()));
            trip.setUpgradePct(Double.parseDouble(m.get("UPGRADE_PCT").toString()));
            trip.setDate(m.get("START_TIME").toString());
            trip.setDuration(Double.parseDouble(m.get("DURATION").toString()));
            trip.setVehicleId((String)m.get("VEHICLE_ID").toString());
            trip.setStartFrom((String)m.get("START_FROM").toString());
            trip.setDestination((String)m.get("DESTINATION").toString());
            trips.add(trip);
        }
        return trips;
    }

    private String getCity(String loc){
        String sql = "SELECT L.CITY_ID FROM LOCATION L WHERE L.LOCATION_ID = ?";
        return getJdbcTemplate().queryForObject(sql, String.class, loc);
    }

    private List<Plan> searchPlanUtil(SearchRequest req, int dep){
        if(req.getStartingCity().equals(req.getDestinationCity()))return new ArrayList<Plan>(Arrays.asList(new Plan()));
        System.out.println(req.getTravellingDate());
        if(dep == 0)return null;
        List<Plan> ret = new ArrayList<Plan>();
        List<Trip> l = searchTripReq(req);
//        System.out.println(l.size());
        City c = cd.getCityById(req.getStartingCity());
        for(Trip t : l){
            SearchRequest req2 = new SearchRequest(req);
//            System.out.println(t);
            City c2 = cd.getCityById(getCity(t.getDestination()));
            req2.setStartingCity(c2.getCityId());
            req2.setTravellingDate(new Date(t.getDate().getTime() + (long)((t.getDuration() +
                    c2.getTimezone() -c.getTimezone())*3600*1000)));

//            System.out.println(t.getTripId() + " " + t.getDate() + " d " + req2.getTravellingDate());
            List<Plan> temp = searchPlanUtil(req2, dep - 1);
            if(temp == null)continue;
            for(Plan p : temp){
                boolean ok = true;
                for(Trip k : p.getTrips()){
                    if(getCity(k.getDestination()).equals(c.getCityId()))ok = false;
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
        System.out.println(ret.size());
        return ret;
    }

}
