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


import java.util.Calendar;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="deployment_sessions")
public class DeploymentSession implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public static final long DEPLOYMENT_TIMEOUT = 300_000L;
    private String sessID;
    private IAT test;
    private Long id;
    private Calendar deploymentStart = Calendar.getInstance();
    private String deploymentUploadKey = null, itemSlideUploadKey = null, reconnectionKey = null;
    public DeploymentSession() { }
    
    public DeploymentSession(Client c, User u, IAT test, String sessID) {
        this.test = test;
        this.sessID = sessID;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="SessionID")
    public Long getId() {
        return this.id;
    }
    public void setId(Long val) {
        this.id = val;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deployment_start")
    public Calendar getDeploymentStart() {
        return this.deploymentStart;
    }
    public void setDeploymentStart(Calendar val) {
        this.deploymentStart = val;
    }

    @Basic
    @Column(name="deployment_upload_key")
    public String getDeploymentUploadKey() {
        return this.deploymentUploadKey;
    }
    public void setDeploymentUploadKey(String val) {
        this.deploymentUploadKey = val;
    }
    
    @Basic
    @Column(name="item_slide_upload_key") 
    public String getItemSlideUploadKey() {
        return this.itemSlideUploadKey;
    }
    public void setItemSlideUploadKey(String val) {
        this.itemSlideUploadKey = val;
    }
            
    @Basic
    @Column(name="reconnection_key")
    public String getReconnectionKey() {
        return this.reconnectionKey;
    }
    public void setReconnectionKey(String val) {
        this.reconnectionKey = val;
    }

    @Basic
    @Column(name="web_socket_id")
    public String getWebSocketId() {
        return this.sessID;
    }
    public void setWebSocketId(String val) {
        this.sessID = val;
    }

    @OneToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID", referencedColumnName = "TestID")
    public IAT getTest() {
        return this.test;
    }
    public void setTest(IAT val) {
        test = val;
    }
}
