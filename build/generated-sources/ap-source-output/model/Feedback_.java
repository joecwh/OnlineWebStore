package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Feedback.class)
public class Feedback_ { 

    public static volatile SingularAttribute<Feedback, Date> createdat;
    public static volatile SingularAttribute<Feedback, String> subject;
    public static volatile SingularAttribute<Feedback, String> name;
    public static volatile SingularAttribute<Feedback, String> id;
    public static volatile SingularAttribute<Feedback, String> message;
    public static volatile SingularAttribute<Feedback, String> email;
    public static volatile SingularAttribute<Feedback, String> status;

}