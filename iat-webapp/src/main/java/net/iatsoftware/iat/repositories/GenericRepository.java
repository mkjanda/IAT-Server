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

import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Validated
public interface GenericRepository<I extends Serializable, E extends Serializable> {
    @NotNull
    Iterable<E> getAll();
    
    E get(@NotNull I id);
    
    void add(@NotNull E entity);
    
    E update(@NotNull E entity);
    
    void delete(@NotNull E entity);
    
    void deleteById(@NotNull I id);
}
