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

import net.iatsoftware.iat.generated.FileManifestType;
import net.iatsoftware.iat.messaging.File;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="manifest_files", indexes = {
    @Index(name="files_deployment_session", columnList="SessionID")
})
public class ManifestFile implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id, fileSize;
    private int transmissionOrder;
    private String fileName, filePath;
    private String mimeType;
    private DeploymentSession deploymentSession;
    private FileManifestType fileType;
    
    public ManifestFile() {}
    
    public ManifestFile(DeploymentSession ds, File f, int transOrder, FileManifestType fileType) {
        this.deploymentSession = ds;
        this.fileSize = f.getSize();
        this.fileName = f.getName();
        this.filePath = f.getPath();
        this.transmissionOrder = transOrder;
        this.fileType = fileType;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ManifestFileID")
    public Long getManifestFileId() {
        return this.id;
    }
    public void setManifestFileId(Long id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="SessionID", referencedColumnName="SessionID")
    public DeploymentSession getDeploymentSession() {
        return this.deploymentSession;
    }
    public void setDeploymentSession(DeploymentSession val) {
        this.deploymentSession = val;
    }
    
    @Basic
    @Column(name="file_name")
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String val) {
        this.fileName = val;
    }
    
    @Basic
    @Column(name="file_path")
    public String getFilePath() {
        return this.filePath;
    }
    public void setFilePath(String val) {
        this.filePath = val;
    }
    @Basic
    @Column(name="file_size")
    public long getFileSize() {
        return this.fileSize;
    }
    public void setFileSize(Long val) {
        this.fileSize = val;
    }
    
    @Basic
    @Column(name="transmission_order")
    public int getTransmissionOrder() {
        return this.transmissionOrder;
    }
    public void setTransmissionOrder(Integer val) {
        this.transmissionOrder = val;
    }

    @Basic
    @Column(name="mime_type")
    public String getMimeType() {
        return mimeType;
    }
    public void setMimeType(String val) {
        mimeType = val;
    }

    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name="file_type")
    public FileManifestType getFileType() {
        return this.fileType;
    }
    public void setFileType(FileManifestType val) {
        this.fileType = val;
    }
}
