package com.iris.webapp.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iris.webapp.entity.Restaurant;

@Transactional
@Repository
public class RestaurantDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Restaurant> getAllRestaurantsForStation(int stationId) {
		try {
			String sql = "Select r from " + Restaurant.class.getName() + " r where r.station =" + stationId;
			Session session = this.sessionFactory.getCurrentSession();
			Query<Restaurant> query = session.createQuery(sql, Restaurant.class);
			return (List<Restaurant>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}