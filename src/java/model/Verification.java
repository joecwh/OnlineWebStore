/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
@Table(name = "VERIFICATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Verification.findAll", query = "SELECT v FROM Verification v")
    , @NamedQuery(name = "Verification.findByToken", query = "SELECT v FROM Verification v WHERE v.token = :token")
    , @NamedQuery(name = "Verification.findByUserid", query = "SELECT v FROM Verification v WHERE v.userid = :userid")
    , @NamedQuery(name = "Verification.findByExpiredat", query = "SELECT v FROM Verification v WHERE v.expiredat = :expiredat")})
public class Verification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "TOKEN")
    private String token;
    @Size(max = 255)
    @Column(name = "USERID")
    private String userid;
    @Column(name = "EXPIREDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredat;

    public Verification() {
    }

    public Verification(String userid) 
    {
        this.token = UUID.randomUUID().toString();
        this.userid = userid;

        LocalDateTime currentTimestamp = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentTimestamp.plus(15, ChronoUnit.MINUTES);
        this.expiredat = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getExpiredat() {
        return expiredat;
    }

    public void setExpiredat(Date expiredat) {
        this.expiredat = expiredat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (token != null ? token.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Verification)) {
            return false;
        }
        Verification other = (Verification) object;
        if ((this.token == null && other.token != null) || (this.token != null && !this.token.equals(other.token))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Verification[ token=" + token + " ]";
    }
    
}
