/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * Entity class for storing the presentation information
 */
@Entity
public class Presentation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; /*   presentation  id*/

    private String title; /*   presentation title */

    private String description; /*   presentation description */

    private String status; /*   presentation status*/

    private boolean isAllocated; /*   presentation isAllocated */

    @ManyToOne
    private Topic topic; /*   presentation  topic*/

    @OneToOne
    private Publisher publisher; /*   presentation publisher*/

    @ManyToOne
    private Participant participant; /*   presentation participant*/

    private String image; /*   presentation image*/

// Getter and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsAllocated() {
        return isAllocated;
    }

    public void setIsAllocated(boolean isAllocated) {
        this.isAllocated = isAllocated;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String toString() {
        return "Presentation{" + "id=" + id + ", title=" + title + ", description=" + description + ", status=" + status + ", isAllocated=" + isAllocated + ", topic=" + topic + ", publisher=" + publisher + ", participant=" + participant + ", image=" + image + '}';
    }

}
