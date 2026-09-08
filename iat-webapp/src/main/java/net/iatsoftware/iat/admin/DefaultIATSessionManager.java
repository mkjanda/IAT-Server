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

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Cache;
import java.time.Duration;

import java.util.Base64;
import java.util.Random;

@Service
public class DefaultIATSessionManager implements IATSessionManager {
    private static final Random random = new Random();
    private static final Base64.Encoder b64Encoder = Base64.getEncoder();
    private static final long SESSION_LIFE = 3_600_000L;
    static public final Cache<String, IATSession> sessionCache = Caffeine.newBuilder()
        .maximumSize(10_000).expireAfterAccess(Duration.ofHours(1))
        .build();
   
    @Override
    public IATSession createSession() {
        byte[] idBytes = new byte[18];
        String sessId;
        do {
            random.nextBytes(idBytes);
            sessId = b64Encoder.encodeToString(idBytes);
        } while (sessionCache.getIfPresent(sessId) != null);
        IATSession session = new IATSession(sessId);
        sessionCache.put(sessId, session);
        return session;
    }
    
    @Override
    public IATSession getSession(String sessId) {
        return sessionCache.getIfPresent(sessId);
    }
    
    @Override
    public void destroySession(String sessId) {
        sessionCache.invalidate(sessId);
    }
    
    @Scheduled(initialDelay=60_000L, fixedDelay=60_000L)
    public void cleanupProc() {
        long time = System.currentTimeMillis();
        for (String id : sessionCache.asMap().keySet()) {
            IATSession sess = sessionCache.getIfPresent(id);
            if (sess.getLastAccessTime() + SESSION_LIFE < time)
                destroySession(sess.getId());
        }
    }
}
