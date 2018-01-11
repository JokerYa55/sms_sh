/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.DAO;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import rtk.beans.AppProperties;

/**
 *
 * @author vasil
 */
public class AppPropertiesDAO extends abstractDAO<AppProperties, Long> {

    public AppPropertiesDAO(EntityManager em) {
        super(em);
    }

//    private final EntityManager em;
//
//    public AppPropertiesDAO(EntityManager em) {
//        this.em = em;
//    }
//
//    @Override
//    public EntityManager getEM() {
//        return this.em;
//    }
//
    public AppProperties getItem(String id) {
        //em.getTransaction().begin();
        TypedQuery<AppProperties> query = getEM().createNamedQuery("findByProperties", AppProperties.class);
        query.setParameter("pname", id);
        //em.getTransaction().commit();
        return query.getSingleResult();
    }

}
