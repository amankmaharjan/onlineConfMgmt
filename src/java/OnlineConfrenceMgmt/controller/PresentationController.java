package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.Country;
import OnlineConfrenceMgmt.entity.Participant;
import OnlineConfrenceMgmt.entity.Presentation;
import OnlineConfrenceMgmt.entity.Publisher;
import OnlineConfrenceMgmt.entity.Topic;
import OnlineConfrenceMgmt.session.ParticipantFacade;
import OnlineConfrenceMgmt.session.PresentationFacade;
import OnlineConfrenceMgmt.session.PublisherFacade;
import OnlineConfrenceMgmt.session.TopicFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

/*
Controller class for Presentaiton which performs all the web operations
*/
@ManagedBean
@ViewScoped
public class PresentationController implements IMessages {

    @EJB
    private PresentationFacade presentationFacade; // Presentation EJB beans
    @EJB
    private TopicFacade topicFacade; // Presentation EJB beans
    @EJB
    private PublisherFacade publisherFacade; // Presentation EJB beans
    @EJB
    private ParticipantFacade participantFacade; // Presentation EJB beans

    //objects
    private Presentation presentation = new Presentation(); // Presentation object to store presentation infomraiton
    private Country country = new Country();
    private Topic topic = new Topic();
    private Publisher publisher = new Publisher();
    private Participant participant = new Participant();
    private Part presentationImgFile;

    //list
    private List<Presentation> presentationList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Topic> topicList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Publisher> publisherList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Country> countryList = new ArrayList<>(); // country LIst object to hold list of coutnry
    private List<Participant> participantList = new ArrayList<>();// participantList object to hold list of praticipants
    private String fileLocation = "C:\\Users\\User\\OneDrive\\MIT\\term4\\project\\OnlineConfrenceMgmt\\web\\upload\\"; /* Image file location */
    private String fliebase = "//upload//"; /* folder name */

    private boolean edit = false;

    @PostConstruct
    public void init() {
        /*load all the datas */
        this.presentationList = presentationFacade.findAll();
        this.topicList = topicFacade.findAll();
        this.publisherList = publisherFacade.findAll();
        this.participantList = participantFacade.findAll();
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    //Presentation Getters       
    public Presentation getPresentation() {
        return presentation;
    }

    // Presentation Setter
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    // CusotmerList getter
    public List<Presentation> getPresentationList() {
        return presentationList;
    }

    // Presentation List setter
    public void setPresentationList(List<Presentation> presentationList) {
        this.presentationList = presentationList;
    }

    // create Presentation
    public void create() throws IOException {

        /* check if edit  is enable or disable */
        if (!edit) {
            this.presentation.setId(null);
            this.presentation.setTopic(topicFacade.find(topic.getId()));
            this.presentation.setPublisher(publisherFacade.find(publisher.getId()));
            /* file upload */
            String fileName = this.presentationImgFile.getSubmittedFileName();
            saveFile(presentationImgFile, fileName);
            this.presentation.setImage(fileName);
            this.presentation.setParticipant(participantFacade.find(participant.getId()));

            presentationFacade.create(presentation);
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.CREATE_MESSAGE, this.presentation.getId().toString());
            resetFields();
        } else {
            /* create fields */
            Presentation updatedPresentation = presentationFacade.find(presentation.getId());
            updatedPresentation.setId(presentation.getId());
            updatedPresentation.setTitle(presentation.getTitle());
            updatedPresentation.setStatus(presentation.getStatus());
            updatedPresentation.setDescription(presentation.getDescription());
            updatedPresentation.setPublisher(publisherFacade.find(publisher.getId()));
            updatedPresentation.setTopic(topicFacade.find(topic.getId()));
            updatedPresentation.setParticipant(participantFacade.find(participant.getId()));

            /* file upload */
            String fileName = this.presentationImgFile.getSubmittedFileName();
            if (!fileName.equals("")) {
                saveFile(presentationImgFile, fileName);
                updatedPresentation.setImage(fileName);
            }
            presentationFacade.edit(updatedPresentation);
            edit = false;

            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, this.presentation.getId().toString());

            /* reset fields */
            resetFields();

        }
        /* reload the data */
        init();

    }

    /* udpate the presentation */
    public void update(Presentation presentation) {
        this.presentation = presentation;
        this.edit = true;
    }

    /* delete the presentation */
    public void delete(Presentation presentation) {
        presentationFacade.remove(presentation);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE, presentation.getId().toString());
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
        this.presentation.setId(null);
        this.presentation.setStatus(null);
        this.presentation.setTitle(null);
        this.presentation.setDescription(null);
        this.presentation.setPublisher(null);
        this.presentation.setTopic(null);
        this.presentation.setParticipant(null);
    }

    // Getters and setters */
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<Publisher> getPublisherList() {
        return publisherList;
    }

    public void setPublisherList(List<Publisher> publisherList) {
        this.publisherList = publisherList;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public Part getPresentationImgFile() {
        return presentationImgFile;
    }

    public void setPresentationImgFile(Part presentationImgFile) {
        this.presentationImgFile = presentationImgFile;
    }
    /* Method that saves the image file */

    private void saveFile(Part presentationImgFile, String fileName) throws IOException {
        try (InputStream input = presentationImgFile.getInputStream()) {
            Files.copy(input, new File(fileLocation, fileName).toPath());
            System.out.println("Image copied");
        } catch (IOException e) {
            // Show faces message?
        }
    }

    /* Getter and setter */
    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFliebase() {
        return fliebase;
    }

    public void setFliebase(String fliebase) {
        this.fliebase = fliebase;
    }

}
