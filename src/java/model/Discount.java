/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "DISCOUNT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discount.findAll", query = "SELECT d FROM Discount d")
    , @NamedQuery(name = "Discount.findByDiscountid", query = "SELECT d FROM Discount d WHERE d.discountid = :discountid")
    , @NamedQuery(name = "Discount.findByName", query = "SELECT d FROM Discount d WHERE d.name = :name")
    , @NamedQuery(name = "Discount.findByCode", query = "SELECT d FROM Discount d WHERE d.code = :code")
    , @NamedQuery(name = "Discount.findByPercent", query = "SELECT d FROM Discount d WHERE d.percent = :percent")})
public class Discount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DISCOUNTID")
    private String discountid;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "CODE")
    private String code;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERCENT")
    private Double percent;

    public Discount() {
    }
    
    public Discount(String name, String code, double percent) {
        UUID uuid = UUID.randomUUID();
        String discountid = uuid.toString();
        
        this.discountid = discountid;
        this.name = name;
        this.code = code;
        this.percent = percent;
    }

    public Discount(String discountid) {
        this.discountid = discountid;
    }
    
    public String getDiscountid() {
        return discountid;
    }

    public void setDiscountid(String discountid) {
        this.discountid = discountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (discountid != null ? discountid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discount)) {
            return false;
        }
        Discount other = (Discount) object;
        if ((this.discountid == null && other.discountid != null) || (this.discountid != null && !this.discountid.equals(other.discountid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Discount[ discountid=" + discountid + " ]";
    }
    
}
