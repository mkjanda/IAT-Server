package net.iatsoftware.iat.entities;

import javax.persistence.Basic;
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
    private Long id, resourceId;
    private Long referenceId;
    private IAT test;

    public ResourceReference(){}

    public ResourceReference(IAT test, Long resourceId, Long referenceId) {
        this.resourceId = resourceId;
        this.referenceId = referenceId;
        this.test = test;
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
    public Long getReferenceId() {
        return referenceId;
    }
    public void setReferenceId(Long val) {
        referenceId = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="test_id", referencedColumnName="TestID")
    public IAT getTest() {
        return test;
    }
    public  void setTest(IAT test) {
        this.test = test;
    }
}
