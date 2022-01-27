package net.iatsoftware.iat.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;


@Entity
@Table(name="test_resources")
public class TestResource implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private IAT test;
    private String resourceName;
    private long resourceId;
    private byte[] resource;
    private byte[] addendum;
    private String mimeType;
    private Set<ResourceReference> resourceReferences;

    public TestResource(){}

    public TestResource(IAT test, String resourceName, String mimeType) {
        this.test = test;
        this.resourceName = resourceName;
        this.mimeType = mimeType;
    }
    
    public TestResource(IAT test, String resourceName, String mimeType, byte[] resource) {
        this.test = test;
        this.resourceName = resourceName;
        this.resource = resource;
        this.mimeType = mimeType;
    }

    public TestResource(IAT test, String resourceName, String mimeType, byte[] resource, String addendum) {
        this.test = test;
        this.resourceName = resourceName;
        this.resource = resource;
        this.mimeType = mimeType;
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
    @Column(name="resource_name")
    public String getResourceName() {
        return resourceName;
    }
    public void setResourceName(String val) {
        resourceName = val;
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

    @Lob
    @Column(name="addendum", nullable=true)
    public byte[] getAddendum() {
        return this.addendum;
    }
    public void setAddendum(byte[] val) {
        this.addendum = val;
    }

    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="resource_id")
    public Set<ResourceReference> getReferences() {
        return resourceReferences;
    }
}
