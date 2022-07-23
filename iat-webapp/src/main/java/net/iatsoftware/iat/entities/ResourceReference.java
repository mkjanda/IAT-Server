package net.iatsoftware.iat.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private TestResource testResource;

    public ResourceReference(){}

    public ResourceReference(TestResource testResource, Long referenceId) {
        this.testResource = testResource;
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

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="resource_id", referencedColumnName="id")
    public TestResource getResource() {
        return this.testResource;
    }
    public void setResource(TestResource val) {
        this.testResource = val;
    }
}
