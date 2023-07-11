/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static enumeration.ShippingCode.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "SHIPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shipping.findAll", query = "SELECT s FROM Shipping s")
    , @NamedQuery(name = "Shipping.findByShippingid", query = "SELECT s FROM Shipping s WHERE s.shippingid = :shippingid")
    , @NamedQuery(name = "Shipping.findByReceivername", query = "SELECT s FROM Shipping s WHERE s.receivername = :receivername")
    , @NamedQuery(name = "Shipping.findByEmail", query = "SELECT s FROM Shipping s WHERE s.email = :email")
    , @NamedQuery(name = "Shipping.findByContact", query = "SELECT s FROM Shipping s WHERE s.contact = :contact")
    , @NamedQuery(name = "Shipping.findByShippingaddress", query = "SELECT s FROM Shipping s WHERE s.shippingaddress = :shippingaddress")
    , @NamedQuery(name = "Shipping.findByStatus", query = "SELECT s FROM Shipping s WHERE s.status = :status")
    , @NamedQuery(name = "Shipping.findByExpectedreachdate", query = "SELECT s FROM Shipping s WHERE s.expectedreachdate = :expectedreachdate")
    , @NamedQuery(name = "Shipping.findByModifiedat", query = "SELECT s FROM Shipping s WHERE s.modifiedat = :modifiedat")
    , @NamedQuery(name = "Shipping.findByCreatedat", query = "SELECT s FROM Shipping s WHERE s.createdat = :createdat")})
public class Shipping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SHIPPINGID")
    private String shippingid;
    @Size(max = 255)
    @Column(name = "RECEIVERNAME")
    private String receivername;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "CONTACT")
    private String contact;
    @Size(max = 255)
    @Column(name = "SHIPPINGADDRESS")
    private String shippingaddress;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "EXPECTEDREACHDATE")
    @Temporal(TemporalType.DATE)
    private Date expectedreachdate;
    @Column(name = "MODIFIEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    public Shipping() {
    }
    
    public Shipping(String receivername, String email, String contact, String shippingaddress, Date expectedreachdate) 
    {
        UUID uuid = UUID.randomUUID();
        String shippingid = uuid.toString();
        
        this.shippingid = shippingid;
        this.receivername = receivername;
        this.email = email;
        this.contact = contact;
        this.shippingaddress = shippingaddress;
        this.status = PENDING.name();
        this.expectedreachdate = expectedreachdate;
    }

    public Shipping(String shippingid) {
        this.shippingid = shippingid;
    }

    public String getShippingid() {
        return shippingid;
    }

    public void setShippingid(String shippingid) {
        this.shippingid = shippingid;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getShippingaddress() {
        return shippingaddress;
    }

    public void setShippingaddress(String shippingaddress) {
        this.shippingaddress = shippingaddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpectedreachdate() {
        return expectedreachdate;
    }

    public void setExpectedreachdate(Date expectedreachdate) {
        this.expectedreachdate = expectedreachdate;
    }

    public Date getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(Date modifiedat) {
        this.modifiedat = modifiedat;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shippingid != null ? shippingid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shipping)) {
            return false;
        }
        Shipping other = (Shipping) object;
        if ((this.shippingid == null && other.shippingid != null) || (this.shippingid != null && !this.shippingid.equals(other.shippingid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Shipping[ shippingid=" + shippingid + " ]";
    }
    
}
