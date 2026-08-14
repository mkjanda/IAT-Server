/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.events;
import org.springframework.web.socket.WebSocketSession;

import net.iatsoftware.iat.messaging.Envelope;

/**
 *
 * @author Michael Janda too
 */
public class WebSocketFinalDataSent  extends WebSocketDataSent {
    
    public WebSocketFinalDataSent(WebSocketSession session, Envelope msg) {
        super(session, msg);
    }
}
