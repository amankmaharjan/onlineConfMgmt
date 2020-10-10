/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Class that stores the constant of the project
 */
public class OnlineConfrenceMessagesConstant {

    //Messages constants
    public static String UPDATE_MESSAGE = "Successfully updated:";
    public static String DELETE_MESSAGE = "Successfully deleted:";
    public static String CREATE_MESSAGE = "Successfully created:";
    public static String USERNAME_PASSWORD_ERROR = "Wrong username and Password";

    // User Roles
    public static String[] roles = {"ADMIN", "PARTICIPANT", "PUBLISHER"};
    public static List<String> userRoles = new ArrayList<>(Arrays.asList(roles));

}
