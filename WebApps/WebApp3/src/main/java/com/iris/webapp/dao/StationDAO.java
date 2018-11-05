package com.iris.webapp.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iris.webapp.entity.Station;

@Transactional
@Repository
public class StationDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public List<String> getAllStations() {
        try {
            String sql = "Select s.name from " + Station.class.getName() + " s";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<String> query = session.createQuery(sql, String.class);
            return (List<String>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public int getStationIdFromName(String stationName) {
        try {
            String sql = "Select s.id from " + Station.class.getName() + " s where s.name = :stationName";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<Integer> query = session.createQuery(sql, Integer.class);
            query.setParameter("stationName", stationName);
            return (int) query.getSingleResult();
        } catch (NoResultException e) {
            return -1;
        }
    }
}