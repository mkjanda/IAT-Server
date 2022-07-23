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


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

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
