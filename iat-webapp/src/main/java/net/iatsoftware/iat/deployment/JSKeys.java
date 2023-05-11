/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.config.MyBeanFactory;
import net.iatsoftware.iat.entities.JSKey;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;


@Component
@Scope("prototype")
@XmlRootElement(name = "JSKeySet")
@XmlAccessorType(XmlAccessType.NONE)
public class JSKeys implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger("critical");
    @XmlElements(
    @XmlElement(name = "Key"))
    private final List<JSKey> KeySet = new ArrayList<>();

    @Inject
    MyBeanFactory beanFactory;

    public JSKeys() {
    }

    public String[] addKey() {
        JSKey key = new JSKey();
//        key.generateKey();
        KeySet.add(key);
    //    return key.getKeyWordStrings();
    return new String[4];
    }

    public void addKey(JSKey key) {
        JSKey k = beanFactory.jsKey();
  //      k.setKeyWords(key.getKeyWordStrings());
        KeySet.add(k);
    }

    public List<JSKey> getKeys() {
        return KeySet;
    }

    public String getKeySetJSON() {
        try {
            ObjectMapper oMapper = new ObjectMapper();
            return oMapper.writeValueAsString(KeySet).intern();
        } catch (com.fasterxml.jackson.core.JsonProcessingException ex) {
            log.error(ex);
            return "";
        }
    }

    public JSKeys getEncryptedKeySet(List<BigInteger> cipher) {
        return beanFactory.jsKeys();
    }
}
