/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineConfrenceMgmt.session;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * Abstract class for performing JPA operations
 */
public abstract class AbstractFacade<T> {
    /* entity class */

    private Class<T> entityClass;

    // constructor
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    /* Get entity manger */
    protected abstract EntityManager getEntityManager();
/* create the entity */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
/* edit the entity */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
/* Remove the entity */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
/* find the entity */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
/* get all entity */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
/* find range */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
/* count the entity */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
