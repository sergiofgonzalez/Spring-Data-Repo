package org.joolzminer.examples.sdata.jpa.order;

import java.util.List;

import org.joolzminer.examples.sdata.jpa.core.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
	List<Order> findByCustomer(Customer customer);
}
