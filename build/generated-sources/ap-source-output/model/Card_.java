package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Card.class)
public class Card_ { 

    public static volatile SingularAttribute<Card, Date> expirydate;
    public static volatile SingularAttribute<Card, Integer> cvc;
    public static volatile SingularAttribute<Card, String> ownername;
    public static volatile SingularAttribute<Card, String> cardid;
    public static volatile SingularAttribute<Card, String> cardnumber;
    public static volatile SingularAttribute<Card, String> userid;

}