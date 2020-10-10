/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OnlineConfrenceMgmt.session;

import OnlineConfrenceMgmt.entity.Topic;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * Facade class for Topic operations
 */
@Stateless
public class TopicFacade extends AbstractFacade<Topic> {
    @PersistenceContext(unitName = "onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TopicFacade() {
        super(Topic.class);
    }
    
}
