
package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.Topic;
import OnlineConfrenceMgmt.session.TopicFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/*
Controller class for Topic which performs all the web operations
*/
@ManagedBean
@ViewScoped
public class TopicController implements IMessages{

    @EJB
    private TopicFacade topicFacade; // Topic EJB beans

    private Topic topic = new Topic(); // Topic object to store topic infomraiton
    private List<Topic> topicList = new ArrayList<>(); //topiclist object to hold list of topic
    private boolean edit = false;

    @PostConstruct
    public void init() {
        this.topicList = topicFacade.findAll();
    }

    //Topic Getters       
    public Topic getTopic() {
        return topic;
    }

    // Topic Setter
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    // CusotmerList getter
    public List<Topic> getTopicList() {
        return topicList;
    }

    // Topic List setter
    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    // create Topic
    public void create() {

        /* check if edit  is enable or disable */
        if (!edit) {
            this.topic.setId(null);
            topicFacade.create(topic);
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.CREATE_MESSAGE, this.topic.getTopicId());
            resetFields();
        } else {
            /* create fields */
            Topic updatedTopic = topicFacade.find(topic.getId());
            updatedTopic.setTopicId(topic.getTopicId());
            updatedTopic.setTopicTitle(topic.getTopicTitle());
            topicFacade.edit(updatedTopic);
            edit = false;

            /* reset fields */
            resetFields();
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, this.topic.getTopicId());

        }
        /* reload the data */
        init();

    }

    /* udpate the topic */
    public void update(Topic topic) {
        this.topic = topic;
        this.edit = true;
    }

    /* delete the topic */
    public void delete(Topic topic) {
        topicFacade.remove(topic);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE, this.topic.getTopicId());
        init();
    }
    /* edit */
    public boolean getEdit() {
        return edit;
    }

    /*  edit */
    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    /* cancel button */
    public void cancel() {
        this.edit = false;
        resetFields();
        this.init();
    }
    
    /* reset fields */
    public void resetFields() {
        this.topic.setId(null);
        this.topic.setTopicId(null);
        this.topic.setTopicTitle(null);
    }
   

}
