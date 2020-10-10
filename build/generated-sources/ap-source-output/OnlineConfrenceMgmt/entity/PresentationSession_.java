package OnlineConfrenceMgmt.entity;

import OnlineConfrenceMgmt.entity.Country;
import OnlineConfrenceMgmt.entity.Presentation;
import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2020-09-30T20:53:39")
@StaticMetamodel(PresentationSession.class)
public class PresentationSession_ { 

    public static volatile SingularAttribute<PresentationSession, Date> date;
    public static volatile SingularAttribute<PresentationSession, Country> country;
    public static volatile SingularAttribute<PresentationSession, String> image;
    public static volatile SingularAttribute<PresentationSession, Long> id;
    public static volatile SingularAttribute<PresentationSession, String> time;
    public static volatile SingularAttribute<PresentationSession, String> Topic;
    public static volatile SingularAttribute<PresentationSession, String> descriptions;
    public static volatile ListAttribute<PresentationSession, Presentation> presentationList;
    public static volatile SingularAttribute<PresentationSession, String> status;

}