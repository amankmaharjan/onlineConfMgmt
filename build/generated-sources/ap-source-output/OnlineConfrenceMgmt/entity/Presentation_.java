package OnlineConfrenceMgmt.entity;

import OnlineConfrenceMgmt.entity.Participant;
import OnlineConfrenceMgmt.entity.Publisher;
import OnlineConfrenceMgmt.entity.Topic;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2020-09-30T20:53:38")
@StaticMetamodel(Presentation.class)
public class Presentation_ { 

    public static volatile SingularAttribute<Presentation, String> image;
    public static volatile SingularAttribute<Presentation, String> description;
    public static volatile SingularAttribute<Presentation, Topic> topic;
    public static volatile SingularAttribute<Presentation, Publisher> publisher;
    public static volatile SingularAttribute<Presentation, Long> id;
    public static volatile SingularAttribute<Presentation, Boolean> isAllocated;
    public static volatile SingularAttribute<Presentation, String> title;
    public static volatile SingularAttribute<Presentation, Participant> participant;
    public static volatile SingularAttribute<Presentation, String> status;

}