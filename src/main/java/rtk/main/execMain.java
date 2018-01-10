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
import javax.persistence.Persistence;
import org.jboss.logging.Logger;

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
        log.info(String.format("******************* %s ****************************", new Date()));
    }
}
