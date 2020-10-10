/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.entity;

import OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant;
import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * Entity class for storing user information
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //  primary key
    private String name; // name field
    private String address; // address field
    private int phoneNumber; // phone number field
    private String email;// email field
    private String password; // password field
    private List<String> roles; // roles

    public User() {
    }

    public User(String name, String password, List<String> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }



    public boolean isAdmin() {
        if (roles != null) {
            if (this.roles.contains(OnlineConfrenceMessagesConstant.roles[0])) {
                return true;
            }
        }
        return false;
    }

    public boolean isParticipant() {
        if (roles != null) {
            if (this.roles.contains(OnlineConfrenceMessagesConstant.roles[1])) {
                return true;
            }
        }
        return false;
    }

    public boolean isPublisher() {
        if (roles != null) {
            if (this.roles.contains(OnlineConfrenceMessagesConstant.roles[2])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", address=" + address + ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password + ", roles=" + roles + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
