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
 * Entity class for publisher
 */
@Entity
@DiscriminatorValue("PB")
public class Publisher extends User {
    
    @OneToOne
    private Country country; /* Publisher country */

    /* Getter and setter */
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
}
