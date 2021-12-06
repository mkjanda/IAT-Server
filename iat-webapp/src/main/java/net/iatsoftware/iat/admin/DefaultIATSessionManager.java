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

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultIATSessionManager implements IATSessionManager {
    private static final ConcurrentHashMap<String, IATSession> sessions = new ConcurrentHashMap<>();
    private static final Random random = new Random();
    private static final Base64.Encoder b64Encoder = Base64.getEncoder();
    private static final long SESSION_LIFE = 3_600_000L;
   
    @Override
    public IATSession createSession() {
        byte[] idBytes = new byte[18];
        String sessId;
        do {
            random.nextBytes(idBytes);
            sessId = b64Encoder.encodeToString(idBytes);
        } while (sessions.containsKey(sessId));
        IATSession session = new IATSession(sessId);
        sessions.put(sessId, session);
        return session;
    }
    
    @Override
    public IATSession getSession(String sessId) {
        return sessions.get(sessId);
    }
    
    @Override
    public void destroySession(String sessId) {
        sessions.remove(sessId);
    }
    
    @Scheduled(initialDelay=60_000L, fixedDelay=60_000L)
    public void cleanupProc() {
        Enumeration<String> ids = sessions.keys();
        long time = System.currentTimeMillis();
        while (ids.hasMoreElements()) {
            String id = ids.nextElement();
            IATSession sess = sessions.get(id);
            if (sess.getLastAccessTime() + SESSION_LIFE < time)
                destroySession(sess.getId());
        }
    }
}
