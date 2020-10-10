/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * This interface shows the message in the browser
 */
public interface IMessages {

    /* show messages */
    public default void showMessages(FacesMessage.Severity severity, String message, String detail) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, new FacesMessage(severity, message, detail));
    }
}
