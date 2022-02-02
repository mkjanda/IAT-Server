package net.iatsoftware.iat.entities;

import java.util.List;
import java.util.regex.Pattern;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;;

@Entity
@Table(name="test_resources", uniqueConstraints = @UniqueConstraint(columnNames={"TestID", "name"}))
public class TestResource implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private IAT test;
    private String name, path, mimeType;
    private long resourceId, size;
    private byte[] resource;
    private List<ResourceReference> resourceReferences;

    public TestResource(){}

    public TestResource(IAT test, String path, String mimeType) {
        this.test = test;
        this.name = Pattern.compile("(^|/)(([/]+)$").matcher(path).group(1);
        this.path = path;
        this.mimeType = mimeType;
    }
    
    public TestResource(IAT test, String path, String mimeType, byte[] resource) {
        this.test = test;
        this.name = Pattern.compile("(^|/)(([/]+)$").matcher(path).group(1);
        this.path = path;
        this.resource = resource;
        this.mimeType = mimeType;
        this.size = resource.length;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ResourceID")
    public long getResourceId() {
        return resourceId;
    }
    public void setResourceId(long val) {
        resourceId = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID", referencedColumnName="TestID")
    public IAT getTest() {
        return test;
    }
    public void setTest(IAT val) {
        test = val;
    }

    @Basic
    @Column(name="name")
    public String getName() {
        return name;
    }
    public void setName(String val) {
        name = val;
    }

    @Basic
    @Column(name="path")
    public String getPath() {
        return path;
    }
    public void setPath(String val) {
        path = val;
    }

    @Basic
    @Column(name="mimetype")
    public String getMimeType() {
        return this.mimeType;
    }
    public void setMimeType(String val) {
        this.mimeType = val;
    }

    @Lob
    @Column(name="resource")
    public byte[] getResource() {
        return resource;
    }
    public void setResource(byte []val) {
        this.resource = val;
    }

    @Basic
    @Column(name="size")
    public long getSize() {
        return this.size;
    }
    public void setSize(long val) {
        this.size = val;
    }

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="ResourceID")
    public List<ResourceReference> getReferences() {
        return resourceReferences;
    }
    public void setReferences(List<ResourceReference> val) {
        this.resourceReferences = val;
    }
}
