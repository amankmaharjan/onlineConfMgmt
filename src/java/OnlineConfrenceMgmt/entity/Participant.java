/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * Entity class for storing the participant  records
 */
@Entity
@DiscriminatorValue("PA")
public class Participant extends User{

   
    /* participant country */
    @OneToOne
    private Country country;

    /* Getter and setter */
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
