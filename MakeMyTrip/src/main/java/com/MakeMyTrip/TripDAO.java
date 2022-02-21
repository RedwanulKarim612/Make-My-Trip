package com.MakeMyTrip;

import com.fasterxml.jackson.core.JsonToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
            String sql = "SELECT t.TRIP_ID,t.START_TIME, l1.LOCATION_ID as SID, l1.address as SADD, l2.location_id as DID, l2.address as DADD,c1.city_name as CS, c2.city_name as CD, " +
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

    public List<Map<String,Object>> getBookedTripsByCustomer() throws EmptyResultDataAccessException{
        String sql = "SELECT TR.TRIP_ID, COUNT(T.TICKET_ID) AS CNT,TR.START_TIME, TR.DURATION,\n" +
                "\tL1.ADDRESS SADD,\n" +
                "\tL2.ADDRESS DADD,\n" +
                "\tC1.CITY_NAME SC,\n" +
                "\tC2.CITY_NAME DC,\n" +
                "\tV.VEHICLE_ID,\n" +
                "\tCOM.COMPANY_NAME\n" +
                "FROM TRIP TR JOIN TICKET T ON(TR.TRIP_ID = T.TRIP_ID)\n" +
                "JOIN LOCATION L1 ON(TR.START_FROM = L1.LOCATION_ID)\n" +
                "JOIN LOCATION L2 ON(TR.DESTINATION = L2.LOCATION_ID)\n" +
                "JOIN CITY C1 ON(L1.CITY_ID = C1.CITY_ID)\n" +
                "JOIN CITY C2 ON(L2.CITY_ID = C2.CITY_ID)\n" +
                "JOIN VEHICLE V ON(V.VEHICLE_ID = TR.VEHICLE_ID)\n" +
                "JOIN COMPANY COM ON(V.COMPANY_ID = COM.COMPANY_ID) \n" +
                "WHERE T.BOUGHT_BY = ? \n" +
                "GROUP BY TR.TRIP_ID ,TR.START_TIME,TR.DURATION, L1.ADDRESS, L2.ADDRESS , C1.CITY_NAME, C2.CITY_NAME, V.VEHICLE_ID, COM.COMPANY_NAME";
        return getJdbcTemplate().queryForList(sql,SecurityContextHolder.getContext().getAuthentication().getName());
    }



    public List<Trip> searchTripReq(SearchRequest req, Date last) {
        String sql =
                "SELECT t.trip_id, t.BASE_PRICE, t.UPGRADE_PCT, t.START_TIME,t.DURATION,t.VEHICLE_ID, t.START_FROM, t.DESTINATION FROM TRIP t " +
                        "WHERE t.START_TIME BETWEEN ? AND ?\n " +
                        "AND (SELECT L.CITY_ID FROM LOCATION L WHERE t.START_FROM = L.LOCATION_ID) = ? " +
                        "AND (SELECT COUNT(*) FROM TICKET t2 WHERE t.TRIP_ID = t2.TRIP_ID AND t2.TYPE = ?) >= ?";
        List<Map<String,Object>> ms =  getJdbcTemplate().queryForList(sql, req.getTravellingDate(),
                last, req.getStartingCity(), req.getType(), req.getNumberOfTravellers());
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

    private Double getTimeZone(String loc){
        String sql = "SELECT C.TIMEZONE FROM LOCATION L JOIN CITY C ON (L.CITY_ID = C.CITY_ID) WHERE L.LOCATION_ID = ?";
        return (double)getJdbcTemplate().queryForObject(sql,Double.class,loc);
    }

    Date shiftDateByHour(Date d, double hour){
        return new Date(d.getTime() + (long)(hour * 3600 * 1000));
    }
    private List<Plan> searchPlanUtil(SearchRequest req, int dep){
        if(req.getStartingCity().equals(req.getDestinationCity()))return new ArrayList<Plan>(Arrays.asList(new Plan()));
        if(dep == 0)return null;
        Date last = shiftDateByHour(req.getTravellingDate(), dep == 3 ? 24 : 7);
        List<Plan> ret = new ArrayList<Plan>();
        List<Trip> l = searchTripReq(req, last);
        City c = cd.getCityById(req.getStartingCity());
        for(Trip t : l){
            SearchRequest req2 = new SearchRequest(req);
            City c2 = cd.getCityById(getCity(t.getDestination()));
            req2.setStartingCity(c2.getCityId());
            req2.setTravellingDate(shiftDateByHour(t.getDate(), t.getDuration() +
                    c2.getTimezone() - c.getTimezone() + 1));
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

        for(Plan p : ret) {
//            System.out.println(p.getTrips().get(0).getStartFrom());
//            System.out.println("ddd " + new Date((p.getTrips().get(0).getDate().getTime()+(long)(((p.getTrips().get(0).getDuration()*1.0-getTimeZone(p.getTrips().get(0).getStartFrom()))*1000.0*60*60)))));
//            p.setTotalDuration(((new Date((p.getTrips().get(0).getDate().getTime()+(long)(((p.getTrips().get(0).getDuration()*1.0-getTimeZone(p.getTrips().get(0).getStartFrom()))*1000.0*60*60))))).getTime()
//                                    -new Date(p.getTrips().get(p.getTrips().size()-1).getDate().getTime()-(long)(1.0*getTimeZone(p.getTrips().get(p.getTrips().size()-1).getStartFrom())*1000.0*60*60)).getTime())/(1000.0*60*60));
            p.organize(req);
        }
        if(req.getOrder().equalsIgnoreCase("PRICE")){
            Collections.sort(ret, (p1, p2) -> (p1.getPrice() - p2.getPrice() > 0 ? 1 : -1));
        }else if(req.getOrder().equalsIgnoreCase("NO_OF_TRIPS")){
            Collections.sort(ret, (p1, p2) -> p1.getNumberOfTrips() - p2.getNumberOfTrips());
        }else{
            Collections.sort(ret, (p1, p2) -> (p1.getTotalDuration() - p2.getTotalDuration() > 0 ? 1 : -1));
        }
        System.out.println(ret.size());
        return ret;
    }

    public List<Map<String,Object>> getTripsInPlan(Plan plan) {
        List<String> ids = new ArrayList<>();
        for(Trip trip: plan.getTrips()){
            ids.add(trip.getTripId());
        }
        return getTripsInList(ids);
    }

    public List<Map<String, Object>> getTripsInList(List<String> tripIds) {
        String sql = "SELECT\n" +
                "t.TRIP_ID,\n" +
                "l1.LOCATION_ID AS SID,\n" +
                "l1.address AS SADD,\n" +
                "l2.location_id AS DID,\n" +
                "l2.address AS DADD,\n" +
                "c1.city_name AS CS,\n" +
                "c2.city_name AS CD,\n" +
                "t.VEHICLE_ID,\n" +
                "t.BASE_PRICE,\n" +
                "t.UPGRADE_PCT,\n" +
                "t.DURATION,\n" +
                "com.COMPANY_NAME AS COMPANY_NAME,\n" +
                "TO_CHAR(t.START_TIME,'dd MON yyyy HH:MI AM') as START_TIME,\n" +
                "TO_CHAR(t.START_TIME + (t.DURATION + c2.TIMEZONE -c1.TIMEZONE)/24, 'dd MON yyyy HH:MI AM') as finishTime\n" +
                "\n" +
                "FROM\n" +
                "TRIP t\n" +
                "JOIN location l1 ON ( t.start_from = l1.location_id )\n" +
                "JOIN location l2 ON ( t.destination = l2.location_id )\n" +
                "JOIN city c1 ON ( l1.city_id = c1.city_id ) join city c2 ON ( l2.city_id = c2.city_id )\n" +
                "JOIN vehicle v ON ( t.vehicle_id = v.vehicle_id )\n" +
                "JOIN company com ON ( v.company_id = com.company_id ) \n" +
                "WHERE\n" +
                "t.TRIP_ID =? ";
        List<Map<String,Object>> mp = new ArrayList<>();
        for (String id: tripIds){
            mp.add(getJdbcTemplate().queryForMap(sql,id));
        }
        return mp;
    }

    public List<String> bookTickets(TravellersForm form){
        List<String> travellerIds = new ArrayList<>();
        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        int numTicket = form.getTravellers().size();
        if(numTicket == 0)return travellerIds;
        double costOfEachTicket = 0;
        List<List<String>> all = new ArrayList<>();
        String sql;
        for(String t_id : form.getTripIds()){
            sql = "SELECT TICKET_ID FROM TICKET WHERE TRIP_ID = ? AND TYPE = ? AND BOUGHT_BY IS NULL AND ROWNUM <= ?";
            List<String> tickets = new ArrayList<>();
            List<Map<String, Object>> m = getJdbcTemplate().queryForList(sql, t_id, form.getType(), numTicket);
            for(Map<String, Object> tt : m)tickets.add(tt.get("TICKET_ID").toString());
            if(tickets.size() != numTicket)return null;
            all.add(tickets);
            sql = "SELECT PRICE FROM TICKET WHERE TRIP_ID = ? AND TYPE = ? AND BOUGHT_BY IS NULL AND ROWNUM <= 1";
            costOfEachTicket += getJdbcTemplate().queryForObject(sql, Double.class, t_id, form.getType());
        }
        sql = "SELECT AMOUNT FROM WALLET WHERE CUSTOMER_ID = ?";
        Double balance = getJdbcTemplate().queryForObject(sql, Double.class, buyer);
        if(balance < costOfEachTicket * numTicket){
            return null;
        }
        int id = 0;
        String Insert_sql = "INSERT INTO TRAVELLER(NAME, IDENTYFICATION_TYPE, IDENTIFICATION_NO) " +
                "VALUES(?, ?, ?)";
        for(Traveller tr : form.getTravellers()){
            KeyHolder keyholder = new GeneratedKeyHolder();
            getJdbcTemplate().update(connection -> {
                PreparedStatement ps = connection.prepareStatement(Insert_sql, new String[] {"TRAVELLER_ID"});
                ps.setString(1, tr.getName());
                ps.setString(2, tr.getIdentificationType());
                ps.setString(3, tr.getIdentificationNo());
                return ps;
            }, keyholder);
            Map<String, Object> key = keyholder.getKeys();
            tr.setTravellerId(key.get("TRAVELLER_ID").toString());
            travellerIds.add(key.get("TRAVELLER_ID").toString());
        }
        for(String t_id : form.getTripIds()){
            List<String> tkt = all.get(id++);
            sql = "UPDATE TICKET SET " +
                    "TRAVELLER_ID = ?, " +
                    "BOUGHT_BY = ? " +
                    "WHERE TICKET_ID = ?";
            for(int i = 0; i < numTicket; i++)
                getJdbcTemplate().update(sql, form.getTravellers().get(i).getTravellerId(), buyer, tkt.get(i));
        }

        sql = "UPDATE WALLET SET AMOUNT = ? WHERE CUSTOMER_ID = ?";
        getJdbcTemplate().update(sql, balance - numTicket * costOfEachTicket, buyer);
        return travellerIds;
    }

    public Map<String,Object> getPlanInfo(Plan plan){
        Map<String,Object> mp1 = new HashMap<>();
        String sql1 = "SELECT t.TRIP_ID,t.START_TIME, l1.LOCATION_ID as SID, l1.address as SADD, c1.city_name as CS,c1.timezone   " +
                "FROM TRIP t join location l1 on (t.start_from = l1.location_id) " +
                "join city c1 on (l1.city_id = c1.city_id) " +
                "WHERE t.trip_id = ?";
        mp1 = getJdbcTemplate().queryForMap(sql1,plan.getTrips().get(0).getTripId());
        System.out.println(mp1.get("START_TIME"));
        String sql2 = "SELECT t.TRIP_ID, l1.LOCATION_ID as DID, l1.address as DADD, c1.city_name as CD, ?+?/24-?/24+c1.timezone/24 as FINISH_TIME  " +
                "FROM TRIP t join location l1 on (t.destination = l1.location_id) " +
                "join city c1 on (l1.city_id = c1.city_id) " +
                "WHERE t.trip_id = ?";

        Map<String,Object> mp2 = getJdbcTemplate().queryForMap(sql2,mp1.get("START_TIME"), plan.getTotalDuration(),mp1.get("TIMEZONE"),plan.getTrips().get(plan.getTrips().size()-1).getTripId());
        Map<String,Object> mp = new HashMap<>();
        mp.putAll(mp1);
        mp.putAll(mp2);
        System.out.println("d " + mp.get("FINISH_TIME"));
        return mp;
    }

    public List<Map<String, Object>> getPlansBriefInfo(List<Plan> plans){
        List<Map<String,Object>> mpList = new ArrayList<>();
        for(Plan p : plans){
            mpList.add(getPlanInfo(p));
        }
        return mpList;
    }
}
