/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.services;

/**
 *
 * @author Michael Janda
 */

import org.springframework.web.socket.WebSocketSession;


public interface WebSocketService extends WebSocketContainer {
    void registerWebSocket(WebSocketSession wss);
    Object getSessionProperty(String sessionId, String property);
    void setSessionProperty(String sessionId, String property, Object o);
    WebSocketSession getSocketSession(String sessId);
}
