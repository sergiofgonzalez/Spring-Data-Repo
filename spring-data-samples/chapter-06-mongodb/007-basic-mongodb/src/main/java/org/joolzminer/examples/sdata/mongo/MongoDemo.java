package org.joolzminer.examples.sdata.mongo;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDemo {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDemo.class);
	
	/**
	 * Inserts the following entry in the MongoDB database:
	 *  { 	firstname : "Sergio",
	 *  	lastname  : "F. Gonzalez",
	 *  	addresses : [ { city : "Madrid", street : "Arturo Soria" } ]
	 *  }
	 */
	public static void main(String[] args) throws UnknownHostException {
		
		System.out.println("*** Connecting to local MongoDB and inserting a Document: ");
		
		Mongo mongo = new Mongo();
		DB database = mongo.getDB("local");
		DBCollection customers = database.getCollection("customers");
				
		DBObject address = new BasicDBObject("city", "Madrid");
		address.put("street", "Arturo Soria");
		
		BasicDBList addresses = new BasicDBList();
		addresses.add(address);
		
		DBObject customer = new BasicDBObject("firstname", "Sergio");
		customer.put("lastname", "F. Gonzalez");
		customer.put("addresses", addresses);
		
		customers.insert(customer);
		
		System.out.println("done! - run db.customers.find() from the shell to check");
	}
}
