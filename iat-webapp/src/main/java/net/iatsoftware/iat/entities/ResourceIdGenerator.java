package net.iatsoftware.iat.entities;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;


import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


public class ResourceIdGenerator implements IdentifierGenerator {
    public static String ENTITY_NAME = "net.iatsoftware.iat.entities.TestResource";
    
    private static final ConcurrentHashMap<IAT, Integer> testMap = new ConcurrentHashMap<>();

    public Serializable generate(SharedSessionContractImplementor session, Object obj) 
            throws HibernateException {
        TestResource entity = (TestResource)obj;
        synchronized (this) {
            testMap.putIfAbsent(entity.getTest(), testMap.getOrDefault(entity.getTest(), 0));
        }
        return testMap.size();
    }
}
