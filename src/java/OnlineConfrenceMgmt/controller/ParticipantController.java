package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.Country;
import OnlineConfrenceMgmt.entity.Participant;
import OnlineConfrenceMgmt.session.CountryFacade;
import OnlineConfrenceMgmt.session.ParticipantFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/*
Controller class for Participant which performs all the web operations
*/
@ManagedBean
@ViewScoped
public class ParticipantController implements IMessages {

    @EJB
    private ParticipantFacade participantFacade; // Participant EJB beans
    @EJB
    private CountryFacade countryFacade; // Participant EJB beans

    private Participant participant = new Participant(); // Participant object to store participant infomraiton
    private Country country = new Country();
    private List<Participant> participantList = new ArrayList<>(); //participantlist object to hold list of participant
    private boolean edit = false;
    private List<Country> countryList = new ArrayList<Country>();

    @PostConstruct
    public void init() {
        this.participantList = participantFacade.findAll();
        this.countryList = countryFacade.findAll();
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    //Participant Getters       
    public Participant getParticipant() {
        return participant;
    }

    // Participant Setter
    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    // CusotmerList getter
    public List<Participant> getParticipantList() {
        return participantList;
    }

    // Participant List setter
    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    // create Participant
    public void create() {

        /* check if edit  is enable or disable */
        if (!edit) {
            this.participant.setId(null);
            this.participant.setCountry(countryFacade.find(this.country.getId()));
            List<String> rolesList = new ArrayList<>();
            rolesList.add(OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.roles[1]);
            this.participant.setRoles(rolesList);
            participantFacade.create(participant);
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.CREATE_MESSAGE, this.participant.getId().toString());
            resetFields();
        } else {
            /* create fields */
            Participant updatedParticipant = participantFacade.find(participant.getId());
            this.participant.setCountry(country);
            updatedParticipant.setId(participant.getId());
            updatedParticipant.setName(participant.getName());
            updatedParticipant.setEmail(participant.getEmail());
            updatedParticipant.setPassword(participant.getPassword());
            updatedParticipant.setCountry(countryFacade.find(country.getId()));

            participantFacade.edit(updatedParticipant);
            edit = false;

            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, this.participant.getId().toString());

            /* reset fields */
            resetFields();

        }
        /* reload the data */
        init();

    }

    /* udpate the participant */
    public void update(Participant participant) {
        this.participant = participant;
        this.edit = true;
    }

    /* delete the participant */
    public void delete(Participant participant) {
        participantFacade.remove(participant);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE, participant.getId().toString());
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
        this.participant.setId(null);
        this.participant.setName(null);
        this.participant.setEmail(null);
        this.participant.setPassword(null);
        this.participant.setCountry(null);
    }

    // country getter
    public Country getCountry() {
        return country;
    }
// coutnry setter

    public void setCountry(Country country) {
        this.country = country;
    }

}
