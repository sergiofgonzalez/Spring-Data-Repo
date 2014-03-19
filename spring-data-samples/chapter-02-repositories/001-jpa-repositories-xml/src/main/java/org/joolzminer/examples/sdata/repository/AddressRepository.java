package org.joolzminer.examples.sdata.repository;

import org.joolzminer.examples.sdata.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long>{
}
