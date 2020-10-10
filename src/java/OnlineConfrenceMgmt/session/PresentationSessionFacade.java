/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.session;

import OnlineConfrenceMgmt.entity.PresentationSession;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * Facade class for PresentationSession operations
 */
@Stateless
public class PresentationSessionFacade extends AbstractFacade<PresentationSession> {

    @PersistenceContext(unitName = "onlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PresentationSessionFacade() {
        super(PresentationSession.class);
    }

  /* Method that retrurns all the active status session */
    public List<PresentationSession> getActiveSession() {
        TypedQuery<PresentationSession> query = getEntityManager().createQuery("SELECT  p FROM  PresentationSession p where p.status=?1", PresentationSession.class);
        query.setParameter(1, "ACTIVE");
        return query.getResultList();
    }
/* Method that remvoes all the presentation session*/
    public void removeAll() {
        em.createQuery("DELETE FROM PresentationSession p").executeUpdate();

    }

}
