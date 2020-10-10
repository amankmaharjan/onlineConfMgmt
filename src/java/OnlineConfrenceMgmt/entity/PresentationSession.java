/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author User
 */
@Entity
public class PresentationSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;   /* Presentation sesssion   id*/

    private Date date;   /* Presentation sesssion  date  */

    private String time;   /* Presentation sesssion   time*/

    private Country country;   /* Presentation sesssion   country*/

    private String Topic;   /* Presentation sesssion  topic */

    private String status;   /* Presentation sesssion  status  */

    private String image;   /* Presentation sesssion  image */

    private String descriptions;   /* Presentation sesssion descriptions   */

    @OneToMany   /* Presentation sesssion   */

    private List<Presentation> presentationList;   /* Presentation sesssion  presentationList  */

    /* Getter and setter */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String Topic) {
        this.Topic = Topic;
    }

    @Override
    public String toString() {
        return "PresentationSession{" + "id=" + id + ", date=" + date + ", time=" + time + ", country=" + country + ", Topic=" + Topic + ", presentationList=" + presentationList + '}';
    }

    public List<Presentation> getPresentationList() {
        return presentationList;
    }

    public void setPresentationList(List<Presentation> presentationList) {
        this.presentationList = presentationList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

}
