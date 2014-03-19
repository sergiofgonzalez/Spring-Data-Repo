package org.joolzminer.examples.sdata.mongo.repository;

import java.util.List;

import org.joolzminer.examples.sdata.mongo.domain.core.Customer;
import org.joolzminer.examples.sdata.mongo.domain.order.Order;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	
	List<Order> findByCustomer(Customer customer);
}
