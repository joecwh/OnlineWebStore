/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
@Table(name = "CARD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c")
    , @NamedQuery(name = "Card.findByCardid", query = "SELECT c FROM Card c WHERE c.cardid = :cardid")
    , @NamedQuery(name = "Card.findByOwnername", query = "SELECT c FROM Card c WHERE c.ownername = :ownername")
    , @NamedQuery(name = "Card.findByCardnumber", query = "SELECT c FROM Card c WHERE c.cardnumber = :cardnumber")
    , @NamedQuery(name = "Card.findByExpirydate", query = "SELECT c FROM Card c WHERE c.expirydate = :expirydate")
    , @NamedQuery(name = "Card.findByCvc", query = "SELECT c FROM Card c WHERE c.cvc = :cvc")})
public class Card implements Serializable {

    @Size(max = 255)
    @Column(name = "USERID")
    private String userid;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CARDID")
    private String cardid;
    @Size(max = 255)
    @Column(name = "OWNERNAME")
    private String ownername;
    @Size(max = 255)
    @Column(name = "CARDNUMBER")
    private String cardnumber;
    @Column(name = "EXPIRYDATE")
    @Temporal(TemporalType.DATE)
    private Date expirydate;
    @Column(name = "CVC")
    private Integer cvc;

    public Card() {
    }
    
    public Card(String userid, String ownername, String cardnumber, Date expirydate, int cvc) 
    {
        UUID uid = UUID.randomUUID();
        String cardid = uid.toString();
        
        this.cardid = cardid;
        this.userid = userid;
        this.ownername = ownername;
        this.cardnumber = cardnumber;
        this.expirydate = expirydate;
        this.cvc = cvc;
    }
    
    public void setData(String userid, String ownername, String cardnumber, Date expirydate, int cvc)
    {
        this.userid = userid;
        this.ownername = ownername;
        this.cardnumber = cardnumber;
        this.expirydate = expirydate;
        this.cvc = cvc;
    }

    public Card(String cardid) {
        this.cardid = cardid;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardid != null ? cardid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Card)) {
            return false;
        }
        Card other = (Card) object;
        if ((this.cardid == null && other.cardid != null) || (this.cardid != null && !this.cardid.equals(other.cardid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Card[ cardid=" + cardid + " ]";
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
}
