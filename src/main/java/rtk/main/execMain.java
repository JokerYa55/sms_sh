/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.main;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import org.jboss.logging.Logger;
import rtk.DAO.AppPropertiesDAO;
import rtk.beans.AppProperties;

/**
 *
 * @author vasil
 */
@Singleton
@Local(execMain.class)
@Lock(LockType.WRITE)
public class execMain {

    private final Logger log = Logger.getLogger(getClass().getName());

    private final long i = 0;
    //private final String propFileName = "app.properties";

    //@PersistenceContext(unitName = "elk_sh_jpa")
    protected EntityManager em;
    protected static EntityManagerFactory emf;

    @PostConstruct
    public void postConstruct() {
        log.debug("postConstruct");
        if (emf == null) {
            this.emf = Persistence.createEntityManagerFactory("sms_sheduler_jpa");
        }
    }

    @PreDestroy
    private void preDestriy() {
        log.debug("preDestriy");
    }

    @Schedule(minute = "*/1", hour = "*")
    @Lock(LockType.WRITE)
    public synchronized void runSh(Timer time) {
        try {
            log.info(String.format("******************* %s ****************************", new Date()));
            try {
                em = emf.createEntityManager();
            } catch (Exception em_ex) {
                log.error(em_ex.getMessage());
            }
            
            log.info("START \t\t\t=> " + (new Date()).toString());
            log.info("NEXT START \t\t=> " + time.getNextTimeout());
            
            String url = getAppParams("url", "null");
            log.info("URL = " + url);
            String sendCount = getAppParams("max_send_count", "10");
            log.info("send_count = " + sendCount);
            String maxRecUserLog = getAppParams("max_rec_user_log", "30");
            log.info("max_rec_user_log = " + maxRecUserLog);
            
        } catch (Exception e) {
            log.log(Logger.Level.ERROR, e);
        }
    }
    
    private String getAppParams(String pName, String pDefVal) {
        String res = null;
        try {
            AppPropertiesDAO appDAO = new AppPropertiesDAO(em);
            AppProperties prop = null;
            try {
                prop = appDAO.getItem(pName);
                res = prop.getParam_value();
            } catch (NoResultException e) {
                prop = new AppProperties();
                prop.setParam_name(pName);
                prop.setParam_value(pDefVal);
                appDAO.addItem(prop);
                res = prop.getParam_value();
            }
        } catch (Exception e1) {
            log.log(Logger.Level.ERROR, e1);
        }
        return res;
    }
}
