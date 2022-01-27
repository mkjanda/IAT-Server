package net.iatsoftware.iat.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="resource_references")
public class ResourceReference implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private Long id, resourceId;
    private Integer referenceId;

    public ResourceReference(){}

    public ResourceReference(Long resourceId, int referenceId) {
        this.resourceId = resourceId;
        this.referenceId = referenceId;
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
    @Column(name="resource_id")
    public Long getResourceId() {
        return resourceId;
    }
    public void setResourceId(Long val) {
        resourceId = val;
    }

    @Basic
    @Column(name="reference_id")
    public int getReferenceName() {
        return referenceId;
    }
    public void setReferenceName(int val) {
        referenceId = val;
    }
}
