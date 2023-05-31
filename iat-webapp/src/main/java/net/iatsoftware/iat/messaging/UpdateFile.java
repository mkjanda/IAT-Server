/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

/**
 *
 * @author Michael Janda
 */

import net.iatsoftware.iat.generated.ManifestEntityType;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="UpdateFile")
@XmlAccessorType(XmlAccessType.NONE)
public class UpdateFile extends net.iatsoftware.iat.generated.UpdateFilePojo {
    public UpdateFile() {
        this.entityType = ManifestEntityType.UPDATE_FILE;
    }
    
    public UpdateFile(String filename, String path, String destDirectory, long size) {
        this.size = size;
        this.entityType = ManifestEntityType.UPDATE_FILE;
        this.name = filename;
        this.path = destDirectory;
        this.sourcePath = path;
    }
    
}
