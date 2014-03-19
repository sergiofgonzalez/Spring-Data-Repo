package org.joolzminer.examples.sdata.jpa.core;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class JpaCustomerRepository implements CustomerRepository {

	private static final String JPQL_SELECT_BY_EMAIL = "select c from Customer c where c.emailAddress = :email";
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Customer findOne(Long id) {
		return em.find(Customer.class, id);
	}

	@Override
	@Transactional(readOnly = false)
	public Customer save(Customer customer) {
		if (customer.getId() == null) {
			em.persist(customer);
			return customer;
		} else {
			return em.merge(customer);		
		}
	}

	@Override
	public Customer findByEmailAddress(EmailAddress emailAddress) {
		TypedQuery<Customer> query = em.createQuery(JPQL_SELECT_BY_EMAIL, Customer.class);
		query.setParameter("email", emailAddress);
		
		return query.getSingleResult();
	}

}
