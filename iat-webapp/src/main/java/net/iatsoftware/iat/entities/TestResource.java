package net.iatsoftware.iat.entities;

import net.iatsoftware.iat.generated.ResourceType;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="test_resources")
public class TestResource implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private IAT test;
    private ResourceType resourceType = null;
    private String mimeType;
    private Long id;
    private Integer resourceId = -1, size;
    private byte[] resource;
    private List<ResourceReference> resourceReferences = new ArrayList<>();

    public TestResource(){}

    public TestResource(IAT test, Integer resourceId, String mimeType, byte[] resource, ResourceType type) {
        this.test = test;
        this.mimeType = mimeType;
        this.resourceType = type;
        this.resource = resource;
        this.size = resource.length;
        this.resourceId = resourceId;
    }

    public TestResource(IAT test, String mimeType, byte[] resource, ResourceType type) {
        this.test = test;
        this.mimeType = mimeType;
        this.resourceType = type;
        this.resource = resource;
        this.size = resource.length;
    }

    public TestResource(IAT test, String mimeType, ResourceType type) {
        this.test = test;
        this.resource = null;
        this.resourceType = type;
    }
    

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="TestResourceID")
    public Long getId() {
        return id;
    }
    public void setId(Long val) {
        id = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID")
    public IAT getTest() {
        return test;
    }
    public void setTest(IAT val) {
        test = val;
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
    @Column(name="resource", nullable=true)
    public byte[] getResourceBytes() {
        return resource;
    }
    public void setResourceBytes(byte []val) {
        if (this.resource != null)
            this.size = this.resource.length;
        this.resource = val;
    }

    @Basic
    @Column(name="size")
    public int getSize() {
        return this.size;
    }
    public void setSize(int val) {
        this.size = val;
    }

    @Basic
    @Column(name="discriminator")
    public Integer getResourceId() {
        return resourceId;
    }
    public void setResourceId(Integer val) {
        resourceId = val;
    }

    @OneToMany(mappedBy="resource")
    public List<ResourceReference> getReferences() {
        return resourceReferences;
    }
    public void setReferences(List<ResourceReference> val) {
        this.resourceReferences = val;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="resource_type")
    public ResourceType getResourceType() {
        return resourceType;
    }
    public void setResourceType(ResourceType val) {
        resourceType = val;
    }
}
