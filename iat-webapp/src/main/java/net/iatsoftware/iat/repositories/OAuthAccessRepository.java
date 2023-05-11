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

public interface OAuthAccessRepository extends GenericRepository<Long, OAuthAccess> {
    OAuthAccess createOAuth(Client c, IAT test);
    int verifyAuthToken(String authToken, String clientId, String clientSecret);
    OAuthAccess performOAuth(String authToken);
    OAuthAccess verifyAccessToken(String accessToken);
    int verifyRefreshRequest(String clientId, String clientSecret, String refreshToken);
    String refreshAccessToken(String refreshToken);
    void cleanupExpiredTokens();
}
