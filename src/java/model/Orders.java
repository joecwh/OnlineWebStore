/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static enumeration.OrderCode.INCOMPLETE;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@Table(name = "ORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")
    , @NamedQuery(name = "Orders.findByOrderid", query = "SELECT o FROM Orders o WHERE o.orderid = :orderid")
    , @NamedQuery(name = "Orders.findByTransactionid", query = "SELECT o FROM Orders o WHERE o.transactionid = :transactionid")
    , @NamedQuery(name = "Orders.findByShippingid", query = "SELECT o FROM Orders o WHERE o.shippingid = :shippingid")
    , @NamedQuery(name = "Orders.findByUserid", query = "SELECT o FROM Orders o WHERE o.userid = :userid")
    , @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status")
    , @NamedQuery(name = "Orders.findByModifiedat", query = "SELECT o FROM Orders o WHERE o.modifiedat = :modifiedat")
    , @NamedQuery(name = "Orders.findByCreatedat", query = "SELECT o FROM Orders o WHERE o.createdat = :createdat")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ORDERID")
    private String orderid;
    @Size(max = 255)
    @Column(name = "TRANSACTIONID")
    private String transactionid;
    @Size(max = 255)
    @Column(name = "SHIPPINGID")
    private String shippingid;
    @Size(max = 255)
    @Column(name = "USERID")
    private String userid;
    @Size(max = 255)
    @Column(name = "STATUS")
    private String status;
    @Column(name = "MODIFIEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedat;
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    public Orders() {
    }
    public Orders(String transactionid, String shippingid, String userid) 
    {
        UUID uuid = UUID.randomUUID();
        String orderid = uuid.toString();
        orderid = orderid.substring(0, 5);
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
                
        this.orderid = orderid;
        this.transactionid = transactionid;
        this.shippingid = shippingid;
        this.userid = userid;
        this.status = INCOMPLETE.name();
        this.createdat = currentTimestamp;
    }
    

    public Orders(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getShippingid() {
        return shippingid;
    }

    public void setShippingid(String shippingid) {
        this.shippingid = shippingid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Orders[ orderid=" + orderid + " ]";
    }
    
}
