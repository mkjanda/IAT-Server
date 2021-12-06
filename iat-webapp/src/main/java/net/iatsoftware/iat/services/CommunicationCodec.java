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

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.websocket.Encoder;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.iatsoftware.iat.messaging.XmlPacket;
import java.io.StringWriter;
import java.io.StringReader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CommunicationCodec implements Encoder.Text<XmlPacket>, Decoder.Text<XmlPacket>
{
    private final Logger log = LogManager.getLogger();
    private static Marshaller marshaller = null;
    private static Unmarshaller unmarshaller = null;

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }
    
    private synchronized Marshaller getMarshaller() {
        if (marshaller == null) {
            WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
            marshaller = ctx.getBean(Marshaller.class);
        }
        return marshaller;
    }
    
    private synchronized Unmarshaller getUnmarshaller() {
        if (unmarshaller == null) {
            WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
            unmarshaller = ctx.getBean(Unmarshaller.class);
        }
        return unmarshaller;
    }
    
    @Override
    public String encode(XmlPacket p)
    {
        try {
            StringWriter sWriter = new StringWriter();
            StreamResult result = new StreamResult(sWriter);
            getMarshaller().marshal(p, result);
            sWriter.flush();
            return sWriter.toString();
        }
        catch (Exception ex) {
            log.error("Error encoding envelope for transmission", ex);
            return "";
        }
    }
    
    @Override
    public XmlPacket decode(String input)
    {
        try {
            StreamSource source = new StreamSource(new StringReader(input));
            XmlPacket p = (XmlPacket)getUnmarshaller().unmarshal(source);
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            getMarshaller().marshal(p, sr);
            log.info(sw.toString());
            return p;
        }
        catch (Exception ex) {
            log.error("Error decoding envelope from transmission", ex);
            return null;
        }
    }
    
    @Override
    public void init(EndpointConfig config)
    {
        log.error("codec initialized");
    }
    
    @Override
    public void destroy(){
        log.error("codec destroyed");
    }
}
