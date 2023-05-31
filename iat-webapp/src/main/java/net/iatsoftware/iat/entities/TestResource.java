package net.iatsoftware.iat.entities;

import net.iatsoftware.iat.generated.ResourceType;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="test_resources")
public class TestResource implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private IAT test;
    private ResourceType resourceType = null;
    private String mimeType;
    private Long id;
    private Integer resourceId = null, size;
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
        this.size = 0;
        this.mimeType = mimeType;
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
