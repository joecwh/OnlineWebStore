package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Orders.class)
public class Orders_ { 

    public static volatile SingularAttribute<Orders, Date> createdat;
    public static volatile SingularAttribute<Orders, String> shippingid;
    public static volatile SingularAttribute<Orders, String> orderid;
    public static volatile SingularAttribute<Orders, Date> modifiedat;
    public static volatile SingularAttribute<Orders, String> userid;
    public static volatile SingularAttribute<Orders, String> transactionid;
    public static volatile SingularAttribute<Orders, String> status;

}