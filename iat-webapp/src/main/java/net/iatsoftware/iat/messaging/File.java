/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.generated.ManifestEntityType;

/**
 *
 * @author Michael Janda
 */
public class File extends net.iatsoftware.iat.generated.FilePojo implements java.io.Serializable {
    private static final long serialVersionUID = 1;

    public File()
    {
        size = 0;
        name = "";
        this.entityType = ManifestEntityType.FILE;
    }
    
    public File(String n, int s)
    {
        size = s;
        name = n;
        entityType = ManifestEntityType.FILE;
        path =  n;
    }
}
