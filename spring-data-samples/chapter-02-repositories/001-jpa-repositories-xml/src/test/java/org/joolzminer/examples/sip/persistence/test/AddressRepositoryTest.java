package org.joolzminer.examples.sip.persistence.test;

import javax.inject.Inject;

import junit.framework.Assert;

import org.joolzminer.examples.sdata.domain.Address;
import org.joolzminer.examples.sdata.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/application-context.xml"})
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class AddressRepositoryTest {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AddressRepositoryTest.class);
	
	@Inject private AddressRepository addressRepository;
	
	@Test
	@Transactional(readOnly = false)
	public void testSaveAddress() {
		Address savedAddress = addressRepository.save(new Address("street", "city", "coutry"));
		
		Assert.assertEquals(savedAddress, addressRepository.findOne(savedAddress.getId()));
	}	
}
