/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.DAO;

import javax.persistence.EntityManager;
import rtk.beans.UsersSmsMessages;

/**
 *
 * @author vasil
 */
public class UsersSmsMessagesDAO extends abstractDAO<UsersSmsMessages, Long> {

    public UsersSmsMessagesDAO(EntityManager em) {
        super(em);
    }

}
