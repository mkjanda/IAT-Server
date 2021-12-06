/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.resultretrieval;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ItemSlide;
import net.iatsoftware.iat.repositories.IATRepositoryManager;
import net.iatsoftware.iat.services.PacketDataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import javax.inject.Inject;

@Component("ItemSlideDataSource")
@Scope(value="prototype")
public class DefaultItemSlideDataSource implements PacketDataSource {
    @Inject IATRepositoryManager iatRepositoryManager;
    
    private final IAT test;
    private List<ItemSlide> slideList = null;
    
    public DefaultItemSlideDataSource(IAT test)
    {
        this.test = test;
    }
    
    @Override
    public void halt(){}
    
    @Override
    public boolean isHalted() {
        return false;
    }
    
    @Override
    public byte[] getMoreData() {
        if (slideList == null) {
            slideList = iatRepositoryManager.getItemSlidesByTest(test);
        }
        if (slideList.isEmpty())
            return null;
        return slideList.remove(0).getImageData();
    }
}
