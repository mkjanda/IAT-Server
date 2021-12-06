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
public class File extends net.iatsoftware.iat.generated.FilePojo {
    public File()
    {
        size = 0;
        name = "";
        entityType = net.iatsoftware.iat.generated.ManifestEntityType.FILE;
    }
    
    public File(String n, long s)
    {
        size = s;
        name = n;
        entityType = net.iatsoftware.iat.generated.ManifestEntityType.FILE;
        path = "./" + n;
    }
}
