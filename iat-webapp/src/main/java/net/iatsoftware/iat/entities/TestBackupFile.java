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


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="test_backup_files")
public class TestBackupFile implements java.io.Serializable {
    private long id;
    private IAT test;
    private String fileName;
    private byte[] fileData;
    private DeploymentSession deploymentSession = null;
    
    public TestBackupFile() {  }
    
    public TestBackupFile(String fileName, byte[] fileData, IAT test, DeploymentSession ds) {
        this.fileName = fileName;
        this.fileData = fileData;
        this.test = test;
        this.deploymentSession = ds;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ManifestFileID")
    public Long getId() {
        return this.id;
    }
    public void setId(long val) {
        this.id = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID", referencedColumnName="TestID")
    public IAT getTest() {
        return this.test;
    }
    public void setTest(IAT val) {
        this.test = val;
    }
    
    @Basic
    @Column(name="file_name")
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String val) {
        this.fileName = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @MapsId
    @JoinColumn(name="DeploymentSessionID")
    public DeploymentSession getDeploymentSession() {
        return this.deploymentSession;
    }
    public void setDeploymentSession(DeploymentSession val) {
        this.deploymentSession = val;
    }
    
    
    @Lob
    @Column(name="file_data")
    public byte[] getFileData() {
        return this.fileData;
    }
    public void setFileData(byte[] val) {
        this.fileData = val;
    }
}
