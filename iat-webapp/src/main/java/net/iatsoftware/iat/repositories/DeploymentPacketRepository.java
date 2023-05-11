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

import net.iatsoftware.iat.entities.DeploymentPacket;
import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.generated.PacketType;

import java.util.List;

public interface DeploymentPacketRepository extends GenericRepository<Long, DeploymentPacket> {
  //  List<DeploymentPacket> getQueuedDeploymentPackets(Long dsID);
    void deletePackets(List<DeploymentPacket> packets);
    byte []getData(DeploymentSession ds, PacketType packetType, int ordinal);
}
