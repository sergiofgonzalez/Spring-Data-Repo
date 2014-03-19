package org.joolzminer.examples.sdata.jdbc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.joolzminer.examples.sdata.jdbc.domain.Address;
import org.joolzminer.examples.sdata.jdbc.domain.Customer;
import org.joolzminer.examples.sdata.jdbc.domain.EmailAddress;
import org.joolzminer.examples.sdata.jdbc.domain.QAddress;
import org.joolzminer.examples.sdata.jdbc.domain.QCustomer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.core.OneToManyResultSetExtractor;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jdbc.query.SqlDeleteCallback;
import org.springframework.data.jdbc.query.SqlInsertCallback;
import org.springframework.data.jdbc.query.SqlInsertWithKeyCallback;
import org.springframework.data.jdbc.query.SqlUpdateCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Path;

@Repository
@Transactional(readOnly = true)
public class QueryDslCustomerRepository implements CustomerRepository {

	private final QCustomer qCustomer = QCustomer.customer;
	private final QAddress qAddress = QAddress.address;

	private QueryDslJdbcTemplate qdslJdbcTemplate;

	@SuppressWarnings("rawtypes")
	private Path[] customerAddressProjection;

	@Resource
	public void setDataSource(DataSource dataSource) {
		qdslJdbcTemplate = new QueryDslJdbcTemplate(dataSource);
		customerAddressProjection = new Path[] { qCustomer.id,
				qCustomer.firstName, qCustomer.lastName,
				qCustomer.emailAddress, qAddress.id, qAddress.customerId,
				qAddress.street, qAddress.city, qAddress.country };
	}

	@Override
	public Customer findById(Long id) {
		if (id == null) {
			return null;
		}
		
		SQLQuery findByIdQuery = qdslJdbcTemplate.newSqlQuery()
									.from(qCustomer)
									.leftJoin(qCustomer._customerCustomerIdFk, qAddress)
									.where(qCustomer.id.eq(id));
		
		return qdslJdbcTemplate.queryForObject(findByIdQuery, new CustomerExtractor(), customerAddressProjection);
	}

	@Override	
	public List<Customer> findAll() {
		SQLQuery allCustomersQuery = qdslJdbcTemplate.newSqlQuery()
										.from(qCustomer)
										.leftJoin(qCustomer._customerCustomerIdFk, qAddress);
		return qdslJdbcTemplate.query(allCustomersQuery, new CustomerListExtractor(), customerAddressProjection);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(final Customer customer) {
		if (customer.getId() == null) {
			Long generatedKey = qdslJdbcTemplate.insertWithKey(qCustomer, new SqlInsertWithKeyCallback<Long>() {

				@Override
				public Long doInSqlInsertWithKeyClause(SQLInsertClause insert) throws SQLException {
					return insert	
							.columns(qCustomer.firstName, qCustomer.lastName, qCustomer.emailAddress)
							.values(customer.getFirstName(), customer.getLastName(), customer.getEmailAddress() == null ? null : customer.getEmailAddress().toString())
							.executeWithKey(qCustomer.id);
				}
			});
			customer.setId(generatedKey);
		} else {
			qdslJdbcTemplate.update(qCustomer, new SqlUpdateCallback() {
				
				@Override
				public long doInSqlUpdateClause(SQLUpdateClause update) {
					return update
							.where(qCustomer.id.eq(customer.getId()))
							.set(qCustomer.firstName, customer.getFirstName())
							.set(qCustomer.lastName, customer.getLastName())
							.set(qCustomer.emailAddress, customer.getEmailAddress() == null ? null : customer.getEmailAddress().toString())
							.execute();
				}
			});
		}
		
		// Save address data (remember it is managed by Customer entity)
		final List<Long> ids = new ArrayList<Long>();
		for (Address address : customer.getAddresses()) {
			if (address.getId() != null) {
				ids.add(address.getId());
			}
		}
		
		// Step 1: delete any potentially removed addresses
		if (ids.size() > 0) {
			qdslJdbcTemplate.delete(qAddress, new SqlDeleteCallback() {
				
				@Override
				public long doInSqlDeleteClause(SQLDeleteClause delete) {
					return delete
							.where(qAddress.customerId.eq(customer.getId())
									.and(qAddress.id.notIn(ids)))
							.execute();
				}
			});
		}
		
		// Step 2: update existing ones and add new ones
		for (final Address address : customer.getAddresses()) {
			if (address.getId() != null) {
				qdslJdbcTemplate.update(qAddress, new SqlUpdateCallback() {
					
					@Override
					public long doInSqlUpdateClause(SQLUpdateClause update) {
						return update
								.where(qAddress.id.eq(address.getId()))
								.set(qAddress.customerId, customer.getId())
								.set(qAddress.street, address.getStreet())
								.set(qAddress.city, address.getCity())
								.set(qAddress.country, address.getCountry())
								.execute();
					}
				});
			} else {
				qdslJdbcTemplate.insert(qAddress, new SqlInsertCallback() {
					
					@Override
					public long doInSqlInsertClause(SQLInsertClause insert) {
						return insert
								.columns(qAddress.customerId, qAddress.street, qAddress.city, qAddress.country)
								.values(customer.getId(), address.getStreet(), address.getCity(), address.getCountry())
								.execute();
					}
				});
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(final Customer customer) {
		qdslJdbcTemplate.delete(qAddress, new SqlDeleteCallback() {
			
			@Override
			public long doInSqlDeleteClause(SQLDeleteClause delete) {
				return delete.where(qAddress.customerId.eq(customer.getId())).execute();
			}
		});
		
		qdslJdbcTemplate.delete(qCustomer, new SqlDeleteCallback() {
			
			@Override
			public long doInSqlDeleteClause(SQLDeleteClause delete) {
				return delete.where(qCustomer.id.eq(customer.getId())).execute();
			}
		});
		
	}

	@Override
	public Customer findByEmailAddress(EmailAddress emailAddress) {
		if (emailAddress == null) {
			return null;
		}
		
		SQLQuery findByIdQuery = qdslJdbcTemplate.newSqlQuery()
									.from(qCustomer)
									.leftJoin(qCustomer._customerCustomerIdFk, qAddress)
									.where(qCustomer.emailAddress.eq(emailAddress.toString()));
		
		return qdslJdbcTemplate.queryForObject(findByIdQuery, new CustomerExtractor(), customerAddressProjection);

	}
	
	private static String columnLabel(Path<?> path) {
		return path.toString();
	}
	
	private static class CustomerExtractor implements ResultSetExtractor<Customer> {
		
		CustomerListExtractor customerListExtractor = new CustomerListExtractor(OneToManyResultSetExtractor.ExpectedResults.ONE_OR_NONE);
		
		@Override
		public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<Customer> customers = customerListExtractor.extractData(rs);
			return customers.size() > 0 ? customers.get(0) : null;					
		}
		
	}
	
	private static class CustomerListExtractor extends OneToManyResultSetExtractor<Customer, Address, Integer> {
		private static final QCustomer qCustomer = QCustomer.customer;
		
		private final QAddress qAddress = QAddress.address;
		
		public CustomerListExtractor() {
			super(new CustomerMapper(), new AddressMapper());
		}

		public CustomerListExtractor(ExpectedResults expectedResults) {
			super(new CustomerMapper(), new AddressMapper(), expectedResults);
		}
		
		@Override
		protected Integer mapPrimaryKey(ResultSet rs) throws SQLException {
			return rs.getInt(qCustomer.id.toString());
		}

		@Override
		protected Integer mapForeignKey(ResultSet rs) throws SQLException {
			String columnName = qAddress.customerCustomerIdFk.getLocalColumns().get(0).toString();
			if (rs.getObject(columnName) != null) {
				return rs.getInt(columnName);
			} else {
				return null;
			}
		}

		@Override
		protected void addChild(Customer root, Address child) {
			root.addAddress(child);
		}
	}
	
	private static class CustomerMapper implements RowMapper<Customer> {

		private final QCustomer qCustomer = QCustomer.customer;
		
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			
			customer.setId(rs.getLong(columnLabel(qCustomer.id)));
			customer.setFirstName(rs.getString(columnLabel(qCustomer.firstName)));
			customer.setLastName(rs.getString(columnLabel(qCustomer.lastName)));
			
			if (rs.getString(columnLabel(qCustomer.emailAddress)) != null) {
				customer.setEmailAddress(new EmailAddress(rs.getString(columnLabel(qCustomer.emailAddress))));
			}
			
			return customer;
		}
		
	}
	
	private static class AddressMapper implements RowMapper<Address> {
		private final QAddress qAddress = QAddress.address;

		@Override
		public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
			String street = rs.getString(columnLabel(qAddress.street));
			String city = rs.getString(columnLabel(qAddress.city));
			String country = rs.getString(columnLabel(qAddress.country));
			
			Address address = new Address(street, city, country);
			address.setId(rs.getLong(columnLabel(qAddress.id)));
			
			return address;
		}		
	}
}
