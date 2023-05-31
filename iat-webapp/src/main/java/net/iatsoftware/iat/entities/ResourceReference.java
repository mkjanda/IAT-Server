package net.iatsoftware.iat.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="resource_references")
public class ResourceReference implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private Long id;
    private Integer referenceId;
	private TestResource testResource;

    public ResourceReference(){}

    public ResourceReference(TestResource testResource, Integer referenceId) {
        this.testResource = testResource;
        this.referenceId =  referenceId;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name="reference_id")
    public Integer getReferenceId() {
        return referenceId;
    }
    public void setReferenceId(Integer val) {
        referenceId = val;
    }

	@ManyToOne
    @JoinColumn(name="TestResourceID")
    public TestResource getResource() {
        return this.testResource;
    }
    public void setResource(TestResource val) {
        this.testResource = val;
    }
}
