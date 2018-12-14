package com.iris.webapp.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iris.webapp.entity.OrderDetail;

@Transactional
@Repository
public class OrderDetailDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public int getMaxOrderId() {
		try {
			String sql = "Select max(o.id) from " + OrderDetail.class.getName() + " o";

			Session session = this.sessionFactory.getCurrentSession();
			Query<Integer> query = session.createQuery(sql, Integer.class);
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	public int createOrder(OrderDetail order) {
		String sql = "insert into " + OrderDetail.class.getName() + " values ?";
		Session session = this.sessionFactory.getCurrentSession();
		Query<OrderDetail> query = session.createQuery(sql, OrderDetail.class);
		query.setParameter(0, order);
		int result = query.executeUpdate();
		return result;
	}
}