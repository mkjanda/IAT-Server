/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.messaging;

import net.iatsoftware.iat.generated.FileEntityType;

/**
 *
 * @author Michael Janda
 */
public class File extends net.iatsoftware.iat.generated.GFile implements java.io.Serializable {
    private static final long serialVersionUID = 1;

    public File()
    {
        size = 0;
        name = "";
    }
    
    public File(String n, int s)
    {
        size = s;
        name = n;
        path =  n;
    }

    public FileEntityType getEntityType() {
        return FileEntityType.FILE;
    }
}
