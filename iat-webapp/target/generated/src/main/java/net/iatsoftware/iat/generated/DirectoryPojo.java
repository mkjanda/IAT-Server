//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.24 at 07:02:58 PM EDT 
//


package net.iatsoftware.iat.generated;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import net.iatsoftware.iat.messaging.Directory;
import net.iatsoftware.iat.messaging.File;
import net.iatsoftware.iat.messaging.FileEntity;


/**
 * <p>Java class for DirectoryPojo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectoryPojo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.iatsoftware.net/schema}file-entity-impl"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Directory" type="{http://www.iatsoftware.net/schema}directory-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="File" type="{http://www.iatsoftware.net/schema}file-impl" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectoryPojo", propOrder = {
    "directory",
    "file"
})
public abstract class DirectoryPojo
    extends FileEntity
{

    @XmlElement(name = "Directory")
    protected List<Directory> directory;
    @XmlElement(name = "File")
    protected List<File> file;

    /**
     * Gets the value of the directory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the directory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirectory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Directory }
     * 
     * 
     */
    public List<Directory> getDirectory() {
        if (directory == null) {
            directory = new ArrayList<Directory>();
        }
        return this.directory;
    }

    /**
     * Gets the value of the file property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the file property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link File }
     * 
     * 
     */
    public List<File> getFile() {
        if (file == null) {
            file = new ArrayList<File>();
        }
        return this.file;
    }

}
