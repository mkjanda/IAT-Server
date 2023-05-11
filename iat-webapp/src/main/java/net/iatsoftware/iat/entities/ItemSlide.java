/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.iatsoftware.iat.entities;

/**
 *
 * @author Michael Janda
 */

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="item_slides", indexes={@Index(name="items_test_ndx", columnList="TestID")})
public class ItemSlide implements java.io.Serializable {
    private static final long serialVersionUID = 1;
    private long id, slideSize;
    private IAT test;
    private String fileName;
    private byte[] imageData;
    private int slideNum;
    
    public ItemSlide(){}
    public ItemSlide(IAT test, int slideNum, String filename, byte[] imageData) {
        this.test = test;
        this.fileName = filename;
        this.imageData = imageData;
        this.slideSize = imageData.length;
        this.slideNum = slideNum;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="SlideID")
    public long getId() {
        return this.id;
    }
    public void setId(Long val) {
        this.id = val;
    }
    
    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestID", referencedColumnName="TestID")
    public IAT getTest() {
        return this.test;
    }
    public void setTest(IAT val) {
        this.test = val;
    }
    
    @Basic
    @Column(name="file_name")
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String val) {
        this.fileName = val;
    }
    
    @Basic
    @Column(name="slide_num")
    public int getSlideNum() {
        return this.slideNum;
    }
    public void setSlideNum(int val) {
        this.slideNum = val;
    }
    
    @Basic
    @Column(name="slide_size")
    public long getSlideSize() {
        return this.slideSize;
    }
    public void setSlideSize(long val) {
        this.slideSize = val;
    }
    
    @Lob
    @Column(name="image_data")
    public byte[] getImageData() {
        return this.imageData;
    }
    public void setImageData(byte[] val) {
        this.imageData = val;
    }
    
}
