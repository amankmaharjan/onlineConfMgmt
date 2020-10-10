package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.Country;
import OnlineConfrenceMgmt.entity.Publisher;
import OnlineConfrenceMgmt.session.CountryFacade;
import OnlineConfrenceMgmt.session.PublisherFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/*
Controller class for Publisher which performs all the web operations
*/
@ManagedBean
@ViewScoped
public class PublisherController implements IMessages {

    @EJB
    private PublisherFacade publisherFacade; // Publisher EJB beans
    @EJB
    private CountryFacade countryFacade; // Publisher EJB beans

    private Publisher publisher = new Publisher(); // Publisher object to store publisher infomraiton
    private Country country = new Country();
    private List<Publisher> publisherList = new ArrayList<>(); //publisherlist object to hold list of publisher
    private boolean edit = false;
    private List<Country> countryList = new ArrayList<Country>();// country list object

    @PostConstruct
    public void init() {
        // load al the datas
        this.publisherList = publisherFacade.findAll();
        this.countryList = countryFacade.findAll();
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    //Publisher Getters       
    public Publisher getPublisher() {
        return publisher;
    }

    // Publisher Setter
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    // CusotmerList getter
    public List<Publisher> getPublisherList() {
        return publisherList;
    }

    // Publisher List setter
    public void setPublisherList(List<Publisher> publisherList) {
        this.publisherList = publisherList;
    }

    // create Publisher
    public void create() {

        /* check if edit  is enable or disable */
        if (!edit) {
            this.publisher.setId(null);
            this.publisher.setCountry(countryFacade.find(this.country.getId()));
            List<String> rolesList = new ArrayList<>();
            rolesList.add(OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.roles[2]);
            publisher.setRoles(rolesList);
            publisherFacade.create(publisher);
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.CREATE_MESSAGE, this.publisher.getId().toString());
            resetFields();
        } else {
            /* create fields */
            Publisher updatedPublisher = publisherFacade.find(publisher.getId());
            this.publisher.setCountry(country);
            updatedPublisher.setId(publisher.getId());
            updatedPublisher.setName(publisher.getName());
            updatedPublisher.setEmail(publisher.getEmail());
            updatedPublisher.setPassword(publisher.getPassword());
            updatedPublisher.setCountry(countryFacade.find(country.getId()));

            publisherFacade.edit(updatedPublisher);
            edit = false;

            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, this.publisher.getId().toString());

            /* reset fields */
            resetFields();

        }
        /* reload the data */
        init();

    }

    /* udpate the publisher */
    public void update(Publisher publisher) {
        this.publisher = publisher;
        this.edit = true;
    }

    /* delete the publisher */
    public void delete(Publisher publisher) {
        publisherFacade.remove(publisher);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE, publisher.getId().toString());
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
        this.publisher.setId(null);
        this.publisher.setName(null);
        this.publisher.setEmail(null);
        this.publisher.setPassword(null);
        this.publisher.setCountry(null);
    }
    /* get country */

    public Country getCountry() {
        return country;
    }
    /* set country */

    public void setCountry(Country country) {
        this.country = country;
    }

}
