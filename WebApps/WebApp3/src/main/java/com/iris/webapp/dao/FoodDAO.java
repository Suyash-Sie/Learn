package com.iris.webapp.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iris.webapp.entity.Food;

@Transactional
@Repository
public class FoodDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public List<Food> getFoodItemsOfRestaurant(int restaurantId) {
        try {
            String sql = "Select f from " + Food.class.getName() + " f where f.restaurant =" + restaurantId;
            Session session = this.sessionFactory.getCurrentSession();
            Query<Food> query = session.createQuery(sql, Food.class);
            return (List<Food>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}