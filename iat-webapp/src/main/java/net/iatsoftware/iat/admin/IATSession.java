/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.admin;

/**
 *
 * @author michael
 */

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class IATSession {
    private final String sessId;
    private final Map<String, Object> sessionObjects = new HashMap<>();
    
    public IATSession(String sessId) {
        this.sessId = sessId;
    }
    
    public String getId() {
        return this.sessId;
    }
    
    public Object getAttribute(String name) {
        return sessionObjects.get(name);
    }
    
    public void setAttribute(String name, Object val) {
        this.sessionObjects.put(name, val);
    }
    
    public boolean checkAttribute(String name) {
        if (this.sessionObjects.containsKey(name))
            return true;
        return false;
    }
}
