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

import net.iatsoftware.iat.messaging.Manifest;
import net.iatsoftware.iat.entities.IAT;
import net.iatsoftware.iat.entities.ItemSlide;

import java.util.List;

public interface ItemSlideRepository extends GenericRepository<Long, ItemSlide> {
    List<ItemSlide> getSlidesByTest(IAT test);
    Manifest getItemSlideManifest(IAT test);
}
