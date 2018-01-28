/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.http.message.BasicNameValuePair;
import org.jboss.logging.Logger;
import rtk.DAO.AppPropertiesDAO;
import rtk.DAO.UsersSmsMessagesDAO;
import rtk.beans.AppProperties;
import rtk.beans.UsersSmsMessages;
import rtk.interfaces.senderInterface;
import rtk.util.tele2sender;

/**
 *
 * @author vasil
 */
@Singleton
@Local(execMain.class)
@Lock(LockType.WRITE)
public class execMain {

    private final Logger log = Logger.getLogger(getClass().getName());
    private final String SMS_MODULE = "sms_module";
    private final String EMAIL_MODULE = "email_module";

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

    @Schedule(minute = "*/1", second = "*/30", hour = "*")
    @Lock(LockType.WRITE)
    public synchronized void runSh(Timer time) {
        try {
            log.info(String.format("*************** %s : %s ******************", SMS_MODULE, new Date()));
            try {
                em = emf.createEntityManager();
            } catch (Exception em_ex) {
                log.error(em_ex.getMessage());
            }

            log.info("START \t\t\t=> " + (new Date()).toString());
            log.info("NEXT START \t\t=> " + time.getNextTimeout());

            String url = getAppParams("url_sms", "null");
            log.info("URL SMS = " + url);
            String sendCount = getAppParams("max_send_count", "10");
            log.info("send_count = " + sendCount);
            String maxRecUserLog = getAppParams("max_rec_user_log", "30");
            log.info("max_rec_user_log = " + maxRecUserLog);
            Date current_date = new Date();
            String smsLastDateRun = getAppParams(SMS_MODULE + "_last_send_date", current_date.getTime() + "");

            UsersSmsMessagesDAO logSmsDAO = new UsersSmsMessagesDAO(em);
            Map<String, Object> params = new HashMap();
            params.put("status", false);
            params.put("message_type", "SMS");
            List<UsersSmsMessages> smsList = logSmsDAO.getList("UsersSmsMessages.findByTypeStatus", UsersSmsMessages.class, params);

            List sms_params = new ArrayList();
            sms_params.add(new BasicNameValuePair("operation", "send"));
            sms_params.add(new BasicNameValuePair("login", "rt_com:kclkb2b:1"));
            sms_params.add(new BasicNameValuePair("operation", "send"));
            sms_params.add(new BasicNameValuePair("password", "A2Y4LE6h"));
            sms_params.add(new BasicNameValuePair("shortcode", "Rostelecom"));
            senderInterface sms_sender = new tele2sender(url, sms_params);

            for (UsersSmsMessages item : smsList) {
                log.info("item => " + item);
                log.info("tel => " + item.getUserId().getPhone());
                String check_sms = sms_sender.send(item.getUserId().getPhone(), String.format("Kod %s. PTK", item.getMessage()).replaceAll(" ", "%20"));
                item.setCheck_code(check_sms);
                item.setStatus(true);
                item.setCheck_code_date(new Date());
                //item.setMessage_type("SMS");
                (new UsersSmsMessagesDAO(em)).updateItem(item);
            }

            em.clear();
            em.close();

        } catch (Exception e) {
            log.log(Logger.Level.ERROR, e);
        }
    }

    @Schedule(minute = "*", second = "*/30", hour = "*")
    @Lock(LockType.WRITE)
    public synchronized void runShEmail(Timer time) {
        try {
            log.info(String.format("*************** %s : %s ******************", EMAIL_MODULE, new Date()));
            try {
                em = emf.createEntityManager();
            } catch (Exception em_ex) {
                log.error(em_ex.getMessage());
            }

            log.info("START \t\t\t=> " + (new Date()).toString());
            log.info("NEXT START \t\t=> " + time.getNextTimeout());

            String url = getAppParams("smtp_server_host", "null");
            log.info("mail host = " + url);
            String sendCount = getAppParams("smtp_server_port", "25");
            log.info("send_count = " + sendCount);
            String maxRecUserLog = getAppParams("max_rec_user_log", "30");
            log.info("max_rec_user_log = " + maxRecUserLog);
            Date current_date = new Date();
            String smsLastDateRun = getAppParams(EMAIL_MODULE + "_last_send_date", current_date.getTime() + "");

//            UsersSmsMessagesDAO logSmsDAO = new UsersSmsMessagesDAO(em);
//            Map<String, Object> params = new HashMap();
//            params.put("status", false);
//            List<UsersSmsMessages> smsList = logSmsDAO.getList("UsersSmsMessages.findByStatus", UsersSmsMessages.class, params);
//
//            List sms_params = new ArrayList();
//            sms_params.add(new BasicNameValuePair("operation", "send"));
//            sms_params.add(new BasicNameValuePair("login", "rt_com:kclkb2b:1"));
//            sms_params.add(new BasicNameValuePair("operation", "send"));
//            sms_params.add(new BasicNameValuePair("password", "A2Y4LE6h"));
//            sms_params.add(new BasicNameValuePair("shortcode", "Rostelecom"));
//            senderInterface sms_sender = new tele2sender(url, sms_params);
//
//            for (UsersSmsMessages item : smsList) {
//                log.info("item => " + item);
//                log.info("tel => " + item.getUserId().getPhone());
//                String check_sms = sms_sender.send(item.getUserId().getPhone(), String.format("Kod %s. PTK", item.getMessage()).replaceAll(" ", "%20"));
//                item.setCheck_code(check_sms);
//                item.setStatus(true);
//                item.setCheck_code_date(new Date());
//                //item.setMessage_type("SMS");
//                (new UsersSmsMessagesDAO(em)).updateItem(item);
//            }

            em.clear();
            em.close();

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
