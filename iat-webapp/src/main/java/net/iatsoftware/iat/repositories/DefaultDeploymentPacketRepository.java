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


import net.iatsoftware.iat.entities.DeploymentSession;
import net.iatsoftware.iat.generated.PacketType;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

import net.iatsoftware.iat.entities.DeploymentPacket;


@Repository
public class DefaultDeploymentPacketRepository extends GenericJpaRepository<Long, DeploymentPacket> 
    implements DeploymentPacketRepository
{
    private static Logger logger = LogManager.getLogger();

    @Override
    public void deletePackets(List<DeploymentPacket> packets) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<DeploymentPacket> deletion = cb.createCriteriaDelete(DeploymentPacket.class);
        Root<DeploymentPacket> root = deletion.from(DeploymentPacket.class);
        Predicate pred = root.in(packets);
        this.entityManager.createQuery(deletion.where(pred)).executeUpdate();
    }
    
    @Override
    public byte[] getData(DeploymentSession ds, PacketType packetType, int ordinal) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<byte[]> query = cb.createQuery(byte[].class);
            Root<DeploymentPacket> root = query.from(DeploymentPacket.class);
            Predicate pred = cb.and(cb.equal(root.get("deploymentSession"), ds), cb.equal(root.get("packetType"), packetType), cb.equal(root.get("uploadOrdinal"), ordinal));
            return this.entityManager.createQuery(query.select(root.get("packetData")).where(pred)).getSingleResult();
        }
        catch (Exception ex) {
            logger.error("Error retrieving deployment packet data", ex);
            return null;
        }
    }
}
