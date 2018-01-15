/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.logging.Logger;
import rtk.httpUtil.utlHttp;
import rtk.interfaces.senderInterface;

/**
 *
 * @author vasil
 */
public class tele2sender implements senderInterface {

    private final Logger log = Logger.getLogger(getClass().getName());
    private utlHttp httpConnect = null;
    List params;
    String url = null;
    
    public tele2sender(String url, List params) {
        log.info("tele2sender");
        this.httpConnect = new utlHttp();        
        this.url = url;
        this.params = params;
    }

    @Override
    public String send(String address, String message) {
        log.info("send => " + address + " message => " + message);
        String res = null;
        List temp_params = new ArrayList();
        temp_params.addAll(params);
        temp_params.add(new BasicNameValuePair("msisdn", address));
        temp_params.add(new BasicNameValuePair("text", message));        
//        temp_params.forEach((t) -> {
//            log.info("t => " + t);
//        });
        res = httpConnect.doGet(this.url, temp_params, null);        
        log.info("res = " + res);
        return res;
    }

}
