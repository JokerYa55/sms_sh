/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 *
 * @author vasiliy.andricov
 */
public class emailAuthenticator extends Authenticator{

    private String login;
    private String password;

    public emailAuthenticator(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password);
    }
}
