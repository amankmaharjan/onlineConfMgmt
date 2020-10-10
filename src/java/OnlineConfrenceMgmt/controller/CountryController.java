package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.Country;
import OnlineConfrenceMgmt.session.CountryFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/*
Controller class for country
*/
@ManagedBean
@ViewScoped
public class CountryController implements IMessages {

    @EJB
    private CountryFacade countryFacade; // Country EJB beans

    private Country country = new Country(); // Country object to store country infomraiton
    private List<Country> countryList = new ArrayList<>(); //countrylist object to hold list of country
    private boolean edit = false;

    @PostConstruct
    public void init() {
        /* Get all country list */
        this.countryList = countryFacade.findAll();
    }

    //Country Getters       
    public Country getCountry() {
        return country;
    }

    // Country Setter
    public void setCountry(Country country) {
        this.country = country;
    }

    // CusotmerList getter
    public List<Country> getCountryList() {
        return countryList;
    }

    // Country List setter
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    // create Country
    public void create() {

        /* check if edit  is enable or disable */
        if (!edit) {
            this.country.setId(null);
            countryFacade.create(country);
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.CREATE_MESSAGE, this.country.getId().toString());
            resetFields();
        } else {
            /* create fields */
            Country updatedCountry = countryFacade.find(country.getId());
            updatedCountry.setId(country.getId());
            updatedCountry.setName(country.getName());
            updatedCountry.setZone(country.getZone());
            countryFacade.edit(updatedCountry);
            edit = false;

            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, this.country.getId().toString());

            /* reset fields */
            resetFields();

        }
        /* reload the data */
        init();

    }

    /* udpate the country */
    public void update(Country country) {
        this.country = country;
        this.edit = true;
    }

    /* delete the country */
    public void delete(Country country) {
        countryFacade.remove(country);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE,country.getId().toString());
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
        this.country.setId(null);
        this.country.setName(null);
        this.country.setZone(null);
        this.country.setGmtOffset(null);
    }

}
