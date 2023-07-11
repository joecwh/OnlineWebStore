package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Shipping.class)
public class Shipping_ { 

    public static volatile SingularAttribute<Shipping, String> receivername;
    public static volatile SingularAttribute<Shipping, String> shippingaddress;
    public static volatile SingularAttribute<Shipping, Date> createdat;
    public static volatile SingularAttribute<Shipping, String> shippingid;
    public static volatile SingularAttribute<Shipping, Date> expectedreachdate;
    public static volatile SingularAttribute<Shipping, Date> modifiedat;
    public static volatile SingularAttribute<Shipping, String> contact;
    public static volatile SingularAttribute<Shipping, String> email;
    public static volatile SingularAttribute<Shipping, String> status;

}