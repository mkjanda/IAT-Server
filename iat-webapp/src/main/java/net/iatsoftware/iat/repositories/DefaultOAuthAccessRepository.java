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
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.OAuthAccess;

import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@Repository
public class DefaultOAuthAccessRepository extends GenericJpaRepository<Long, OAuthAccess>
        implements OAuthAccessRepository {
    
    private static final Base64.Encoder b64Encoder = Base64.getEncoder();
    
    @Override
    public OAuthAccess createOAuth(Client c, IAT test) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<OAuthAccess> root = query.from(OAuthAccess.class);
        SecureRandom rand = new SecureRandom();
        byte []data = new byte[30];
        rand.nextBytes(data);
        String authToken = b64Encoder.encodeToString(data);
        Predicate pred = cb.equal(root.get("authToken"), authToken);
        boolean authTokenTaken = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
        while (authTokenTaken) {
            rand.nextBytes(data);
            authToken = b64Encoder.encodeToString(data);
            pred = cb.equal(root.get("authToken"), authToken);
            authTokenTaken = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
        }
        
        rand.nextBytes(data);
        String accessToken = b64Encoder.encodeToString(data);
        pred = cb.equal(root.get("accessToken"), accessToken);
        boolean accessTokenTaken = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
        while (accessTokenTaken) {
            rand.nextBytes(data);
            accessToken = b64Encoder.encodeToString(data);
            pred = cb.equal(root.get("accessToken"), accessToken);
            accessTokenTaken = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
        }

        rand.nextBytes(data);
        String refreshToken = b64Encoder.encodeToString(data);
        pred = cb.equal(root.get("refreshToken"), refreshToken);
        boolean refreshTokenTaken = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
        while (refreshTokenTaken) {
            rand.nextBytes(data);
            refreshToken = b64Encoder.encodeToString(data);
            pred = cb.equal(root.get("refreshToken"), refreshToken);
            refreshTokenTaken = this.entityManager.createQuery(query.select(cb.count(root)).where(pred)).getSingleResult() > 0;
        }
        
        OAuthAccess oAuth = new OAuthAccess(c, test, authToken, accessToken, refreshToken);
        this.add(oAuth);
        return oAuth;
    }
    
    @Override
    public int verifyAuthToken(String authToken, String clientId, String clientSecret) {
        Pattern patt = Pattern.compile("^CLIENT([1-9][0-9]*):([A-Za-z0-9_\\-]+):(.+)$");
        Matcher m = patt.matcher(clientId);
        if (!m.matches())
            return OAuthAccess.INVALID_CLIENT_ID;
        long clientID = Long.parseLong(m.group(1));
        String testName = m.group(2);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OAuthAccess> query = cb.createQuery(OAuthAccess.class);
        Root<OAuthAccess> root = query.from(OAuthAccess.class);
        Predicate pred = cb.equal(root.get("authToken"), authToken);
        OAuthAccess oAuth;
        try {
            oAuth = this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException ex) {
            return OAuthAccess.AUTH_TOKEN_NOT_FOUND;
        }
        if (oAuth.isAuthTokenConsumed())
            return OAuthAccess.AUTH_TOKEN_CONSUMED;
        oAuth.setAuthTokenConsumed(true);
        this.update(oAuth);
        if ((oAuth.getClient().getClientId() != clientID) || (!oAuth.getTest().getTestName().equals(testName)) || 
                (!oAuth.getTest().getOauthClientId().equals(clientId)))
            return OAuthAccess.MISMATCHED_CLIENT_ID;
        else if (!clientSecret.equals(oAuth.getTest().getOauthClientSecret()))
            return OAuthAccess.INVALID_CLIENT_SECRET;
        return OAuthAccess.AUTH_TOKEN_VALID;
    }
    
    @Override
    public OAuthAccess performOAuth(String authToken) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OAuthAccess> query = cb.createQuery(OAuthAccess.class);
        Root<OAuthAccess> root = query.from(OAuthAccess.class);
        Predicate pred = cb.equal(root.get("authToken"), authToken);
        try {
            OAuthAccess oAuth = this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
            oAuth.setAuthTokenConsumed(true);
            Calendar accessExpires = Calendar.getInstance();
            accessExpires.add(Calendar.SECOND, OAuthAccess.accessExpiration);
            oAuth.setAccessExpires(accessExpires);
            Calendar refreshExpires = Calendar.getInstance();
            refreshExpires.add(Calendar.SECOND, OAuthAccess.refreshExpiration);
            oAuth.setRefreshExpires(refreshExpires);
            oAuth = this.update(oAuth);
            return oAuth;
        }
        catch (javax.persistence.NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public OAuthAccess verifyAccessToken(String accessToken) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OAuthAccess> query = cb.createQuery(OAuthAccess.class);
        Root<OAuthAccess> root = query.from(OAuthAccess.class);
        Predicate pred = cb.equal(root.get("accessToken"), accessToken);
        OAuthAccess oAuth;
        try {
            oAuth = this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException ex) {
            return null;
        }
        return oAuth;
    }
    
    @Override
    public int verifyRefreshRequest(String clientId, String clientSecret, String refreshToken) {
        Pattern patt = Pattern.compile("^([1-9][0-9]*)-([A-Za-z0-9_\\-]+):(.+)$");
        Matcher m = patt.matcher(clientId);
        if (!m.matches())
            return OAuthAccess.INVALID_CLIENT_ID;
        long clientID = Long.parseLong(m.group(1));
        String testName = m.group(2);
        String oauthClientId = m.group(3);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OAuthAccess> query = cb.createQuery(OAuthAccess.class);
        Root<OAuthAccess> root = query.from(OAuthAccess.class);
        Predicate pred = cb.equal(root.get("refreshToken"), refreshToken);
        OAuthAccess oAuth;
        try {
            oAuth = this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException ex) {
            return OAuthAccess.NO_SUCH_REFRESH_TOKEN;
        }
        if ((oAuth.getClient().getClientId() != clientID) || (!oAuth.getTest().getTestName().equals(testName)) || 
                (!oAuth.getTest().getOauthClientId().equals(oauthClientId)))
            return OAuthAccess.MISMATCHED_CLIENT_ID;
        else if (!clientSecret.equals(oAuth.getTest().getOauthClientSecret()))
            return OAuthAccess.INVALID_CLIENT_SECRET;
        return OAuthAccess.REFRESH_TOKEN_VALID;
    }
    
    @Override
    public String refreshAccessToken(String refreshToken) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<OAuthAccess> query = cb.createQuery(OAuthAccess.class);
        Root<OAuthAccess> root = query.from(OAuthAccess.class);
        Predicate pred = cb.equal(root.get("refreshToken"), refreshToken);
        OAuthAccess oAuth;
        try {
            oAuth = this.entityManager.createQuery(query.select(root).where(pred)).getSingleResult();
        }
        catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
            return null;
        }
        byte []data = new byte[30];
        SecureRandom rand = new SecureRandom();
        rand.nextBytes(data);
        Base64.Encoder enc = Base64.getEncoder();
        String b64 = enc.encodeToString(data);
        CriteriaQuery<Long> query2 = cb.createQuery(Long.class);
        root = query2.from(OAuthAccess.class);
        pred = cb.equal(root.get("accessToken"), b64);
        while (this.entityManager.createQuery(query2.select(cb.count(root)).where(pred)).getSingleResult() > 0L) {
            rand.nextBytes(data);
            b64 = enc.encodeToString(data);
        }
        oAuth.setAccessToken(b64);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 20);
        oAuth.setAccessExpires(cal);
        this.update(oAuth);
        return b64;
    }
    
    @Override
    public void cleanupExpiredTokens() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<OAuthAccess> deletion = cb.createCriteriaDelete(OAuthAccess.class);
        Root<OAuthAccess> root = deletion.from(OAuthAccess.class);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, -OAuthAccess.refreshExpiration);
        Predicate pred = cb.lessThan(root.get("refreshExpires"), cb.currentTimestamp());
        this.entityManager.createQuery(deletion.where(pred)).executeUpdate();
    }
}
