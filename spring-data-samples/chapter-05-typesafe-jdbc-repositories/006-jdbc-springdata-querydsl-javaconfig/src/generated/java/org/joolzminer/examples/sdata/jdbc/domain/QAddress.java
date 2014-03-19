package org.joolzminer.examples.sdata.jdbc.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAddress is a Querydsl query type for QAddress
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QAddress extends com.mysema.query.sql.RelationalPathBase<QAddress> {

    private static final long serialVersionUID = -595201197;

    public static final QAddress address = new QAddress("address");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final NumberPath<Long> customerId = createNumber("customer_id", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath street = createString("street");

    public final com.mysema.query.sql.PrimaryKey<QAddress> primary = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QCustomer> customerCustomerIdFk = createForeignKey(customerId, "id");

    public QAddress(String variable) {
        super(QAddress.class, forVariable(variable), "null", "address");
    }

    public QAddress(Path<? extends QAddress> path) {
        super(path.getType(), path.getMetadata(), "null", "address");
    }

    public QAddress(PathMetadata<?> metadata) {
        super(QAddress.class, metadata, "null", "address");
    }

}

