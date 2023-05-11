package net.iatsoftware.iat.entities;

import net.iatsoftware.iat.generated.ResourceType;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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
    private ResourceType resourceType = ResourceType.DEPLOYMENT_FILE;
    private String name, path, mimeType;
    private Long id;
    private Integer resourceId;
	private Integer size;
    private byte[] resource;
    private List<ResourceReference> resourceReferences = new ArrayList<>();

    public TestResource(){}

    public TestResource(IAT test, Integer resourceId, String mimeType, byte[] resource, ResourceType type) {
        this.test = test;
        this.path = "";
        URLEncoder.encode(this.name = test.getTestName(), StandardCharsets.UTF_8);
        this.mimeType = mimeType;
        this.resourceType = type;
        this.resource = resource;
        this.size = resource.length;
        this.resourceId = resourceId;
    }
    
    public TestResource(IAT test, String path, String mimeType, byte[] resource, ResourceType type) {
        this.test = test;
        path = URLEncoder.encode(path, StandardCharsets.UTF_8);
		var matcher = Pattern.compile("(.*/)?([^/]+)").matcher(path);
		matcher.find();
		this.name = matcher.toMatchResult().group(2);
        this.path = path;
        this.resource = resource;
        this.mimeType = mimeType;
        this.size = resource.length;
        this.resourceType = type;
        this.resourceId = 0;
    }

    public TestResource(IAT test, Integer resourceId, String path, String mimeType, int size, ResourceType type) {
        this.test = test;
        path = URLEncoder.encode(path, StandardCharsets.UTF_8);
		var matcher = Pattern.compile("(.*/)?([^/]+)").matcher(path);
		matcher.find();
		this.name = matcher.toMatchResult().group(2);
        this.path = path;
        this.mimeType = mimeType;
        this.size = size;
        this.resourceType = type;
        this.resourceId = resourceId;
    }

    public TestResource(IAT test, Integer resourceId, String path, String mimeType, byte[] resourceBytes, ResourceType type) {
        this.test = test;
        path = URLEncoder.encode(path, StandardCharsets.UTF_8);
		var matcher = Pattern.compile("(.*/)?([^/]+)").matcher(path);
		matcher.find();
		this.name = matcher.toMatchResult().group(2);
        this.path = path;
        this.mimeType = mimeType;
        this.size = resourceBytes.length;
        this.resourceType = type;
        this.resourceId = resourceId;
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
    @Column(name="resource_data", nullable=true)
    public byte[] getResourceBytes() {
        return resource;
    }
    public void setResourceBytes(byte []val) {
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
    @Column(name="resource_id")
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
