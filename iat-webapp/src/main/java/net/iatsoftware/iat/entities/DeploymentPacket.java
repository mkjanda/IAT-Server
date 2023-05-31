/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.generated.PacketType;

import jakarta.persistence.Basic;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name="deployment_packets", indexes=
        @Index(name="tests_deployment", columnList="DeploymentSessionID"))
public class DeploymentPacket implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private DeploymentSession deploymentSession = null;
    private byte[] packetData = null;
    private int uploadOrdinal;
    private PacketType packetType;
    
    public DeploymentPacket() {}
    
    public DeploymentPacket(DeploymentSession deploymentSession, PacketType packetType, byte[] data, int uploadOrdinal) {
        this.deploymentSession = deploymentSession;
        this.packetData = data;
        this.uploadOrdinal = uploadOrdinal;
        this.packetType = packetType;
    }
    
    
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId()
    {
        return this.id;
    }
    public void setId(long val)
    {
        this.id = val;
    }
            
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="DeploymentSessionID")
    public DeploymentSession getDeploymentSession()
    {
        return this.deploymentSession;
    }
    public void setDeploymentSession(DeploymentSession val)
    {
        this.deploymentSession = val;
    }
    
    @Lob
    @Column(name="packet_data")
    public byte[] getPacketData()
    {
        return this.packetData;
    }
    public void setPacketData(byte[] val)
    {
        this.packetData = val;
    }
    
    @Basic
    @Column(name="upload_ordinal")
    public int getUploadOrdinal() {
        return this.uploadOrdinal;
    }
    public void setUploadOrdinal(int val) {
        this.uploadOrdinal = val;
    }
    
    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name="packet_type")
    public PacketType getPacketType() {
        return this.packetType;
    }
    public void setPacketType(PacketType val) {
        this.packetType = val;
    }
}
