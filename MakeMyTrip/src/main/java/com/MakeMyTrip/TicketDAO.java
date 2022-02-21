package com.MakeMyTrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TicketDAO extends JdbcDaoSupport {
    @Autowired
    DataSource dataSource;
    @PostConstruct
    public void initialize(){
        setDataSource(dataSource);
    }

    public List<Map<String,Object>> getTicketBookedByUser(String tripId){
        String sql = "SELECT T.TICKET_ID, T.PRICE, T.TYPE, T.SEAT_NO , TV.*\n" +
                "FROM TICKET T\n" +
                "JOIN TRAVELLER TV ON(T.TRAVELLER_ID = TV.TRAVELLER_ID)\n" +
                "WHERE T.BOUGHT_BY = ? AND T.TRIP_ID = ?";
        return getJdbcTemplate().queryForList(sql, SecurityContextHolder.getContext().getAuthentication().getName(),tripId);
    }

    public List<Map<String,Object>> getTicketsForBooking(List<String> travellerIds, String tripId){
        String sql = "SELECT T.* , TR.NAME, TR.IDENTYFICATION_TYPE, TR.IDENTIFICATION_NO " +
                "FROM TICKET T JOIN TRAVELLER TR ON(T.TRAVELLER_ID = TR.TRAVELLER_ID) " +
                "WHERE TR.TRAVELLER_ID = ? AND T.TRIP_ID = ?";
        List<Map<String,Object>> ls = new ArrayList<>();

        for(String id: travellerIds){
            ls.add(getJdbcTemplate().queryForMap(sql,id,tripId));
        }
        return ls;
    }
}
