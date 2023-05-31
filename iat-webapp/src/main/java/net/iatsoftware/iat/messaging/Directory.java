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
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
public class Directory extends net.iatsoftware.iat.generated.DirectoryPojo {

    private int walkNdx = 0;

    public Directory() {
        name = "";
        entityType = ManifestEntityType.DIRECTORY;
    }

    public Directory(String n) {
        name = n;
        entityType = ManifestEntityType.DIRECTORY;
    }

    public FileEntity walkManifest() {
        if (walkNdx < file.size()) {
            return file.get(walkNdx++);
        } else if (walkNdx < file.size() + directory.size()) {
            return directory.get(walkNdx++ - file.size()).walkManifest();
        } else {
            return null;
        }
    }

    public boolean contains(String fileName) {
        for (File f : file) {
            if (f.getName().equals(fileName)) {
                return true;
            }
        }
        for (Directory d : directory) {
            if (d.contains(fileName)) {
                return true;
            }
        }
        return false;
    }
}
