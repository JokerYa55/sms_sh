/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.DAO;

import javax.persistence.EntityManager;
import rtk.beans.UsersAuthSmsCode;

/**
 *
 * @author vasil
 */
public class UsersAuthSmsCodeDAO extends abstractDAO<UsersAuthSmsCode, Long> {

    public UsersAuthSmsCodeDAO(EntityManager em) {
        super(em);
    }

}
