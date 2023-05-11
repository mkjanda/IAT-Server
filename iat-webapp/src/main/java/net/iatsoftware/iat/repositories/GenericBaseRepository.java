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

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.io.Serializable;

public abstract class GenericBaseRepository<I extends Serializable, E extends Serializable> implements GenericRepository<I, E> {
    protected final Class<I> idClass;
    protected final Class<E> entityClass;
    
    @SuppressWarnings("unchecked")
    public GenericBaseRepository()
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        while (!(genericSuperclass instanceof ParameterizedType)) {
            if(!(genericSuperclass instanceof Class<?>))
                throw new IllegalStateException("Unable to determine type " +
                        "arguments because generic superclass neither " +
                        "parameterized type nor class.");
            if(genericSuperclass == GenericBaseRepository.class)
                throw new IllegalStateException("Unable to determine type " +
                        "arguments because no parameterized generic superclass " +
                        "found.");
            genericSuperclass = ((Class<?>)genericSuperclass).getGenericSuperclass();            
        }
        
        ParameterizedType type = (ParameterizedType)genericSuperclass;
        Type[] arguments = type.getActualTypeArguments();
        this.idClass = (Class<I>)arguments[0];
        this.entityClass = (Class<E>)arguments[1];
    }
}
