/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.repositories;

/**
 *
 * @author Michael Janda
 */
import net.iatsoftware.iat.entities.Client;

public class UserKey implements java.io.Serializable {

    private Client client;
    private int userNum;

    public UserKey() {
    }

    public UserKey(Client client, int userNum) {
        this.client = client;
        this.userNum = userNum;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client val) {
        this.client = val;
    }

    public Integer getUserNum() {
        return this.userNum;
    }

    public void setUserNum(Integer val) {
        this.userNum = val;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserKey) {
            UserKey u = (UserKey) o;
            return ((this.userNum == u.userNum) && (this.client.getClientId() == u.client.getClientId())) ? true : false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) ((client.getClientId() << 10) | userNum);
    }
}
