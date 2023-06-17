/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.deployment;

/**
 *
 * @author Michael Janda
 */


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CodeFile")
@XmlAccessorType(XmlAccessType.NONE)
@Component
@Scope(value="prototype")
public class CodeFile extends net.iatsoftware.iat.generated.GCodeFile {
    public CodeFile(){}
    
}
