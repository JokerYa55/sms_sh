/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.DAO;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.jboss.logging.Logger;
import rtk.interfaces.daoInterface;


/**
 *
 * @author vasiliy.andricov
 * @param <T>
 * @param <V>
 */
public abstract class abstractDAO<T, V> implements daoInterface<T, V> {

    private final Logger log = Logger.getLogger(getClass().getName());

    private EntityManager em;
    private static EntityManagerFactory emf;

    public abstractDAO() {
        if (emf == null) {
            this.emf = Persistence.createEntityManagerFactory("sms_sheduler_jpa");
        }
        em = emf.createEntityManager();
    }

    @Override
    public EntityManager getEM() {
        return em;
    }

    @Override
    public List<T> getList(int startIdx, int countRec, String jpqName, Class<T> cl) {
        log.info("getList");
        return daoInterface.super.getList(startIdx, countRec, jpqName, cl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> getList(String jpqName, Class<T> cl, Map<String, Object> params) {
        log.info("getList");
        return daoInterface.super.getList(jpqName, cl, params); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<T> getList(String jpqName, Class<T> cl) {
        log.info("getList");
        return daoInterface.super.getList(jpqName, cl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T getItem(long id, String jpqName, Class<T> cl) {
        log.info("getItem");
        return daoInterface.super.getItem(id, jpqName, cl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateItem(T Item) {
        log.info("updateItem");
        return daoInterface.super.updateItem(Item); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteItem(T Item) {
        log.info("deleteItem");
        return daoInterface.super.deleteItem(Item); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T addItem(T Item) {
        log.info("addItem");
        return daoInterface.super.addItem(Item); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
