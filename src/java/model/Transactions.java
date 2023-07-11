/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
@Table(name = "TRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t")
    , @NamedQuery(name = "Transactions.findByTransactionid", query = "SELECT t FROM Transactions t WHERE t.transactionid = :transactionid")
    , @NamedQuery(name = "Transactions.findByCartid", query = "SELECT t FROM Transactions t WHERE t.cartid = :cartid")
    , @NamedQuery(name = "Transactions.findByPaymentid", query = "SELECT t FROM Transactions t WHERE t.paymentid = :paymentid")
    , @NamedQuery(name = "Transactions.findByUserid", query = "SELECT t FROM Transactions t WHERE t.userid = :userid")
    , @NamedQuery(name = "Transactions.findByShippingfee", query = "SELECT t FROM Transactions t WHERE t.shippingfee = :shippingfee")
    , @NamedQuery(name = "Transactions.findByTaxes", query = "SELECT t FROM Transactions t WHERE t.taxes = :taxes")
    , @NamedQuery(name = "Transactions.findBySubtotal", query = "SELECT t FROM Transactions t WHERE t.subtotal = :subtotal")
    , @NamedQuery(name = "Transactions.findByDiscountcode", query = "SELECT t FROM Transactions t WHERE t.discountcode = :discountcode")
    , @NamedQuery(name = "Transactions.findByTotal", query = "SELECT t FROM Transactions t WHERE t.total = :total")
    , @NamedQuery(name = "Transactions.findByCreatedat", query = "SELECT t FROM Transactions t WHERE t.createdat = :createdat")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TRANSACTIONID")
    private String transactionid;
    @Size(max = 255)
    @Column(name = "CARTID")
    private String cartid;
    @Size(max = 255)
    @Column(name = "PAYMENTID")
    private String paymentid;
    @Size(max = 255)
    @Column(name = "USERID")
    private String userid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SHIPPINGFEE")
    private Double shippingfee;
    @Column(name = "TAXES")
    private Double taxes;
    @Column(name = "SUBTOTAL")
    private Double subtotal;
    @Size(max = 255)
    @Column(name = "DISCOUNTCODE")
    private String discountcode;
    @Column(name = "TOTAL")
    private Double total;
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    public Transactions() {
    }
    
    public Transactions(String cartid, String paymentid, String userid, double shippingfee, double taxes, double subtotal, double total, String coupon) 
    {
        UUID uid = UUID.randomUUID();
        String tid = uid.toString();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        
        this.transactionid = tid;
        this.cartid = cartid;
        this.paymentid = paymentid;
        this.userid = userid;
        this.shippingfee = shippingfee;
        this.taxes = taxes;
        this.subtotal = subtotal;
        this.total = total;
        this.discountcode = coupon;
        this.createdat = currentTimestamp;
    }

    public Transactions(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Double getShippingfee() {
        return shippingfee;
    }

    public void setShippingfee(Double shippingfee) {
        this.shippingfee = shippingfee;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getDiscountcode() {
        return discountcode;
    }

    public void setDiscountcode(String discountcode) {
        this.discountcode = discountcode;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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
        hash += (transactionid != null ? transactionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.transactionid == null && other.transactionid != null) || (this.transactionid != null && !this.transactionid.equals(other.transactionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Transactions[ transactionid=" + transactionid + " ]";
    }
    
}
