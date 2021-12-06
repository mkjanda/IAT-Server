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
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.iatsoftware.iat.generated.ManifestEntityType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.NONE)
public class Directory extends net.iatsoftware.iat.generated.DirectoryPojo {

    private int walkNdx = 0;

    public Directory() {
        name = "";
        size = 0;
        entityType = ManifestEntityType.DIRECTORY;
    }

    public Directory(String n) {
        name = n;
        size = 0;
        entityType = ManifestEntityType.DIRECTORY;
    }

    private long computeSize(FileEntity e) {
        long sz = 0;
        for (File f : file) {
            return f.getSize();
        }
        for (Directory d : directory) {
            sz += computeSize(d);
        }
        return sz;
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

    public boolean beforeMarshal(Marshaller m) {
        size = computeSize(this);
        return true;
    }

    public void afterUnmarshal(Unmarshaller u, Object o) {
        size = computeSize(this);
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
