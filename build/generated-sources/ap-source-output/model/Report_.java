package model;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-07-11T20:43:51")
@StaticMetamodel(Report.class)
public class Report_ { 

    public static volatile SingularAttribute<Report, Date> createdat;
    public static volatile SingularAttribute<Report, String> reportid;
    public static volatile SingularAttribute<Report, String> reportname;
    public static volatile SingularAttribute<Report, Serializable> reportpdf;

}