package com.hibernate.util;

import javax.persistence.Entity;

@Entity
public class TestHibernate {

	public static void main(String args[]) {
		HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
		
		
		HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
		HibernateUtil.getSessionFactory().getCurrentSession().close();
		HibernateUtil.closeSessionFactory();
	}
}
