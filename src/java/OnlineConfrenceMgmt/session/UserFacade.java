/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.session;

import OnlineConfrenceMgmt.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * Facade class for User operations
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    // Method that checks the username and password
    public User getUserByIdPassword(String username, String password) {

        TypedQuery<User> query = getEntityManager().createQuery("SELECT  u FROM User u where u.name=?1 and u.password=?2", User.class);
        query.setParameter(1, username);
        query.setParameter(2, password);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }
}
