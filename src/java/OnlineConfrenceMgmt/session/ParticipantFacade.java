/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OnlineConfrenceMgmt.session;

import OnlineConfrenceMgmt.entity.Participant;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
/**
 *
 * Facade class for Participant operations
 */
@Stateless
public class ParticipantFacade extends AbstractFacade<Participant> {
    @PersistenceContext(unitName = "onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParticipantFacade() {
        super(Participant.class);
    }
    
}
