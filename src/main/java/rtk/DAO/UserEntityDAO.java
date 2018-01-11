/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.DAO;

import javax.persistence.EntityManager;
import rtk.beans.UserEntity;
import rtk.interfaces.daoInterface;

/**
 *
 * @author vasil
 */
public class UserEntityDAO implements daoInterface<UserEntity, Long> {

    private EntityManager em;

    public UserEntityDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEM() {
        return this.em;
    }

}
