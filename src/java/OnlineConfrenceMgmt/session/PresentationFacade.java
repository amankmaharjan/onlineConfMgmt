/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.session;

import OnlineConfrenceMgmt.entity.Presentation;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * Facade class for Presentation operations
 */
@Stateless
public class PresentationFacade extends AbstractFacade<Presentation> {

    @PersistenceContext(unitName = "onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PresentationFacade() {
        super(Presentation.class);
    }

    /* Method that returns the gmt list of the presentation country */
    public List<String> getGMTList() {
        Query query = getEntityManager().createQuery("SELECT pr.participant.country.gmtOffset FROM Presentation pr where pr.status='ACTIVE'");
        List<String> gmtOffsetList = query.getResultList();
        return gmtOffsetList;
    }
    /* Method that returns the topic list of the presentation country */
    public List<String> getTopicList() {
        Query query = getEntityManager().createQuery("SELECT pr.topic.topicTitle FROM Presentation pr where pr.status='ACTIVE' group by pr.topic.topicTitle");
        List<String> topicList = query.getResultList();
        return topicList;
    }
    /* Method that returns the Prsentation list  by status */
    public List<Presentation> findByStatusActive() {
        Query query = getEntityManager().createQuery("SELECT pr FROM Presentation  pr where pr.status='ACTIVE'");
        List<Presentation> presentationList = query.getResultList();
        return presentationList;
    }
}
