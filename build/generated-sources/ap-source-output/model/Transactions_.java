package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Transactions.class)
public class Transactions_ { 

    public static volatile SingularAttribute<Transactions, Date> createdat;
    public static volatile SingularAttribute<Transactions, Double> total;
    public static volatile SingularAttribute<Transactions, Double> shippingfee;
    public static volatile SingularAttribute<Transactions, String> paymentid;
    public static volatile SingularAttribute<Transactions, Double> subtotal;
    public static volatile SingularAttribute<Transactions, String> discountcode;
    public static volatile SingularAttribute<Transactions, String> cartid;
    public static volatile SingularAttribute<Transactions, Double> taxes;
    public static volatile SingularAttribute<Transactions, String> userid;
    public static volatile SingularAttribute<Transactions, String> transactionid;

}