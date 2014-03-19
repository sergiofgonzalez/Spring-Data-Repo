package org.joolzminer.examples.sdata.jdbc.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QCustomer is a Querydsl query type for QCustomer
 */
@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QCustomer extends com.mysema.query.sql.RelationalPathBase<QCustomer> {

    private static final long serialVersionUID = 553522271;

    public static final QCustomer customer = new QCustomer("customer");

    public final StringPath emailAddress = createString("email_address");

    public final StringPath firstName = createString("first_name");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("last_name");

    public final com.mysema.query.sql.PrimaryKey<QCustomer> primary = createPrimaryKey(id);

    public final com.mysema.query.sql.ForeignKey<QAddress> _customerCustomerIdFk = createInvForeignKey(id, "customer_id");

    public QCustomer(String variable) {
        super(QCustomer.class, forVariable(variable), "null", "customer");
    }

    public QCustomer(Path<? extends QCustomer> path) {
        super(path.getType(), path.getMetadata(), "null", "customer");
    }

    public QCustomer(PathMetadata<?> metadata) {
        super(QCustomer.class, metadata, "null", "customer");
    }

}

