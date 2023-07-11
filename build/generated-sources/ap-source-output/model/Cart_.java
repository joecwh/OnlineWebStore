package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Double> total;
    public static volatile SingularAttribute<Cart, Integer> quantity;
    public static volatile SingularAttribute<Cart, Boolean> ispaid;
    public static volatile SingularAttribute<Cart, String> productid;
    public static volatile SingularAttribute<Cart, String> cartid;
    public static volatile SingularAttribute<Cart, String> userid;

}