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

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;

@Entity
@Table(name = "test_result_fragments")
public class TestResultFragment implements java.io.Serializable {
    private long id;
    private AdminTimer adminTimer;
    private byte[] resultFragment = null, cipher = null, IV = null;
    private int ordinal;
    
    public TestResultFragment(){}
    
    public TestResultFragment(AdminTimer timer, int ordinal, byte[] encCipher, byte[] encIV, byte[] encData) {
        this.adminTimer = timer;
        this.ordinal = ordinal;
        this.resultFragment = encData;
        this.cipher = encCipher;
        this.IV = encIV;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ResultFragmentID")
    public long getId()
    {
        return this.id;
    }
    public void setId(long val)
    {
        this.id = val;
    }

    @ManyToOne(fetch=FetchType.EAGER, optional=false)
    @JoinColumn(name="TestAdminID", referencedColumnName="timer_id")
    public AdminTimer getAdminTimer()
    {
        return this.adminTimer;
    }
    public void setAdminTimer(AdminTimer val)
    {
        this.adminTimer = val;
    }

    @Basic
    @Column(name="ordinal")
    public int getOrdinal() {
        return this.ordinal;
    }
    public void setOrdinal(int val) {
        this.ordinal = val;
    }
            
    @Basic
    @Column(name="cipher")
    public byte[] getCipher() {
        return this.cipher;
    }
    public void setCipher(byte[] val) {
        this.cipher = val;
    }
            
    @Basic
    @Column(name="iv")
    public byte[] getIV() {
        return this.IV;
    }
    public void setIV(byte[] val) {
        this.IV = val;
    }
    
    @Lob
    @Column(name="result_fragment")
    public byte []getResultFragment()
    {
        return resultFragment;
    }
    public void setResultFragment(byte[] val)
    {
        resultFragment = val;
    }
}
