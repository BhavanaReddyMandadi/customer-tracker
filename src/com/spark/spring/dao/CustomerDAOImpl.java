package com.spark.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spark.spring.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// First of all we need to add the seesion factory of the hibernate so that this will interact with the database 
	//and this session contains the datatsource
	//which is injected i.e the refernce object of the datasource is stored in sessionfactory in the configuration file
	// Note: we should keep the same refrence name which we used in the config file i.e. the bean id ref sessionfactory is given in xml so i kept same name here
	
	@Autowired
	private SessionFactory sessionFactory;
 	
	@Override
	public List<Customer> getCustomers() {
		// get the current hibernate session 
		Session currentSession = sessionFactory.getCurrentSession();  
		
		// create a query and sort by last name 
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", 
															   Customer.class);
		
		//execute query and return result list
		List<Customer> customers = theQuery.getResultList();
		
	   return customers;	
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		// get current the hibernate session 
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save the customer to the databse
		currentSession.saveOrUpdate(theCustomer);
		
		
		
	}

	@Override
	public Customer getCustomers(int theId) {
		// get the current hibernate session 
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
		
		
	}

	@Override
	public void deleteCustomer(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key 
		
		Query theQuery = 
	    currentSession.createQuery("delete from Customer where id:=customerId");
		theQuery.setParameter("customerId", theId);
	        
		theQuery.executeUpdate();
	
	}

}
