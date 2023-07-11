package model;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, Integer> quantity;
    public static volatile SingularAttribute<Product, String> productid;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, Serializable> productimg;
    public static volatile SingularAttribute<Product, String> productname;
    public static volatile SingularAttribute<Product, String> productdesc;

}