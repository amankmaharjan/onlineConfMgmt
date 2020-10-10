package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.Country;
import OnlineConfrenceMgmt.entity.Presentation;
import OnlineConfrenceMgmt.entity.PresentationSession;
import OnlineConfrenceMgmt.entity.Publisher;
import OnlineConfrenceMgmt.entity.Topic;
import OnlineConfrenceMgmt.session.PresentationFacade;
import OnlineConfrenceMgmt.session.PresentationSessionFacade;
import OnlineConfrenceMgmt.session.PublisherFacade;
import OnlineConfrenceMgmt.session.TopicFacade;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
public class PresentationSessionController implements IMessages {

    @EJB
    private PresentationFacade presentationFacade; // Presentation EJB beans
    @EJB
    private TopicFacade topicFacade; // Presentation EJB beans
    @EJB
    private PublisherFacade publisherFacade; // Presentation EJB beans
    @EJB
    private PresentationSessionFacade presentationSessionFacade;
    //objects
    private Presentation presentation = new Presentation(); // Presentation object to store presentation infomraiton
    private Country country = new Country();
    private Topic topic = new Topic();
    private Publisher publisher = new Publisher();

    private PresentationSession presentationSession = new PresentationSession(); // PResentaiton session object to store the presentaion session
    private String date; // date object to store date
    private Part imageFile; //Image file boject for storing the image file
    private String fileLocation = "C:\\Users\\User\\OneDrive\\MIT\\term4\\project\\OnlineConfrenceMgmt\\web\\upload\\"; // file location
    private String fliebase = "//upload//"; // file upload folder

    public PresentationSession getPresentationSession() {
        return presentationSession;
    }

    public void setPresentationSession(PresentationSession presentationSession) {
        this.presentationSession = presentationSession;
    }

    //list
    private List<PresentationSession> presentationSessionList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Presentation> presentationList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Topic> topicList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Publisher> publisherList = new ArrayList<>(); //presentationlist object to hold list of presentation
    private List<Country> countryList = new ArrayList<>(); //countrylist object for storing countris
    private boolean edit = false;

    @PostConstruct
    public void init() {
        // load all the  datas
        this.presentationList = presentationFacade.findAll();
        this.topicList = topicFacade.findAll();
        this.publisherList = publisherFacade.findAll();
        this.presentationSessionList = presentationSessionFacade.findAll();
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
    public void create() throws ParseException {

        /* check if edit  is enable or disable */
        if (!edit) {
//          
            //presetnation sessson
            Map<String, Map<String, List<Presentation>>> gmtTopicMap = generatePresentationSessionList();

            //save the presentation session
            for (Map.Entry<String, Map<String, List<Presentation>>> gmtTopic : gmtTopicMap.entrySet()) {
                /* get gmt*/
                String gmt = gmtTopic.getKey();
                //  get topic map
                Map<String, List<Presentation>> topicMap = gmtTopic.getValue();

                for (Map.Entry<String, List<Presentation>> topicPresentation : topicMap.entrySet()) {
                    String newTopic = topicPresentation.getKey();
                    List<Presentation> newpresentationList = topicPresentation.getValue();
                    PresentationSession newpresentationSession = new PresentationSession();
                    newpresentationSession.setTopic(newTopic);
                    newpresentationSession.setPresentationList(newpresentationList);
                    newpresentationSession.setCountry(newpresentationList.get(0).getParticipant().getCountry());
                    newpresentationSession.setStatus("IN ACTIVE");

                    //crfeate presentation
                    presentationSessionFacade.create(newpresentationSession);
                }

            }
        } else {
            /* create fields */
            PresentationSession updatedPresentationSession = presentationSessionFacade.find(presentationSession.getId());
            updatedPresentationSession.setStatus(presentationSession.getStatus());
            updatedPresentationSession.setTime(presentationSession.getTime());
            updatedPresentationSession.setDate(new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(date).getTime()));
            updatedPresentationSession.setDescriptions(presentationSession.getDescriptions());
            String fileName = this.imageFile.getSubmittedFileName();
            if (!fileName.equals("")) {
                saveFile(imageFile, fileName);
                updatedPresentationSession.setImage(fileName);
            }
            presentationSessionFacade.edit(updatedPresentationSession);
            edit = false;

            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, "Record updated");

            /* reset fields */
            resetFields();

        }
        /* reload the data */
        init();

    }

    /* udpate the presentation */
    public void update(PresentationSession presentationSession) {
        this.presentationSession = presentationSession;
        this.edit = true;
    }

    /* delete the presentation */
    public void delete(PresentationSession presentationSession) {
        presentationSessionFacade.remove(presentationSession);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE, "Record deleted");
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
        this.presentation.setDescription(null);
        this.presentation.setPublisher(null);
        this.presentation.setTopic(null);
    }

    // Gettter and setters */
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

    public List<PresentationSession> getPresentationSessionList() {
        return presentationSessionList;
    }

    public void setPresentationSessionList(List<PresentationSession> presentationSessionList) {
        this.presentationSessionList = presentationSessionList;
    }

    /* This method generatie the presentaitons session lists */
    private Map<String, Map<String, List<Presentation>>> generatePresentationSessionList() {
        /* list of objects */
        Map<String, Map<String, List<Presentation>>> gmtTopicMap = new HashMap<>();
        Set<String> gmtList = new HashSet<>();
        Set<String> topicList = new HashSet<>();

        /* Get all gmt list */
        gmtList = getDistinctGMTList();
        topicList = getDistinctTopicList();
        /* need to change criteria */
        List<Presentation> presentationList = presentationFacade.findByStatusActive();
        // get all topic list
        for (String gmt : gmtList) {
            //mapping of each topic
            Map<String, List<Presentation>> topicMap = new HashMap<>();

            for (String topic : topicList) {
                /* temporary list for storing the presentation */
                List<Presentation> tempPresentationList = new ArrayList<>();
                for (Presentation presentation : presentationList) {
                    /* comparing the gmt and topic*/
                    if (presentation.getParticipant().getCountry().getGmtOffset().equals(gmt) && presentation.getTopic().getTopicTitle().equals(topic)) {
                        /* putting on the list */
                        tempPresentationList.add(presentation);
                    }
                }
                //mapping of each topic
                if (tempPresentationList.size() > 0) {
                    topicMap.put(topic, tempPresentationList);
                }
            }
            //mapping of topic with gmt
            if (topicMap.size() > 0) {
                gmtTopicMap.put(gmt, topicMap);
            }
        }
        return gmtTopicMap;

    }

    /* get distinct gmtList */
    private Set<String> getDistinctGMTList() {
        Set<String> gmtList = new HashSet<>();

        System.out.println("inside gmtList");
        for (String gmt : presentationFacade.getGMTList()) {
            gmtList.add(gmt);
        }
        return gmtList;
    }
    /* get distinct topic List */

    private Set<String> getDistinctTopicList() {
        Set<String> topicList = new HashSet<>();

        System.out.println("inside topicList");
        for (String gmt : presentationFacade.getTopicList()) {
            topicList.add(gmt);
        }
        return topicList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

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
    /* save image file */

    private void saveFile(Part imageFile, String fileName) {
        try (InputStream input = imageFile.getInputStream()) {
            Files.copy(input, new File(fileLocation, fileName).toPath());
        } catch (IOException e) {
            // Show faces message?
        }
    }
/* go to presentation details */
    public String gotoPresentationDetails(PresentationSession presentationSession) {
        this.presentationSession = getPresentaitonSessionDetailById(presentationSession);
        return "presentationSessionDetail";
    }
/* get presentation details by id */
    private PresentationSession getPresentaitonSessionDetailById(PresentationSession presentationSession) {
        return presentationSessionFacade.find(presentationSession.getId());
    }
    /* retruns all the active presentation list */
    public List<PresentationSession> getActivePresentationSessionList() {
        return presentationSessionFacade.getActiveSession();
    }
/* rmoves all presentaion session */
    public void removeAll() {
        presentationSessionFacade.removeAll();
        init();
    }
}
