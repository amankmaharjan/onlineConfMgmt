package OnlineConfrenceMgmt.controller;

import OnlineConfrenceMgmt.common.IMessages;
import OnlineConfrenceMgmt.entity.User;
import OnlineConfrenceMgmt.session.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.security.auth.login.*;

/*
Controller class for User which performs all the web operations
*/
@ManagedBean
@SessionScoped
public class UserController implements IMessages {

    @EJB
    private UserFacade userFacade; // User EJB beans

    private User user = new User(); // User object to store user infomraiton
    private List<User> userList = new ArrayList<>(); //userlist object to hold list of user
    private boolean edit = false;
    LoginContext loginContext;

    @PostConstruct
    public void init() {
        this.userList = userFacade.findAll();
    }

    //User Getter       
    public User getUser() {
        return user;
    }

    // User Setter
    public void setUser(User user) {
        this.user = user;
    }

    // CusotmerList getter
    public List<User> getUserList() {
        return userList;
    }

    // User List setter
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    // create User
    public void create() {

        /* check if edit  is enable or disable */
        if (!edit) {
            this.user.setId(null);
            List<String> rolesList = new ArrayList<>();
            rolesList.add(OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.roles[0]);
            this.user.setRoles(rolesList);
            userFacade.create(user);
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.CREATE_MESSAGE, this.user.getAddress());
            resetFields();
        } else {
            /* create fields */
            User updatedUser = userFacade.find(user.getId());

            userFacade.edit(updatedUser);
            edit = false;

            /* reset fields */
            resetFields();
            showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.UPDATE_MESSAGE, this.user.getAddress());

        }
        /* reload the data */
        init();

    }

    /* udpate the user */
    public void update(User user) {
        this.user = user;
        this.edit = true;
    }

    /* delete the user */
    public void delete(User user) {
        userFacade.remove(user);
        showMessages(FacesMessage.SEVERITY_INFO, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.DELETE_MESSAGE, this.user.getAddress());
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

    }
//Method that cheks the user crdential
    public String login() throws LoginException {

        // check username and password
        user = userFacade.getUserByIdPassword(user.getName(), user.getPassword());
        // determine the role
        if (user != null) {
            if (checkRoles(user)) {
                return "adminPage";

            } else {
                System.out.println("Access Deny");
                return "errorPage";
            }
        }
        showMessages(FacesMessage.SEVERITY_ERROR, OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.USERNAME_PASSWORD_ERROR, "Invalid username or password");
        user = new User();
        return "loginPage";
    }

    // check if the user has roles or not
    public boolean checkRoles(User user) {
        for (String role : user.getRoles()) {
            if (OnlineConfrenceMgmt.common.OnlineConfrenceMessagesConstant.userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }
// logout the user

    public String logOut() throws LoginException {
        user = new User();
        return "homePage";

    }
}
