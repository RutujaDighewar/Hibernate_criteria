package com.app.entity;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

public class Test {

	public void CriteriaTest() {
		Session session = HibernateUtility.getSession().openSession();
		Criteria cr = session.createCriteria(Employee.class);
		List<Employee> list = cr.list();
		list.forEach(System.out::println);
	}

	public void andRestriction() {
		Session session = HibernateUtility.getSession().openSession();
		Criteria cr = session.createCriteria(Employee.class);
		cr.add(Restrictions.eq("name", "Richa")).add(Restrictions.eq("mobile", "8493789"));
		List<Employee> list = cr.list();
		list.forEach(System.out::println);
	}

	public void betweenRestriction() {
		Session session = HibernateUtility.getSession().openSession();
		Criteria cr = session.createCriteria(Employee.class);
		cr.add(Restrictions.between("salary", new Double(10000), new Double(50000)));
		List<Employee> list = cr.list();
		list.forEach(System.out::println);

	}

	public void uniqueResultRestriction() {
		Session session = HibernateUtility.getSession().openSession();
		Criteria cr = session.createCriteria(Employee.class);
		cr.add(Restrictions.eqOrIsNull("name", "Sukhi")).add(Restrictions.eq("age", 29));
		Employee emp = (Employee) cr.uniqueResult();
		System.out.println(emp);
	}

	public void projectionTest() {
		Session session = HibernateUtility.getSession().openSession();
		Criteria cr = session.createCriteria(Employee.class);
		cr.setProjection(Projections.min("salary"));
		System.out.println(cr.uniqueResult());

	}

	public void columnWiseProjection() {
		Session session = HibernateUtility.getSession().openSession();
		Criteria cr = session.createCriteria(Employee.class);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("name"));
		projectionList.add(Projections.property("age"));
		cr.setProjection(projectionList);
		cr.setResultTransformer(new ResultTransformer() {

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Employee emp = new Employee();
				emp.setName((String) tuple[0]);
				emp.setAge((Integer) tuple[1]);
				return emp;
			}

			@Override
			public List transformList(List list) {
				return list;
			}

		});
		List<Employee> list = cr.list();
		list.forEach(System.out::println);

	}

	public void hql() {
		Session session = HibernateUtility.getSession().openSession();
		Query query = session.createQuery("from Employee");
		query.list().forEach(System.out::println);
	}

	public void hql1() {
		Session session = HibernateUtility.getSession().openSession();
		Query query = session.createQuery("from Employee where id=1");
		query.list().forEach(System.out::println);
	}

	public void hql2() {
		int id = 5;
		Session session = HibernateUtility.getSession().openSession();
		Query query = session.createQuery("from Employee where id=:sid");
		query.setParameter("sid", id);
		query.list().forEach(System.out::println);
	}

	public void sql() {
		Session session = HibernateUtility.getSession().openSession();
		SQLQuery query = session.createSQLQuery("select * from Employee");
		query.setResultTransformer(new ResultTransformer() {

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				Employee emp = new Employee();
				emp.setId((Integer) tuple[0]);
				emp.setName((String) tuple[1]);
				emp.setMobile((String) tuple[2]);
				emp.setSalary((Double) tuple[3]);
				emp.setAge((Integer) tuple[4]);
				return emp;
			}

			@Override
			public List transformList(List list) {
				return list;
			}
		});
		query.list().forEach(System.out::println);
	}

	public static void main(String[] args) {

		Test t = new Test();
		t.CriteriaTest();
		t.andRestriction();
		// t.betweenRestriction();
		// t.uniqueResultRestriction();
		// t.projectionTest();
		// t.columnWiseProjection();
		// t.hql();
		// t.hql1();
		// t.hql2();
		// t.sql();
	}

}
