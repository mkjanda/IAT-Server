package net.iatsoftware.iat.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
